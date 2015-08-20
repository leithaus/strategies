// -*- mode: Scala;-*- 
// Filename:    validator.scala 
// Authors:     lgm                                                    
// Creation:    Thu Aug 20 10:31:47 2015 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package com.synereo.casper

trait ValidityCheckUtilsT[Address,Data,Hash,Signature,AppState,Timer] {
  type StateFn[State] = ( State, TxnT[Address,Data,Hash,Signature] ) => State
  def hash[State] : State => Hash
  def validTxn[State](
    state : State,
    transitionFn : StateFn[State],
    txn : TxnT[Address,Data,Hash,Signature]
  ) : ( Boolean, State ) = {
    val nxtState = transitionFn( state, txn )
    ( 
      ( txn.prev == hash( state ) ) && ( txn.post == hash( nxtState ) ),
      nxtState 
    )    
  }
  def loopTest[State](
    testFn : ( State, StateFn[State], TxnT[Address,Data,Hash,Signature] ) => ( Boolean, State ),
    acc : ( Boolean, State ),
    stateFn : StateFn[State],
    candidates : Seq[TxnT[Address,Data,Hash,Signature]]
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
}

trait ValidatorT[Address,Data,Hash,Signature,AppState,Timer] {
  def initConsensusManagerState : ConsensusManagerStateT[Address,Data,Hash,Signature]
  def initAppState : AppState
  def consensusManagerStateMap : ConsensusManagerStateMapT[Address,Data,Hash,Signature]
  def appStateMap : AppStateMapT[Address,Data,Hash,Signature,AppState]
  def timer : Timer
  def valid( block : BlockT[Address,Data,Hash,Signature] ) : Boolean
}
