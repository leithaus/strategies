// -*- mode: Scala;-*- 
// Filename:    validator.scala 
// Authors:     lgm                                                    
// Creation:    Thu Aug 20 10:31:47 2015 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package com.synereo.casper

import scala.collection.mutable.HashMap
import scala.collection.mutable.Map

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
    testFn : ( State, StateFnT[State,Address,Data,Hash,Signature], ( EntryT[Address,Data,Hash,Signature], Int ) ) => ( Boolean, State ),
    acc : ( Boolean, State ),
    stateFn : StateFnT[State,Address,Data,Hash,Signature],
    candidates : Seq[(EntryT[Address,Data,Hash,Signature],Int)]
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
  def winner(
    blkMap : Map[BlockT[Address,Data,Hash,Signature],Seq[Bet[Address,Hash]]]
  ) : Option[Block[Address,Data,Hash,Signature]]
  
  def isBlockFinal(
    cmgtState : ConsensusManagerStateT[Address,Data,Hash,Signature],
    height : Int
  ) : Boolean = {    
    val finalized =
      for( 
	betMap <- cmgtState.ghostTable.get( height );
	blk <- winner( betMap )
      ) yield {
	isBlockFinal( cmgtState, betMap( blk ) )
      }
    finalized match {
      case Some( true ) => true
      case _ => false
    }
  }
  def isBlockFinal(
    cmgtState : ConsensusManagerStateT[Address,Data,Hash,Signature],
    bets : Seq[Bet[Address,Hash]]
  ) : Boolean = {
    val totalBonds = 
      ( 0 /: cmgtState.bondedValidators )( ( acc, vbpair ) => { acc + vbpair._2 } )
    val committedBonds =
      ( 0 /: bets )(
	{
	  ( acc, bet ) => {
	    if ( bet.prob > .9999 ) {
	      cmgtState.bondedValidators.get( bet.validator ) match {
		case Some( bond ) => {
		  acc + bond
		}
		case None => {
		  throw new Exception( "unbonded validator : " + bet.validator )
		}
	      }
	    }
	    else { acc }
	  }
	}
      )
    ( ( committedBonds / totalBonds ) >= cmgtState.finalityThreshold )
  }
  def consensusManagerStateFn : StateFnT[ConsensusManagerStateT[Address,Data,Hash,Signature],Address,Data,Hash,Signature]
  = new StateFnT[ConsensusManagerStateT[Address,Data,Hash,Signature],Address,Data,Hash,Signature]
  {
    def apply(
      state : ConsensusManagerStateT[Address,Data,Hash,Signature],
      txnNHeight : ( EntryT[Address,Data,Hash,Signature], Int )
    ) : ConsensusManagerStateT[Address,Data,Hash,Signature] = {
      val ( txn : EntryT[Address,Data,Hash,Signature], height ) = txnNHeight
      txn match {
        case Ghost( _, cd, _ ) => {
          cd match {
            case blk@Block( h, _, _, _, _, _, _ ) => {
              //throw new Exception( "tbd" )
              ( blockValidityRecord.get( hash( blk ) ), ( h < height ) ) match {
                case ( Some( true ), true ) => {
                  val nGT = 
                    (
		      state.ghostTable
		      + ( h -> ( List( ( blk, Nil ) ) ++ state.ghostTable.getOrElse( h, Nil ) ) )
		    ).asInstanceOf[GhostTableT[Address,Data,Hash,Signature]]
                  ConsensusManagerState(
                    nGT,
		    state.ghostDepth,
                    state.history,
                    state.bondedValidators,
		    state.minimumBond,
		    state.finalityThreshold,
                    state.evidenceChecker,
		    state.blockHashMap,
		    state.balances,
		    state.revenues,
		    state.lastStoredHeight
                  )
                }
                case ( None, true ) => {
                  val validBlkp = valid( blk )
                  blockValidityRecord += ( hash( blk ) -> validBlkp )
		  state.blockHashMap += ( hash( blk ) -> blk )
                  if ( validBlkp ) {
                    val nGT = 
                      (
			state.ghostTable
			+ ( h -> ( List( ( blk, Nil ) ) ++ state.ghostTable.getOrElse( h, Nil ) ) )
		      ).asInstanceOf[GhostTableT[Address,Data,Hash,Signature]]
                    ConsensusManagerState(
                      nGT,
		      state.ghostDepth,
                      state.history,
                      state.bondedValidators,
		      state.minimumBond,
		      state.finalityThreshold,
                      state.evidenceChecker,
		      state.blockHashMap,
		      state.balances,
		      state.revenues,
		      state.lastStoredHeight
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
		    state.ghostDepth,
                    state.history,
                    ( state.bondedValidators - addr ),
		    state.minimumBond,
		    state.finalityThreshold,
                    state.evidenceChecker,
		    state.blockHashMap,
		    state.balances,
		    state.revenues,
		    state.lastStoredHeight
                  )
                }
                case None => {
                  throw new InvalidBlockException( txnNHeight )
                }
              }          
            }
            case Validation( bets, _ ) => {
              //throw new Exception( "tbd" )
	      val newGhostTable = 
		( state.ghostTable /: bets )(
		  {
		    ( acc, e ) => {
		      e match {
			// BUGBUG : lgm -- round number collisions
			// means validator equivocation which is
			// treated as evidence
			case bet@Bet( _, betHeight, blkhash, round, prob, _ ) => {
			  if ( betHeight < height ) {
			    val newBetMap =
			      state.blockHashMap.get( blkhash ) match {
				// BUGBUG : lgm -- if block is in
				// blockHashMap then it should be in
				// ghostTable. This is an invariant
				// that we wish to maintain / check.
				case Some( blk ) => {
				  acc.get( betHeight ) match {
				    case Some( blkBetMap ) => {
				      blkBetMap + ( blk -> ( blkBetMap.getOrElse( blk, Nil ) ++ List( bet ) ) )
				    }
				    case None => {
				      (
					new HashMap[BlockT[Address,Data,Hash,Signature],Seq[Bet[Address,Hash]]]()
					+ ( blk -> List( bet ) )
				      )
				    }
				  }	    
				}
				case None => {
				  throw new InvalidBlockException( txnNHeight )
				}
			      }
			    (
			      acc
			      + ( betHeight -> newBetMap )
			    ).asInstanceOf[GhostTableT[Address,Data,Hash,Signature]]
			  }
			  else {
			    throw new InvalidBlockException( txnNHeight )
			  }
			}
		      }
		    }
		  }
		)
	      ConsensusManagerState(
                newGhostTable,
		state.ghostDepth,		
                state.history,
                state.bondedValidators,
		state.minimumBond,
		state.finalityThreshold,
                state.evidenceChecker,
		state.blockHashMap,
		state.balances,
		state.revenues,
		state.lastStoredHeight
              )
            }
            case _ => {
              throw new Exception( "tbd" )
            }
          }
        }
	case FeeDistribution( prev, post ) => {
	  val ( cmgtHash, _ ) = prev
	  if ( hash( state ) == cmgtHash ) {
	    val lsh = state.lastStoredHeight
	    ( state /: ( 0 to ( ( height - 1  ) - state.lastStoredHeight ) ) )(
	      {
		( acc, e ) => {
		  val currHeight = lsh + e
		  state.ghostTable.get( currHeight ) match {
		    case Some( map ) => {
		      winner( map ) match {
			case Some( blk ) => {			  
			  val blkRevenues =
			    acc.revenues.get( currHeight )
			  ( isBlockFinal( state, currHeight ), blkRevenues ) match {
			    case ( true, None ) => {
			      val newRevAtH = 
				( 0 /: blk.txns.map( _.fee ) )( _ + _ )
			      val newRev = acc.revenues + ( ( lsh + e ) -> newRevAtH )
			      val newBal =
				( acc.balances /: blk.txns )(
				  {
				    ( balAcc, blkTxn ) => {
				      balAcc.get( blkTxn.sender ) match {
					case Some( balance ) => {
					  val balMinFee = ( balance - blkTxn.fee );
					  if ( balMinFee >= 0 ) {
					    balAcc + ( ( blkTxn.sender ) -> balMinFee )
					  }
					  else { balAcc }
					}
					case None => { balAcc }
				      }
				    }
				  }
				)
			      ConsensusManagerState(
				acc.ghostTable,
				acc.ghostDepth,
				acc.history,
				acc.bondedValidators,
				acc.minimumBond,
				acc.finalityThreshold,
				acc.evidenceChecker,
				acc.blockHashMap,	    
				newBal,
				newRev,
				lsh
			      )
			    }
			    case _ => {
			      acc
			    }
			  }
			}
			case None => {
			  throw new InvalidBlockException( txnNHeight )
			}
		      }
		    }
		    case None => {
		      throw new InvalidBlockException( txnNHeight )
		    }
		  }
		}
	      }
	    )
	  }
	  else {
	    throw new InvalidBlockException( txnNHeight )
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
        loopTest[ConsensusManagerStateT[Address,Data,Hash,Signature]](
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
                  loopTest(
		    validAppTxn,
		    ( true, reorgInitAppState ),
		    appStateFn,
		    block.reorgEntries.txns.map( ( e ) => ( e, block.height ) )
		  ) 
                appCond &&
                loopTest(
                  validCmgtTxn,
                  ( true, cmgtState ),
                  consensusManagerStateFn,
                  block.txns.map( ( e ) => ( e, block.height ) )
                )._1 && // fees
                loopTest(
		  validAppTxn,
		  ( true, appState ),
		  appStateFn,
		  block.txns.map( ( e ) => ( e, block.height ) )
		)._1            
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
