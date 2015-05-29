// -*- mode: Scala;-*- 
// Filename:    strategies.scala 
// Authors:     lgm                                                    
// Creation:    Sun May 10 03:29:13 2015 
// Copyright:   Not supplied 
// Description: 
//  The differences between Strategies and RBSets are that
//  1. RBSets can nest, whereas Strategies are strictly alternating;
//  2. RBSets are "rooted": the extension stream is always wrapped by brackets.
// ------------------------------------------------------------------------

package com.biosimilarity.mdp4tw.strategies

trait RSets[+PQ,+OA,+OQ,+PA]
{
  type BA[+PQ,+OA,+OQ,+PA]  
  trait RSetT[+PQ,+OA,+OQ,+PA] extends Strategy {
    def oq : OQ
    def s : Stream[Either[BA[PQ,OA,OQ,PA],RSetT[PQ,OA,OQ,PA]]]
    def pa : PA
  }  
}

trait BSets[+PQ,+OA,+OQ,+PA] {
  type RA[+PQ,+OA,+OQ,+PA]  
  trait BSetT[+PQ,+OA,+OQ,+PA] extends Strategy {
    def pq : PQ
    def s : Stream[Either[RA[PQ,OA,OQ,PA],BSetT[PQ,OA,OQ,PA]]]
    def oa : OA
  }  
}

trait RBSets[+PQ,+OA,+OQ,+PA] extends RSets[PQ,OA,OQ,PA] with BSets[PQ,OA,OQ,PA]
{
  override type BA[+PQ,+OA,+OQ,+PA] = BSetT[PQ,OA,OQ,PA]
  override type RA[+PQ,+OA,+OQ,+PA] = RSetT[PQ,OA,OQ,PA]  

  case class RSet[+PQ,+OA,+OQ,+PA](
    override val oq : OQ,
    override val s : Stream[Either[BA[PQ,OA,OQ,PA],RSetT[PQ,OA,OQ,PA]]],
    override val pa : PA
  ) extends RSetT[PQ,OA,OQ,PA] {
    override def toString() = {
      Utilities.setStreamToString( s, oq, pa )
    }
  }
  case class BSet[+PQ,+OA,+OQ,+PA](
    override val pq : PQ,
    override val s : Stream[Either[RA[PQ,OA,OQ,PA],BSetT[PQ,OA,OQ,PA]]],
    override val oa : OA
  ) extends BSetT[PQ,OA,OQ,PA] {
    override def toString() = {
      Utilities.setStreamToString( s, pq, oa )
    }
  }

  def show[PQ1 >: PQ,OA1 >: OA,OQ1 >: OQ, PA1 >: PA](
    rset : RSetT[PQ1,OA1,OQ1,PA1]
  ) : Unit = {
    val l = rset.s.length;
    println( rset );
    for( wbs <- rset.s ) {
      wbs match {
        case Left( atom ) => {
          show( atom )
        }
        case Right( set ) => {
          show( set )
        }
      }
    }
  }
  def show[PQ1 >: PQ,OA1 >: OA,OQ1 >: OQ, PA1 >: PA](
    bset : BSetT[PQ1,OA1,OQ1,PA1]
  ) : Unit = {
    val l = bset.s.length;
    println( bset );
    for( wbs <- bset.s ) {
      wbs match {
        case Left( atom ) => {
          show( atom )
        }
        case Right( set ) => {
          show( set )
        }
      }
    }
  }  
}


