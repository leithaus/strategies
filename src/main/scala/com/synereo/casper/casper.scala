// -*- mode: Scala;-*- 
// Filename:    casper.scala 
// Authors:     lgm                                                    
// Creation:    Thu Jul 23 07:05:15 2015 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package com.synereo.casper

import scala.collection.mutable.Map
import scala.collection.mutable.MapProxy

import java.util.Date

trait BetT[Address,Hash] {
  def validator : Address
  def height : Int
  def blockHash : Hash
  def round : Int  
  def prob : Double 
  def timestamp : Date
}

case class Bet[Address,Hash](
  override val validator : Address,
  override val height : Int,
  override val blockHash : Hash,
  override val round : Int,
  override val prob : Double,
  override val timestamp : Date
) extends BetT[Address,Hash]

trait ConsensusDataT[Address,Data,Hash,Signature]
trait BlockT[Address,Data,Hash,Signature] extends ConsensusDataT[Address,Data,Hash,Signature] {
  def height : Int
  def timeStamp : Date
  def ghostEntries : Seq[EntryT[Address,Data,Hash,Signature]]
  def feeDistribution : Option[FeeDistributionT[Address,Data,Hash,Signature]]
  def pruning : Option[PruneGhostTableT[Address,Data,Hash,Signature]]
  def bondUnbond : Seq[BondStatusT[Address,Data,Hash,Signature] with EntryT[Address,Data,Hash,Signature]]
  def reorgEntries : ReorgT[Address,Data,Hash,Signature]
  def txns : Seq[TxnT[Address,Data,Hash,Signature]]
  def signature : Signature
}

trait ValidationT[Address,Data,Hash,Signature]
     extends ConsensusDataT[Address,Data,Hash,Signature]
{
  def bets : List[Bet[Address,Hash]]
  def signature : Signature
}

case class Validation[Address,Data,Hash,Signature](
  override val bets : List[Bet[Address,Hash]],
  override val signature : Signature
) extends ValidationT[Address,Data,Hash,Signature]
 
trait EvidenceT[Address,Data,Hash,Signature]
     extends ConsensusDataT[Address,Data,Hash,Signature]
{
  def address : Address
  def validations : List[ValidationT[Address,Data,Hash,Signature]]
}

case class Evidence[Address,Data,Hash,Signature](
  override val address : Address,
  override val validations : List[ValidationT[Address,Data,Hash,Signature]]
) extends EvidenceT[Address,Data,Hash,Signature]

case class Block[Address,Data,Hash,Signature](
  override val height : Int,
  override val timeStamp : Date,
  override val ghostEntries : Seq[EntryT[Address,Data,Hash,Signature]],
  override val feeDistribution : Option[FeeDistributionT[Address,Data,Hash,Signature]],
  override val pruning : Option[PruneGhostTableT[Address,Data,Hash,Signature]],
  override val bondUnbond : Seq[BondStatusT[Address,Data,Hash,Signature] with EntryT[Address,Data,Hash,Signature]],
  override val reorgEntries : ReorgT[Address,Data,Hash,Signature],
  override val txns : Seq[TxnT[Address,Data,Hash,Signature]],
  override val signature : Signature
) extends BlockT[Address,Data,Hash,Signature]

trait EntryT[Address,Data,Hash,Signature] {
  def prev : Hash
  def post : Hash
}

trait PaidServiceT {
  def fee : Int
}

case class TxnPayLoad[Address,Data,Signature](
  sender : Address,
  receiver : Address,
  data : Data,
  override val fee : Int,
  signature : Signature  
) extends PaidServiceT

trait GhostT[Address,Data,Hash,Signature] extends EntryT[Address,Data,Hash,Signature]
trait ReorgT[Address,Data,Hash,Signature]
     extends EntryT[Address,Data,Hash,Signature] {
  def txns : Seq[TxnT[Address,Data,Hash,Signature]]
}
trait TxnT[Address,Data,Hash,Signature]
     extends EntryT[Address,Data,Hash,Signature] with PaidServiceT {
       def payload : TxnPayLoad[Address,Data,Signature]
       def sender : Address = payload.sender
       override def fee : Int = payload.fee
}

case class Ghost[Address,Data,Hash,Signature](
  override val prev : Hash,
  consensusData : ConsensusDataT[Address,Data,Hash,Signature],
  override val post : Hash
) extends GhostT[Address,Data,Hash,Signature]

case class Reorg[Address,Data,Hash,Signature](
  override val prev : Hash,
  override val txns : Seq[TxnT[Address,Data,Hash,Signature]],
  override val post : Hash
) extends ReorgT[Address,Data,Hash,Signature]

case class Txn[Address,Data,Hash,Signature](
  override val prev : Hash,
  override val payload : TxnPayLoad[Address,Data,Signature],
  override val sender : Address,
  override val fee : Int,
  override val post : Hash
) extends TxnT[Address,Data,Hash,Signature] 

trait BondPayLoadT[Address,Signature]
 extends PaidServiceT {
   def validator : Address
   def bonder : Address 
   def bond : Int 
   def bondPeriod : Int 
   def signature : Signature
 }

trait UnbondPayLoadT[Address,Signature] {
  def validator : Address
  def bondPeriod : Int 
  def bondWithdrawal : Option[Int] 
  def signature : Signature
}

case class BondPayLoad[Address,Signature](
  override val validator : Address,
  override val bonder : Address,
  override val bond : Int,
  override val bondPeriod : Int,
  override val fee : Int,
  override val signature : Signature  
) extends BondPayLoadT[Address,Signature] 

case class UnbondPayLoad[Address,Signature](
  override val validator : Address,
  override val bondPeriod : Int,
  override val bondWithdrawal : Option[Int],
  override val signature : Signature
) extends UnbondPayLoadT[Address,Signature] 

trait BondStatusT[Address,Data,Hash,Signature]
trait BondT[Address,Data,Hash,Signature]
     extends EntryT[Address,Data,Hash,Signature]
     with BondStatusT[Address,Data,Hash,Signature] {
       def bondPayLoad : BondPayLoadT[Address,Signature]
}  
trait UnbondT[Address,Data,Hash,Signature]
     extends EntryT[Address,Data,Hash,Signature]
     with BondStatusT[Address,Data,Hash,Signature] {
       def unbondPayLoad : UnbondPayLoadT[Address,Signature]
}  

case class Bond[Address,Data,Hash,Signature](
  override val prev : Hash,
  override val bondPayLoad : BondPayLoadT[Address,Signature],
  override val post : Hash
) extends BondT[Address,Data,Hash,Signature]

case class Unbond[Address,Data,Hash,Signature](
  override val prev : Hash,
  override val unbondPayLoad : UnbondPayLoadT[Address,Signature],
  override val post : Hash
) extends UnbondT[Address,Data,Hash,Signature] 

trait FeeDistributionT[Address,Data,Hash,Signature]
     extends EntryT[Address,Data,Hash,Signature] {
}

trait PruneGhostTableT[Address,Data,Hash,Signature]
     extends EntryT[Address,Data,Hash,Signature] {
} 

case class FeeDistribution[Address,Data,Hash,Signature](
  override val prev : Hash,
  override val post : Hash
) extends FeeDistributionT[Address,Data,Hash,Signature]

case class PruneGhostTable[Address,Data,Hash,Signature](
  override val prev : Hash,
  override val post : Hash
) extends PruneGhostTableT[Address,Data,Hash,Signature]

trait GhostTableT[Address,Data,Hash,Signature]
extends MapProxy[Int,Map[BlockT[Address,Data,Hash,Signature],Seq[Bet[Address,Hash]]]]
