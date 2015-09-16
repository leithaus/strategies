// -*- mode: Scala;-*- 
// Filename:    state.scala 
// Authors:     lgm                                                    
// Creation:    Thu Aug 20 10:32:49 2015 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package com.synereo.casper

import scala.collection.mutable.MapProxy

import java.util.Date

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
  def bondedValidators : Map[Address,( Int, Int )]
  def nextBondedValidators : Map[Address,( Int, Int )]
  def bondRoundToInterval : Map[Int,(Date,Option[Date])]
  def lastSeen : Map[Address,Date]
  def minimumBond : Int
  def finalityThreshold : Double
  def evidenceChecker : Seq[ConsensusDataT[Address,Data,Hash,Signature]] => Option[Address]
  def blockHashMap : BlockHashMapT[Address,Data,Hash,Signature]
  def balances : Map[Address,Int] // BigInt?
  def revenues : Map[Int,Int] // BigInt?
  def lastStoredHeight : Int    
  def minStoredHeight : Int    
}

class ConsensusManagerState[Address,Data,Hash,Signature](
  override val ghostTable : GhostTableT[Address,Data,Hash,Signature],
  override val ghostDepth : Int,
  override val history : GhostTableT[Address,Data,Hash,Signature] => Seq[TxnT[Address,Data,Hash,Signature]],
  override val bondedValidators : Map[Address, ( Int, Int )],
  // BUGBUG -- LGM : since we only ever keep one interval we could
  // (and should?) break up the map into 1 Int for the round number,
  // and one (Date, Option[Date])
  override val nextBondedValidators : Map[Address, ( Int, Int )],
  override val bondRoundToInterval : Map[Int,(Date,Option[Date])],  
  override val lastSeen : Map[Address,Date],
  override val minimumBond : Int,
  override val finalityThreshold : Double,
  override val evidenceChecker : Seq[ConsensusDataT[Address,Data,Hash,Signature]] => Option[Address],
  override val blockHashMap : BlockHashMapT[Address,Data,Hash,Signature],
  override val balances : Map[Address,Int], // BigInt?
  override val revenues : Map[Int,Int], // BigInt?
  override val lastStoredHeight : Int,
  override val minStoredHeight : Int
) extends ConsensusManagerStateT[Address,Data,Hash,Signature]

