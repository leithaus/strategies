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

trait BetT[Hash] {
  def height : Int
  def blockHash : Hash
  def round : Int  
  def prob : Double 
  def timestamp : Date
}

case class Bet[Hash](
  override val height : Int,
  override val blockHash : Hash,
  override val round : Int,
  override val prob : Double,
  override val timestamp : Date
) extends BetT[Hash]

trait ConsensusDataT[Address,Data,Hash,Signature]
trait BlockT[Address,Data,Hash,Signature] extends ConsensusDataT[Address,Data,Hash,Signature] {
  def height : Int
  def timeStamp : Date
  def ghostEntries : Seq[EntryT[Address,Data,Hash,Signature]]
  def reorgEntries : ReorgT[Address,Data,Hash,Signature]
  def txns : Seq[EntryT[Address,Data,Hash,Signature]]
  def signature : Signature
}

trait ValidationT[Address,Data,Hash,Signature] extends ConsensusDataT[Address,Data,Hash,Signature]

case class Validation[Address,Data,Hash,Signature](
  bets : List[Bet[Hash]],
  signature : Signature
) extends ValidationT[Address,Data,Hash,Signature]
 
trait EvidenceT[Address,Data,Hash,Signature] extends ConsensusDataT[Address,Data,Hash,Signature]

case class Evidence[Address,Data,Hash,Signature](
  address : Address,
  validations : List[ValidationT[Address,Data,Hash,Signature]]
) extends EvidenceT[Address,Data,Hash,Signature]

case class Block[Address,Data,Hash,Signature](
  override val height : Int,
  override val timeStamp : Date,
  override val ghostEntries : Seq[EntryT[Address,Data,Hash,Signature]],
  override val reorgEntries : ReorgT[Address,Data,Hash,Signature],
  override val txns : Seq[EntryT[Address,Data,Hash,Signature]],
  override val signature : Signature
) extends BlockT[Address,Data,Hash,Signature]

trait EntryT[Address,Data,Hash,Signature] {
  def prev : Hash
  def post : Hash
}

case class TxnPayLoad[Address,Data,Signature](
  receiver : Address,
  data : Data,
  signature : Signature
)

trait GhostT[Address,Data,Hash,Signature] extends EntryT[Address,Data,Hash,Signature]
trait ReorgT[Address,Data,Hash,Signature] extends EntryT[Address,Data,Hash,Signature] {
  def txns : Seq[TxnT[Address,Data,Hash,Signature]]
}
trait TxnT[Address,Data,Hash,Signature] extends EntryT[Address,Data,Hash,Signature] {
  def payload : TxnPayLoad[Address,Data,Signature]
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
  override val post : Hash
) extends TxnT[Address,Data,Hash,Signature]

trait GhostTableT[Address,Data,Hash,Signature]
extends MapProxy[Int,Map[BlockT[Address,Data,Hash,Signature],Seq[Bet[Hash]]]]
