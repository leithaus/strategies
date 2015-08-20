// -*- mode: Scala;-*- 
// Filename:    casper.scala 
// Authors:     lgm                                                    
// Creation:    Thu Jul 23 07:05:15 2015 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package com.synereo.casper

trait BetT {
  def round : Int
  def prob : Double
}

case class Bet(
  override val round : Int,
  override val prob : Double
) extends BetT

trait ConsensusDataT[Address,Hash,Signature]
trait BlockT[Address,Data,Hash,Signature] extends ConsensusDataT[Address,Hash,Signature] {
  def ghostEntries : Seq[EntryT[Address,Data,Hash,Signature]]
  def reorgEntries : Seq[EntryT[Address,Data,Hash,Signature]]
  def txns : Seq[EntryT[Address,Data,Hash,Signature]]
  def signature : Signature
}

trait ValidationT[Address,Hash,Signature] extends ConsensusDataT[Address,Hash,Signature]

case class Validation[Address,Hash,Signature](
  bets : List[Bet],
  signature : Signature
) extends ValidationT[Address,Hash,Signature]
 
trait EvidenceT[Address,Hash,Signature] extends ConsensusDataT[Address,Hash,Signature]

case class Evidence[Address,Hash,Signature](
  address : Address,
  validations : List[ValidationT[Address,Hash,Signature]]
) extends EvidenceT[Address,Hash,Signature]

case class Block[Address,Data,Hash,Signature](
  override val ghostEntries : Seq[EntryT[Address,Data,Hash,Signature]],
  override val reorgEntries : Seq[EntryT[Address,Data,Hash,Signature]],
  override val txns : Seq[EntryT[Address,Data,Hash,Signature]],
  override val signature : Signature
) extends BlockT[Address,Data,Hash,Signature]

trait EntryT[Address,Data,Hash,Signature] {
  def prev : Hash
  def post : Hash
}

trait GhostT[Address,Data,Hash,Signature] extends EntryT[Address,Data,Hash,Signature]
trait ReorgT[Address,Data,Hash,Signature] extends EntryT[Address,Data,Hash,Signature]
trait TxnT[Address,Data,Hash,Signature] extends EntryT[Address,Data,Hash,Signature]

case class Ghost[Address,Data,Hash,Signature](
  override val prev : Hash,
  consensusData : ConsensusDataT[Address,Hash,Signature],
  override val post : Hash
) extends GhostT[Address,Data,Hash,Signature]

case class Reorg[Address,Data,Hash,Signature](
  override val prev : Hash,
  txn : List[TxnT[Address,Data,Hash,Signature]],
  override val post : Hash
) extends ReorgT[Address,Data,Hash,Signature]

case class TxnPayLoad[Address,Data,Signature](
  receiver : Address,
  data : Data,
  signature : Signature
)

case class Txn[Address,Data,Hash,Signature](
  override val prev : Hash,
  payload : TxnPayLoad[Address,Data,Signature],
  override val post : Hash
) extends TxnT[Address,Data,Hash,Signature]

trait GhostTableT
