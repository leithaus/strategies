// -*- mode: Scala;-*- 
// Filename:    validator.scala 
// Authors:     lgm                                                    
// Creation:    Thu Aug 20 10:31:47 2015 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package com.synereo.casper

trait StateFnT[State,Address,Data,Hash,Signature] 
extends Function2[State,( EntryT[Address,Data,Hash,Signature], Int ),State]

trait ValidityCheckUtilsT[Address,Data,Hash,Signature,AppState,Timer] {
  def hash[State] : State => Hash
  def validTxn[State](
    state : State,
    transitionFn : StateFnT[State,Address,Data,Hash,Signature],
    txnNH : ( EntryT[Address,Data,Hash,Signature], Int )
  ) : ( Boolean, State ) = {
    val ( txn, height ) = txnNH
    val nxtState = transitionFn( state, txnNH )
    ( 
      ( txn.prev == hash( state ) ) && ( txn.post == hash( nxtState ) ),
      nxtState 
    )    
  }
  def loopTest[State](
    testFn : ( State, StateFnT[State,Address,Data,Hash,Signature], EntryT[Address,Data,Hash,Signature] ) => ( Boolean, State ),
    acc : ( Boolean, State ),
    stateFn : StateFnT[State,Address,Data,Hash,Signature],
    candidates : Seq[EntryT[Address,Data,Hash,Signature]]
  ) : ( Boolean, State ) = {
    candidates match {
      case Nil => acc
      case c :: rest => {
        val ( cond, state ) = acc
        val nxtAcc = testFn( state, stateFn, c )
        if ( nxtAcc._1 ) {
          loopTest( testFn, nxtAcc, stateFn, rest )
        }
        else {
          nxtAcc
        }
      }
    }                                        
  }
  def initialDifference(
    txnSeq1 : Seq[TxnT[Address,Data,Hash,Signature]],
    txnSeq2 : Seq[TxnT[Address,Data,Hash,Signature]]
  ) : Int = {
    def loop( index : Int, zipSeq : Seq[( TxnT[Address,Data,Hash,Signature], TxnT[Address,Data,Hash,Signature] )] ) : Int = {
      zipSeq match {
        case Nil => index
        case ( txn1, txn2 ) :: rest => {
          if ( txn2 != txn2 ) {
            index
          }
          else loop( index + 1, rest )
        }
      }
    }
    loop( 0, txnSeq1.zip( txnSeq2 ) )
  }
}

case class InvalidBlockException[T]( data : T ) extends Exception( data + "" )
case class InvalidBlockHeightException[T]( data : T, blkHeight : Int ) extends Exception( data + "" )

