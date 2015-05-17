// -*- mode: Scala;-*- 
// Filename:    strategies.scala 
// Authors:     lgm                                                    
// Creation:    Sun May 10 03:29:13 2015 
// Copyright:   Not supplied 
// Description: 
//  The difference between Strategies and RBSets is that RBSets can
//  nest, whereas Strategies are strictly alternating.
// ------------------------------------------------------------------------

package com.biosimilarity.mdp4tw.strategies

trait RSets[+PQ,+OA,+OQ,+PA]
{
  type BS[+PQ,+OA,+OQ,+PA]
  trait WellBracketedBST[+PQ,+OA,+OQ,+PA] {
    def oq : OQ
    def set : BS[PQ,OA,OQ,PA]
    def pa : PA
  }
  trait RSetT[+PQ,+OA,+OQ,+PA] extends Strategy {
    def s : Stream[Either[WellBracketedBST[PQ,OA,OQ,PA],RSetT[PQ,OA,OQ,PA]]]
  }  
}

trait BSets[+PQ,+OA,+OQ,+PA] {
  type RS[+PQ,+OA,+OQ,+PA]
  trait WellBracketedRST[+PQ,+OA,+OQ,+PA] {
    def pq : PQ
    def set : RS[PQ,OA,OQ,PA]
    def oa : OA
  }
  trait BSetT[+PQ,+OA,+OQ,+PA] extends Strategy {
    def s : Stream[Either[WellBracketedRST[PQ,OA,OQ,PA],BSetT[PQ,OA,OQ,PA]]]
  }  
}

trait RBSets[+PQ,+OA,+OQ,+PA] extends RSets[PQ,OA,OQ,PA] with BSets[PQ,OA,OQ,PA]
{
  override type BS[+PQ,+OA,+OQ,+PA] = BSetT[PQ,OA,OQ,PA]
  override type RS[+PQ,+OA,+OQ,+PA] = RSetT[PQ,OA,OQ,PA]  
  
  case class WellBracketedBS[+PQ,+OA,+OQ,+PA](
    override val oq : OQ, override val set : BS[PQ,OA,OQ,PA], override val pa : PA
  ) extends WellBracketedBST[PQ,OA,OQ,PA] {
    override def toString() = {
      Utilities.setStreamToString( set.s, oq, pa )            
    }
  }
  case class RSet[+PQ,+OA,+OQ,+PA](
    override val s : Stream[Either[WellBracketedBST[PQ,OA,OQ,PA],RSetT[PQ,OA,OQ,PA]]]
  ) extends RSetT[PQ,OA,OQ,PA] {
    override def toString() = {
      Utilities.bracketedSetStreamToString( s )
    }
  }
  case class WellBracketedRS[+PQ,+OA,+OQ,+PA](
    override val pq : PQ, override val set : RS[PQ,OA,OQ,PA], override val oa : OA
  ) extends WellBracketedRST[PQ,OA,OQ,PA] {
    override def toString() = {
      Utilities.setStreamToString( set.s, pq, oa )                  
    }
  }
  case class BSet[+PQ,+OA,+OQ,+PA](
    override val s : Stream[Either[WellBracketedRST[PQ,OA,OQ,PA],BSetT[PQ,OA,OQ,PA]]]
  ) extends BSetT[PQ,OA,OQ,PA] {
    override def toString() = {
      Utilities.bracketedSetStreamToString( s )
    }
  }

  def show[PQ1 >: PQ,OA1 >: OA,OQ1 >: OQ, PA1 >: PA](
    tp : RSetT[PQ1,OA1,OQ1,PA1]
  ) : Unit = {
    val l = tp.s.length;
    println( tp );
    for( wbs <- tp.s ) {
      wbs match {
        case Left( atom ) => {
          show( atom.set )
        }
        case Right( set ) => {
          show( set )
        }
      }
    }
  }
  def show[PQ1 >: PQ,OA1 >: OA,OQ1 >: OQ, PA1 >: PA](
    to : BSetT[PQ1,OA1,OQ1,PA1]
  ) : Unit = {
    val l = to.s.length;
    println( to );
    for( wbs <- to.s ) {
      wbs match {
        case Left( atom ) => {
          show( atom.set )
        }
        case Right( set ) => {
          show( set )
        }
      }
    }
  }  
}


