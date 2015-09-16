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

import java.util.Date
import java.util.concurrent.TimeUnit

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
      ( 0 /: cmgtState.bondedValidators )( ( acc, vbpair ) => { acc + vbpair._2._1 } )
    val committedBonds =
      ( 0 /: bets )(
	{
	  ( acc, bet ) => {
	    if ( bet.prob > .9999 ) {
	      cmgtState.bondedValidators.get( bet.validator ) match {
		case Some( ( bond, unbond ) ) => {
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
  def timeFromGhostTable(
    cmgtState : ConsensusManagerStateT[Address,Data,Hash,Signature]
  ) : Date
  def selectionPeriod(
    cmgtState : ConsensusManagerStateT[Address,Data,Hash,Signature],
    date : Date
  ) : Boolean
  def selectionPeriod(
    cmgtState : ConsensusManagerStateT[Address,Data,Hash,Signature]    
  ) : Boolean = {
    selectionPeriod( cmgtState, timeFromGhostTable( cmgtState ) )
  }
  def bondRound(
    cmgtState : ConsensusManagerStateT[Address,Data,Hash,Signature]    
  ) : Int
  def bondingPeriod(
    cmgtState : ConsensusManagerStateT[Address,Data,Hash,Signature]
  ) : Int
  def deviationFromOrder(
    cmgtState : ConsensusManagerStateT[Address,Data,Hash,Signature]
  ) : Double
  def percentageOfBond(
    cmgtState : ConsensusManagerStateT[Address,Data,Hash,Signature],
    validator : Address
  ) : Double
  def delayPenalty(
    cmgtState : ConsensusManagerStateT[Address,Data,Hash,Signature],
    validator : Address
  ) : Double
  def betConvergence(
    cmgtState : ConsensusManagerStateT[Address,Data,Hash,Signature]
  ) : Double  
  def roundPayment(
    feeAsDouble : Double
  ) : Int
  def timeOut(
    cmgtState : ConsensusManagerStateT[Address,Data,Hash,Signature],
    validator : Address,
    costOfCensoring : Int,
    revenueForCensoring : Int,
    discountRate : Int
  ) : Boolean = {
    val a =
      scala.math.log(
	costOfCensoring / ( costOfCensoring + revenueForCensoring )
      )
    val b = scala.math.log( discountRate )
    val timeOutInterval = scala.math.ceil( ( a / b ) - 1 )
    cmgtState.lastSeen.get( validator ) match {
      case Some( timeLastSeen ) => {
	val currentTime = new Date()
	val inactivity =
	  TimeUnit.HOURS.convert(
	    currentTime.getTime - timeLastSeen.getTime,
	    TimeUnit.MILLISECONDS
	  )
	inactivity > timeOutInterval
      }
      case None => {
	throw new InvalidBlockException( validator )
      }
    }    
  }
  def paymentToValidator(
    cmgtState : ConsensusManagerStateT[Address,Data,Hash,Signature],
    validator : Address,
    feePool : Int
  ) : Int = {
    roundPayment(      
      deviationFromOrder( cmgtState )
      * percentageOfBond( cmgtState, validator )
      * delayPenalty( cmgtState, validator )
      * betConvergence( cmgtState )
      * feePool.toDouble
    )
  }
  def payValidators(
    cmgtState : ConsensusManagerStateT[Address,Data,Hash,Signature],
    revenue : Int 
  ) : ConsensusManagerStateT[Address,Data,Hash,Signature]
  def consensusManagerStateFn : StateFnT[ConsensusManagerStateT[Address,Data,Hash,Signature],Address,Data,Hash,Signature]
  = new StateFnT[ConsensusManagerStateT[Address,Data,Hash,Signature],Address,Data,Hash,Signature]
  {
    def apply(
      state : ConsensusManagerStateT[Address,Data,Hash,Signature],
      txnNHeight : ( EntryT[Address,Data,Hash,Signature], Int )
    ) : ConsensusManagerStateT[Address,Data,Hash,Signature] = {
      def payFeesForBlock(
	acc : ConsensusManagerStateT[Address,Data,Hash,Signature],
	blk : BlockT[Address,Data,Hash,Signature],
	currHeight : Int
      ) : ConsensusManagerStateT[Address,Data,Hash,Signature] = {
	val newRevAtH = 
	  ( 0 /: blk.txns.map( _.fee ) )( _ + _ )
	val newRev = acc.revenues + ( currHeight -> newRevAtH )
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
	val newAcc = payValidators( acc, newRevAtH )
	ConsensusManagerState(
	  newAcc.ghostTable,
	  newAcc.ghostDepth,
	  newAcc.history,
	  newAcc.bondedValidators,
	  newAcc.nextBondedValidators,
	  newAcc.bondRoundToInterval,
	  newAcc.lastSeen,
	  newAcc.minimumBond,
	  newAcc.finalityThreshold,
	  newAcc.evidenceChecker,
	  newAcc.blockHashMap,	    
	  newBal,
	  newRev,
	  currHeight,
	  newAcc.minStoredHeight
	)
      }

      def prune(
	s : ConsensusManagerStateT[Address,Data,Hash,Signature],
	lsh : Int
      ) : ConsensusManagerStateT[Address,Data,Hash,Signature] = {	    	    
	val newBlkHMap =
	  ( s.blockHashMap /: s.ghostTable.getOrElse( lsh, throw new InvalidBlockException( lsh ) ) )(
	    {
	      ( acc, blkBetPair ) => {
		( acc - hash( blkBetPair._1 ) ).asInstanceOf[BlockHashMapT[Address,Data,Hash,Signature]]
	      }
	    }
	  )
	ConsensusManagerState(
          ( s.ghostTable - lsh ).asInstanceOf[GhostTableT[Address,Data,Hash,Signature]],
	  s.ghostDepth,		
          s.history,
          s.bondedValidators,
	  s.nextBondedValidators,
	  s.bondRoundToInterval,
	  s.lastSeen,
	  s.minimumBond,
	  s.finalityThreshold,
          s.evidenceChecker,
	  newBlkHMap,
	  s.balances,
	  ( s.revenues - lsh ),
	  lsh + 1,
	  s.minStoredHeight
	)
      }

      def pruneLoop(
	acc : ConsensusManagerStateT[Address,Data,Hash,Signature],
	h : Int 
      ) : ConsensusManagerStateT[Address,Data,Hash,Signature] = {
	if ( isBlockFinal( acc, acc.lastStoredHeight ) ) {	  
	  pruneLoop( prune( acc, h ), h + 1 )
	}
	else {
	  acc
	}
      }

      val ( txn : EntryT[Address,Data,Hash,Signature], height ) = txnNHeight
      txn match {
        case Ghost( _, cd, _ ) => {
          cd match {
            case blk@Block( h, _, _, _, _, _, _, _, _ ) => {
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
		    state.nextBondedValidators,
		    state.bondRoundToInterval,
		    state.lastSeen,
		    state.minimumBond,
		    state.finalityThreshold,
                    state.evidenceChecker,
		    state.blockHashMap,
		    state.balances,
		    state.revenues,
		    state.lastStoredHeight,
		    state.minStoredHeight
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
		      state.nextBondedValidators,
		      state.bondRoundToInterval,
		      state.lastSeen,
		      state.minimumBond,
		      state.finalityThreshold,
                      state.evidenceChecker,
		      state.blockHashMap,
		      state.balances,
		      state.revenues,
		      state.lastStoredHeight,
		      state.minStoredHeight
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
		    state.nextBondedValidators,
		    state.bondRoundToInterval,
		    state.lastSeen,
		    state.minimumBond,
		    state.finalityThreshold,
                    state.evidenceChecker,
		    state.blockHashMap,
		    state.balances,
		    state.revenues,
		    state.lastStoredHeight,
		    state.minStoredHeight
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
		state.nextBondedValidators,
		state.bondRoundToInterval,
		state.lastSeen,
		state.minimumBond,
		state.finalityThreshold,
                state.evidenceChecker,
		state.blockHashMap,
		state.balances,
		state.revenues,
		state.lastStoredHeight,
		state.minStoredHeight
              )
            }
            case _ => {
              throw new Exception( "tbd" )
            }
          }
        }
	case bnd@Bond( _, payload, _ ) => {
	  if ( selectionPeriod( state ) ) {
	    val bond = payload.bond	    
	    if (
	      ( bond >= state.minimumBond )
	      && ( payload.bondPeriod == bondRound( state ) + 1 )
	    ) {
	      state.balances.get( payload.bonder ) match {
		case Some( balance ) => {
		  if ( bond <= balance ) {
		    val newBalances =
		      state.balances + ( payload.bonder -> ( balance - bond ) )
		    val newNextBondedValidators =
		      state.nextBondedValidators + ( payload.validator -> ( bond, 0 ) )

		    ConsensusManagerState(
		      state.ghostTable,
		      state.ghostDepth,
		      state.history,
		      state.bondedValidators,
		      newNextBondedValidators,
		      state.bondRoundToInterval,
		      state.lastSeen,
		      state.minimumBond,
		      state.finalityThreshold,
		      state.evidenceChecker,
		      state.blockHashMap,
		      newBalances,
		      state.revenues,
		      state.lastStoredHeight,
		      state.minStoredHeight
		    )
		  }
		  else {
		    throw new InvalidBlockException( bnd )
		  }
		}
		case None => {
		  throw new InvalidBlockException( bnd )
		}
	      }	      
	    }
	    else {
	      throw new InvalidBlockException( bnd )
	    }
	  }
	  else {
	    throw new InvalidBlockException( bnd )
	  }
	}
	case unbnd@Unbond( _, payload, _ ) => {
	  if ( 
	    selectionPeriod( state )
	    && ( payload.bondPeriod == ( bondRound( state ) - 1 ) )
	  ) {
	    payload.bondWithdrawal match {
	      case Some( withdrawal ) => {		
		state.balances.get( payload.validator ) match {
		  case Some( balance ) => {
		    if ( withdrawal < balance ) {
		      val newBalances = 
			state.balances + ( payload.validator -> ( balance - withdrawal ) )
		      val newNextBondedValidators =
			( state.nextBondedValidators - payload.validator )

		      ConsensusManagerState(
			state.ghostTable,
			state.ghostDepth,
			state.history,
			state.bondedValidators,
			newNextBondedValidators,
			state.bondRoundToInterval,
			state.lastSeen,
			state.minimumBond,
			state.finalityThreshold,
			state.evidenceChecker,
			state.blockHashMap,
			newBalances,
			state.revenues,
			state.lastStoredHeight,
			state.minStoredHeight
		      )
		    }
		    else {
		      ConsensusManagerState(
			state.ghostTable,
			state.ghostDepth,
			state.history,
			state.bondedValidators,
			( state.nextBondedValidators - payload.validator ),
			state.bondRoundToInterval,
			state.lastSeen,
			state.minimumBond,
			state.finalityThreshold,
			state.evidenceChecker,
			state.blockHashMap,
			state.balances,
			state.revenues,
			state.lastStoredHeight,
			state.minStoredHeight
		      )
		    }
		  }
		  case None => {
		    throw new InvalidBlockException( unbnd )
		  }
		}
	      }
	      case None => {
		ConsensusManagerState(
		  state.ghostTable,
		  state.ghostDepth,
		  state.history,
		  state.bondedValidators,
		  ( state.nextBondedValidators - payload.validator ),
		  state.bondRoundToInterval,
		  state.lastSeen,
		  state.minimumBond,
		  state.finalityThreshold,
		  state.evidenceChecker,
		  state.blockHashMap,
		  state.balances,
		  state.revenues,
		  state.lastStoredHeight,
		  state.minStoredHeight
		)
	      }
	    }	    
	  }
	  else {
	    throw new InvalidBlockException( unbnd )
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
			  ( isBlockFinal( acc, currHeight ), blkRevenues ) match {
			    case ( true, None ) => {
			      payFeesForBlock( acc, blk, currHeight )
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
	case PruneGhostTable( prev, post ) => {
	  val lsh = state.lastStoredHeight
	  if ( ( height - lsh ) > state.ghostDepth ) {
	    (
	      for(
		map <- state.ghostTable.get( lsh );
		blk <- winner( map )
	      ) yield {	    	    
		prune( payFeesForBlock( state, blk, lsh ), lsh )
	      }
	    ) match {
	      case Some( s ) => s
	      case None => throw new InvalidBlockException( lsh )
	    }
	  }
	  else {
	    pruneLoop( state, state.lastStoredHeight )	    
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