trait ValidatorT[Address,Data,PrimHash,Hash <: Tuple2[PrimHash,PrimHash],Signature,AppState,Timer]
  extends ValidityCheckUtilsT[Address,Data,Hash,Signature,AppState,Timer]
{
  def initConsensusManagerState : ConsensusManagerStateT[Address,Data,Hash,Signature]
  def initAppState : AppState
  def consensusManagerStateMap : ConsensusManagerStateMapT[PrimHash,Address,Data,Hash,Signature]
  def appStateMap : AppStateMapT[Address,Data,PrimHash,Signature,AppState]
  def timer : Timer
  def blockValidityRecord : Map[Hash,Boolean]
  def consensusManagerStateFn : StateFnT[ConsensusManagerStateT[Address,Data,Hash,Signature],Address,Data,Hash,Signature]
  = new StateFnT[ConsensusManagerStateT[Address,Data,Hash,Signature],Address,Data,Hash,Signature]
  {
    def apply(
      state : ConsensusManagerStateT[Address,Data,Hash,Signature],
      txnNHeight : ( EntryT[Address,Data,Hash,Signature], Int )
    ) : ConsensusManagerStateT[Address,Data,Hash,Signature] = {
      val ( txn, height ) = txnNHeight
      txn match {
        case Ghost( _, cd, _ ) => {
          cd match {
            case blk@Block( h, _, _, _, _, _ ) => {
              //throw new Exception( "tbd" )
              ( blockValidityRecord.get( hash( blk ) ), ( h < height ) ) match {
                case ( Some( true ), true ) => {
                  val nGT = 
                    state.ghostTable + ( h -> ( List( ( blk, Nil ) ) ++ state.ghostTable.getOrElse( h, Nil ) ) ).asInstanceOf[GhostTableT[Address,Data,Hash,Signature]]
                  ConsensusManagerState(
                    nGT,
                    state.history,
                    state.bondedValidators,
                    state.evidenceChecker
                  )
                }
                case ( None, true ) => {
                  val validBlkp = valid( blk )
                  blockValidityRecord += ( hask( blk ) -> validBlkp )
                  if ( validBlkp ) {
                    val nGT = 
                      state.ghostTable + ( h -> ( List( ( blk, Nil ) ) ++ state.ghostTable.getOrElse( h, Nil ) ) ).asInstanceOf[GhostTableT[Address,Data,Hash,Signature]]
                    ConsensusManagerState(
                      nGT,
                      state.history,
                      state.bondedValidators,
                      state.evidenceChecker
                    )
                  }  
                  else {
                    throw new InvalidBlockException( txnNHeight )
                  }
                }
                case _ => {
                  throw new InvalidBlockException( txnNHeight )
                }
              }
            }
            case Evidence( _, validations ) => {
              state.evidenceChecker( validations ) match {
                case Some( addr ) => {
                  ConsensusManagerState(
                    state.ghostTable,
                    state.history,
                    state.bondedValidators.diff( List( addr ) ),
                    state.evidenceChecker
                  )
                }
                case None => {
                  throw new InvalidBlockException( txnNHeight )
                }
              }          
            }
            case Validation( _, _ ) => {
              throw new Exception( "tbd" )
            }
            case _ => {
              throw new Exception( "tbd" )
            }
          }
        }
        case _ => {
          throw new Exception( "tbd" )
        }
      }
    }
  }
  def appStateFn : StateFnT[AppState,Address,Data,Hash,Signature] 

  val validCmgtTxn = validTxn[ConsensusManagerStateT[Address,Data,Hash,Signature]] _
  val validAppTxn = validTxn[AppState] _
        
  def valid( block : BlockT[Address,Data,Hash,Signature] ) : Boolean = {
    val ( cmgtSH, appSH ) = ( block.ghostEntries( 1 ).prev );
    val ( cmgtS, appS ) = ( consensusManagerStateMap( cmgtSH ), appStateMap( appSH ) );
    val ( initGhostTable, initHistory ) = ( cmgtS.ghostTable, cmgtS.history );

    try {
      val ( ghostCond, ghostState ) =
        loopTest(
          validCmgtTxn,
          ( true, cmgtS ),
          consensusManagerStateFn,
          block.ghostEntries.map( ( e ) => ( e, block.height ) )
        )
      
      (
        ghostCond &&
        {
          val ( ghostGhostTable, ghostHistory ) =
            ( ghostState.ghostTable, ghostState.history );
          val ( initTxnSeq, ghostTxnSeq ) =
            ( initHistory( initGhostTable ), ghostHistory( ghostGhostTable ) ) ;
          val pos = initialDifference( ghostTxnSeq, initTxnSeq );
          val reorgGhostTxnSeq = ghostTxnSeq.drop( pos );
          val reorgInitAppState = appStateMap( ghostTxnSeq( pos - 1 ).post._2 )
          
          (
            // reorg validity check
            block.reorgEntries.txns( 0 ).prev._2 == hash( reorgInitAppState ) &&
            ( reorgGhostTxnSeq.map( _.payload ) == block.reorgEntries.txns.map( _.payload ) ) &&
            {
              val ( cmgtCond, cmgtState ) =
                loopTest(
                  validCmgtTxn,
                  ( true, ghostState ),
                  consensusManagerStateFn,
                  block.reorgEntries.txns.map( ( e ) => ( e, block.height ) )
                )
              cmgtCond && 
              {
                val ( appCond, appState ) =
                  loopTest( validAppTxn, ( true, reorgInitAppState ), appStateFn, block.reorgEntries.txns ) 
                appCond &&
                loopTest(
                  validCmgtTxn,
                  ( true, cmgtState ),
                  consensusManagerStateFn,
                  block.txns.map( ( e ) => ( e, block.height ) )
                )._1 && // fees
                loopTest( validAppTxn, ( true, appState ), appStateFn, block.txns )._1            
              }
            }
          )
        }
      )
    }
    catch {
      case e : Exception => false
    }
  }
}