object ConsensusManagerState {
  def apply[Address,Data,Hash,Signature](
    ghostTable : GhostTableT[Address,Data,Hash,Signature],
    ghostDepth : Int,
    history : GhostTableT[Address,Data,Hash,Signature] => Seq[TxnT[Address,Data,Hash,Signature]],
    bondedValidators : Map[Address,( Int, Int )],
    nextBondedValidators : Map[Address,( Int, Int )],
    bondRoundToInterval : Map[Int,( Date, Option[Date] )],
    lastSeen : Map[Address,Date],
    minimumBond : Int,
    finalityThreshold : Double,
    evidenceChecker : Seq[ConsensusDataT[Address,Data,Hash,Signature]] => Option[Address],
    blockHashMap : BlockHashMapT[Address,Data,Hash,Signature],
    balances : Map[Address,Int], // BigInt?
    revenues : Map[Int,Int], // BigInt?
    lastStoredHeight : Int,
    minStoredHeight : Int
  ) : ConsensusManagerState[Address,Data,Hash,Signature] = {
    new ConsensusManagerState[Address,Data,Hash,Signature](
      ghostTable,
      ghostDepth,
      history,
      bondedValidators,
      nextBondedValidators,
      bondRoundToInterval,
      lastSeen,
      minimumBond,
      finalityThreshold,
      evidenceChecker,
      blockHashMap,
      balances, // BigInt?
      revenues, // BigInt?
      lastStoredHeight,
      minStoredHeight
    )
  }
  def apply[Address,Data,Hash,Signature](
    oldState : ConsensusManagerState[Address,Data,Hash,Signature],
    ghostTable : GhostTableT[Address,Data,Hash,Signature]
  ) : ConsensusManagerState[Address,Data,Hash,Signature] = {
    new ConsensusManagerState[Address,Data,Hash,Signature](
      ghostTable,
      oldState.ghostDepth,
      oldState.history,
      oldState.bondedValidators,
      oldState.nextBondedValidators,
      oldState.bondRoundToInterval,
      oldState.lastSeen,
      oldState.minimumBond,
      oldState.finalityThreshold,
      oldState.evidenceChecker,
      oldState.blockHashMap,
      oldState.balances, // BigInt?
      oldState.revenues, // BigInt?
      oldState.lastStoredHeight,
      oldState.minStoredHeight
    )
  }
  def apply[Address,Data,Hash,Signature](
    oldState : ConsensusManagerState[Address,Data,Hash,Signature],
    history : GhostTableT[Address,Data,Hash,Signature] => Seq[TxnT[Address,Data,Hash,Signature]]
  ) : ConsensusManagerState[Address,Data,Hash,Signature] = {
    new ConsensusManagerState[Address,Data,Hash,Signature](
      oldState.ghostTable,
      oldState.ghostDepth,
      history,
      oldState.bondedValidators,
      oldState.nextBondedValidators,
      oldState.bondRoundToInterval,
      oldState.lastSeen,
      oldState.minimumBond,
      oldState.finalityThreshold,
      oldState.evidenceChecker,
      oldState.blockHashMap,
      oldState.balances, // BigInt?
      oldState.revenues, // BigInt?
      oldState.lastStoredHeight,
      oldState.minStoredHeight
    )
  }
  def apply[Address,Data,Hash,Signature](
    oldState : ConsensusManagerState[Address,Data,Hash,Signature],
    bondedValidators : Map[Address, ( Int, Int )]
  ) : ConsensusManagerState[Address,Data,Hash,Signature] = {
    new ConsensusManagerState[Address,Data,Hash,Signature](
      oldState.ghostTable,
      oldState.ghostDepth,
      oldState.history,
      bondedValidators,
      oldState.nextBondedValidators,
      oldState.bondRoundToInterval,
      oldState.lastSeen,
      oldState.minimumBond,
      oldState.finalityThreshold,
      oldState.evidenceChecker,
      oldState.blockHashMap,
      oldState.balances, // BigInt?
      oldState.revenues, // BigInt?
      oldState.lastStoredHeight,
      oldState.minStoredHeight
    )
  }
  def apply[Address,Data,Hash,Signature](
    oldState : ConsensusManagerState[Address,Data,Hash,Signature],
    blockHashMap : BlockHashMapT[Address,Data,Hash,Signature]
  ) : ConsensusManagerState[Address,Data,Hash,Signature] = {
    new ConsensusManagerState[Address,Data,Hash,Signature](
      oldState.ghostTable,
      oldState.ghostDepth,
      oldState.history,
      oldState.bondedValidators,
      oldState.nextBondedValidators,
      oldState.bondRoundToInterval,
      oldState.lastSeen,
      oldState.minimumBond,
      oldState.finalityThreshold,
      oldState.evidenceChecker,
      blockHashMap,
      oldState.balances, // BigInt?
      oldState.revenues, // BigInt?
      oldState.lastStoredHeight,
      oldState.minStoredHeight
    )
  }
  def apply[Address,Data,Hash,Signature](
    oldState : ConsensusManagerState[Address,Data,Hash,Signature],
    lastStoredHeight : Int // BigInt?
  ) : ConsensusManagerState[Address,Data,Hash,Signature] = {
    new ConsensusManagerState[Address,Data,Hash,Signature](
      oldState.ghostTable,
      oldState.ghostDepth,
      oldState.history,
      oldState.bondedValidators,
      oldState.nextBondedValidators,
      oldState.bondRoundToInterval,
      oldState.lastSeen,
      oldState.minimumBond,
      oldState.finalityThreshold,
      oldState.evidenceChecker,
      oldState.blockHashMap,
      oldState.balances, // BigInt?
      oldState.revenues, // BigInt?
      lastStoredHeight,
      oldState.minStoredHeight
    )
  }
  def unapply[Address,Data,Hash,Signature](
    cmgtState : ConsensusManagerState[Address,Data,Hash,Signature] 
  ) : Option[(
    GhostTableT[Address,Data,Hash,Signature],
    Int,
    GhostTableT[Address,Data,Hash,Signature] => Seq[TxnT[Address,Data,Hash,Signature]],
    Map[Address,( Int, Int )],
    Map[Address,( Int, Int )],
    Map[Int,(Date,Option[Date])],
    Map[Address,Date],
    Int,
    Double,
    Seq[ConsensusDataT[Address,Data,Hash,Signature]] => Option[Address],
    BlockHashMapT[Address,Data,Hash,Signature],
    Map[Address,Int], // BigInt?
    Map[Int,Int], // BigInt?
    Int,
    Int
  )]
  = {
    Some(
      (
	cmgtState.ghostTable,
	cmgtState.ghostDepth,
	cmgtState.history,
	cmgtState.bondedValidators,
	cmgtState.nextBondedValidators,
	cmgtState.bondRoundToInterval,
	cmgtState.lastSeen,
	cmgtState.minimumBond,
	cmgtState.finalityThreshold,
	cmgtState.evidenceChecker,
	cmgtState.blockHashMap,
	cmgtState.balances, // BigInt?
	cmgtState.revenues, // BigInt?
	cmgtState.lastStoredHeight,
	cmgtState.minStoredHeight
      )
    )
  }
}
