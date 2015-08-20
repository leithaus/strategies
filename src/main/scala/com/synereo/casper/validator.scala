// -*- mode: Scala;-*- 
// Filename:    validator.scala 
// Authors:     lgm                                                    
// Creation:    Thu Aug 20 10:31:47 2015 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package com.synereo.casper

trait ValidatorT[Address,Data,Hash,Signature,AppState,Timer] {
  def initConsensusManagerState : ConsensusManagerStateT[Address,Data,Hash,Signature]
  def initAppState : AppState
  def consensusManagerStateMap : ConsensusManagerStateMapT[Address,Data,Hash,Signature]
  def appStateMap : AppStateMapT[Address,Data,Hash,Signature,AppState]
  def timer : Timer
  def valid( block : BlockT[Address,Data,Hash,Signature] ) : Boolean
}
