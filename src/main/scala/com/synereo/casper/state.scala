// -*- mode: Scala;-*- 
// Filename:    state.scala 
// Authors:     lgm                                                    
// Creation:    Thu Aug 20 10:32:49 2015 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package com.synereo.casper

import scala.collection.mutable.MapProxy

trait AppStateMapT[Address,Data,Hash,Signature,AppState]
 extends MapProxy[Hash,AppState] 
trait ConsensusManagerStateMapT[Address,Data,Hash,Signature]
 extends MapProxy[Hash,ConsensusManagerStateT[Address,Data,Hash,Signature]] 

trait ConsensusManagerStateT[Address,Data,Hash,Signature] {
  def ghostTable : GhostTableT
  def history : GhostTableT => Seq[TxnT[Address,Data,Hash,Signature]]
  def bondedValidators : Seq[Address]
  def evidenceChecker : BlockT[Address,Data,Hash,Signature] => Boolean
}
