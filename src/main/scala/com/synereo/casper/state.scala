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
trait BlockHashMapT[Address,Data,Hash,Signature]
 extends MapProxy[Hash,BlockT[Address,Data,Hash,Signature]] 

trait ConsensusManagerStateT[Address,Data,Hash,Signature] {
  def ghostTable : GhostTableT[Address,Data,Hash,Signature]
  def ghostDepth : Int  
  def history : GhostTableT[Address,Data,Hash,Signature] => Seq[TxnT[Address,Data,Hash,Signature]]
  def bondedValidators : Map[Address,Int]
  def minimumBond : Int
  def finalityThreshold : Double
  def evidenceChecker : Seq[ConsensusDataT[Address,Data,Hash,Signature]] => Option[Address]
  def blockHashMap : BlockHashMapT[Address,Data,Hash,Signature]
  def balances : Map[Address,Int] // BigInt?
  def revenues : Map[Int,Int] // BigInt?
  def lastStoredHeight : Int  
}

case class ConsensusManagerState[Address,Data,Hash,Signature](
  override val ghostTable : GhostTableT[Address,Data,Hash,Signature],
  override val ghostDepth : Int,
  override val history : GhostTableT[Address,Data,Hash,Signature] => Seq[TxnT[Address,Data,Hash,Signature]],
  override val bondedValidators : Map[Address,Int],
  override val minimumBond : Int,
  override val finalityThreshold : Double,
  override val evidenceChecker : Seq[ConsensusDataT[Address,Data,Hash,Signature]] => Option[Address],
  override val blockHashMap : BlockHashMapT[Address,Data,Hash,Signature],
  override val balances : Map[Address,Int], // BigInt?
  override val revenues : Map[Int,Int], // BigInt?
  override val lastStoredHeight : Int
) extends ConsensusManagerStateT[Address,Data,Hash,Signature]
