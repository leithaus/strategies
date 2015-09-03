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
trait ConsensusManagerStateMapT[PrimHash,Address,Data,Hash,Signature]
 extends MapProxy[PrimHash,ConsensusManagerStateT[Address,Data,Hash,Signature]] 

trait ConsensusManagerStateT[Address,Data,Hash,Signature] {
  def ghostTable : GhostTableT[Address,Data,Hash,Signature]
  def history : GhostTableT[Address,Data,Hash,Signature] => Seq[TxnT[Address,Data,Hash,Signature]]
  def bondedValidators : Seq[Address]
  def evidenceChecker : Seq[ConsensusDataT[Address,Hash,Signature]] => Option[Address]
}

case class ConsensusManagerState[Address,Data,Hash,Signature](
  override val ghostTable : GhostTableT[Address,Data,Hash,Signature],
  override val history : GhostTableT[Address,Data,Hash,Signature] => Seq[TxnT[Address,Data,Hash,Signature]],
  override val bondedValidators : Seq[Address],
  override val evidenceChecker : Seq[ConsensusDataT[Address,Hash,Signature]] => Option[Address]
) extends ConsensusManagerStateT[Address,Data,Hash,Signature]
