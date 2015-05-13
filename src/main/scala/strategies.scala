// -*- mode: Scala;-*- 
// Filename:    strategies.scala 
// Authors:     lgm                                                    
// Creation:    Sun May 10 03:29:13 2015 
// Copyright:   Not supplied 
// Description: 
//   A winning player strategy (WPS) is a sequence of well-bracketed winning
//   opponent strategies.
//   A winning opponent strategy (WOS) is a sequence of well-bracketed
//   winning player strategies.
//
//   In symbols
//   WPS ::= ( WBOS )*
//   WOS ::= ( WBPS )*
//
//   WBOS ::= "(" WOS ")"
//   WBPS ::= "[" WPS "]"
//  
//   We interpret these as denoting successful sessions.
// ------------------------------------------------------------------------

package com.biosimilarity.mdp4tw.strategies

// These are the basic elements of our framework
trait Move
trait Opening
trait Closing

trait Player
trait Opponent

trait Strategy

// They combine to give us a specification of basic actions
trait PlayerQuestionT extends Move with Opening with Player     // (
trait OpponentAnswerT extends Move with Closing with Opponent   // )

trait OpponentQuestionT extends Move with Opening with Opponent // [
trait PlayerAnswerT extends Move with Closing with Player       // ]

// The specification of basic agents, aka a strategy, is parametric in
// a specification of basic actions

// Further, a Player strategy is parametric in an Opponent strategy
trait PlayerStrategies[+PQ,+OA,+OQ,+PA]
{
  type WOS[+PQ,+OA,+OQ,+PA] <: OpponentStrategies[PQ,OA,OQ,PA]#WinningOpponentStrategyT[PQ,OA,OQ,PA]
  trait WellBracketedWOST[+PQ,+OA,+OQ,+PA] {
    def oq : OQ
    def strategy : WOS[PQ,OA,OQ,PA]
    def pa : PA
  }
  trait WinningPlayerStrategyT[+PQ,+OA,+OQ,+PA] extends Strategy {
    def s : Stream[WellBracketedWOST[PQ,OA,OQ,PA]]
  }  
}

// While an Opponent strategy is parametric in a Player strategy
trait OpponentStrategies[+PQ,+OA,+OQ,+PA] {
  type WPS[+PQ,+OA,+OQ,+PA] <: PlayerStrategies[PQ,OA,OQ,PA]#WinningPlayerStrategyT[PQ,OA,OQ,PA]
  trait WellBracketedWPST[+PQ,+OA,+OQ,+PA] {
    def pq : PQ
    def strategy : WPS[PQ,OA,OQ,PA]
    def oa : OA
  }
  trait WinningOpponentStrategyT[+PQ,+OA,+OQ,+PA] {
    def s : Stream[WellBracketedWPST[PQ,OA,OQ,PA]]
  }  
}

// We can tie the recursive knot to get a pair of interdependent
// agents, yet still parametric in some notion of basic actions
trait GameStrategies[+PQ,+OA,+OQ,+PA]
       extends PlayerStrategies[PQ,OA,OQ,PA]
       with OpponentStrategies[PQ,OA,OQ,PA]
{
  override type WOS[+PQ,+OA,+OQ,+PA] =
    OpponentStrategies[PQ,OA,OQ,PA]#WinningOpponentStrategyT[PQ,OA,OQ,PA]
  override type WPS[+PQ,+OA,+OQ,+PA] =
    PlayerStrategies[PQ,OA,OQ,PA]#WinningPlayerStrategyT[PQ,OA,OQ,PA]  
  
  case class WellBracketedWOS[+PQ,+OA,+OQ,+PA](
    override val oq : OQ, override val strategy : WOS[PQ,OA,OQ,PA], override val pa : PA
  ) extends WellBracketedWOST[PQ,OA,OQ,PA] {
    override def toString() = {
      Utilities.strategyStreamToString( strategy.s, oq, pa )            
    }
  }
  case class WinningPlayerStrategy[+PQ,+OA,+OQ,+PA](
    override val s : Stream[WellBracketedWOST[PQ,OA,OQ,PA]]
  ) extends WinningPlayerStrategyT[PQ,OA,OQ,PA] {
    override def toString() = {
      Utilities.bracketedStrategyStreamToString( s )
    }
  }
  case class WellBracketedWPS[+PQ,+OA,+OQ,+PA](
    override val pq : PQ, override val strategy : WPS[PQ,OA,OQ,PA], override val oa : OA
  ) extends WellBracketedWPST[PQ,OA,OQ,PA] {
    override def toString() = {
      Utilities.strategyStreamToString( strategy.s, pq, oa )                  
    }
  }
  case class WinningOpponentStrategy[+PQ,+OA,+OQ,+PA](
    override val s : Stream[WellBracketedWPST[PQ,OA,OQ,PA]]
  ) extends WinningOpponentStrategyT[PQ,OA,OQ,PA] {
    override def toString() = {
      Utilities.bracketedStrategyStreamToString( s )
    }
  }
}


