// -*- mode: Scala;-*- 
// Filename:    validator.scala 
// Authors:     lgm                                                    
// Creation:    Thu Aug 20 10:31:47 2015 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package com.synereo.casper

trait StateFnT[State,Address,Data,Hash,Signature] 
extends Function2[State,EntryT[Address,Data,Hash,Signature],State]

trait ValidityCheckUtilsT[Address,Data,Hash,Signature,AppState,Timer] {
  def hash[State] : State => Hash
  def validTxn[State](
    state : State,
    transitionFn : StateFnT[State,Address,Data,Hash,Signature],
    txn : EntryT[Address,Data,Hash,Signature]
  ) : ( Boolean, State ) = {
    val nxtState = transitionFn( state, txn )
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

trait ValidatorT[Address,Data,PrimHash,Hash <: Tuple2[PrimHash,PrimHash],Signature,AppState,Timer]
  extends ValidityCheckUtilsT[Address,Data,Hash,Signature,AppState,Timer]
{
  def initConsensusManagerState : ConsensusManagerStateT[Address,Data,Hash,Signature]
  def initAppState : AppState
  def consensusManagerStateMap : ConsensusManagerStateMapT[PrimHash,Address,Data,Hash,Signature]
  def appStateMap : AppStateMapT[Address,Data,PrimHash,Signature,AppState]
  def timer : Timer
  def consensusManagerStateFn : StateFnT[ConsensusManagerStateT[Address,Data,Hash,Signature],Address,Data,Hash,Signature] 
  def appStateFn : StateFnT[AppState,Address,Data,Hash,Signature] 

  val validCmgtTxn = validTxn[ConsensusManagerStateT[Address,Data,Hash,Signature]] _
  val validAppTxn = validTxn[AppState] _
        

  def valid( block : BlockT[Address,Data,Hash,Signature] ) : Boolean = {
    val ( cmgtSH, appSH ) = ( block.ghostEntries( 1 ).prev );
    val ( cmgtS, appS ) = ( consensusManagerStateMap( cmgtSH ), appStateMap( appSH ) );
    val ( initGhostTable, initHistory ) = ( cmgtS.ghostTable, cmgtS.history );

    val ( ghostCond, ghostState ) =
      loopTest[ConsensusManagerStateT[Address,Data,Hash,Signature]](
        validTxn[ConsensusManagerStateT[Address,Data,Hash,Signature]],
        ( true, cmgtS ),
        consensusManagerStateFn,
        block.ghostEntries
      )

    (
      ghostCond &&
      {
        val ( ghostGhostTable, ghostHistory ) =
          ( ghostState.ghostTable, ghostState.history );
        val initTxnSeq = initHistory( initGhostTable );
        val ghostTxnSeq = ghostHistory( ghostGhostTable );    
        val pos = initialDifference( ghostTxnSeq, initTxnSeq );
        val reorgGhostTxnSeq = ghostTxnSeq.drop( pos );
        val reorgInitAppState = appStateMap( ghostTxnSeq( pos - 1 ).post._2 )
        
        (
          // reorg validity check
          ( reorgGhostTxnSeq.map( _.payload ) == block.reorgEntries.txns.map( _.payload ) ) &&
          loopTest( validCmgtTxn, ( true, cmgtS ), consensusManagerStateFn, block.reorgEntries.txns )._1 && 
          loopTest( validAppTxn, ( true, reorgInitAppState ), appStateFn, block.reorgEntries.txns )._1 && 
          loopTest( validCmgtTxn, ( true, cmgtS ), consensusManagerStateFn, block.txns )._1 && // fees
          loopTest( validAppTxn, ( true, appS ), appStateFn, block.txns )._1            
        )
      }
    )      
  }
}
