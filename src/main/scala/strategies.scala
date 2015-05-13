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
//   We interpret these as denoting successful sessions. For example,
//   to interpret HTTP we think of Player as the client and Opponent
//   as the server; thus, "(" is taken as an outgoing HTTP GET request
//   (i.e. the request as seen from the client code), and ")" as an
//   incoming HTTP GET server response (i.e. the response as seen from
//   the client code ). Then, the trace of a non-crashing HTTP client
//   is ()()() ... (), and this trace clearly inhabits WPS.
//
//   Similarly, if we take "[" as the incoming HTTP GET request
//   (i.e. the request as seen from the server code) and "]" as the
//   outgoing HTTP GET response (i.e. the response as seen from the
//   server code), then the trace of a non-crashing HTTP server is
//   [][][] ... [], and this trace clearly inhabits WOS.
//
//   Note, however, that in HTTP the server cannot initiate a call
//   back to a client in response to a request. This is tantamount to
//   a mechanism for session state, and HTTP is noted for its lack of
//   ability to properly handle session state -- a short-coming in
//   protocol hygene that has left cookie crumbs all over the
//   Internet.
//
//   In the view of protocols taken here we do get sessions, without
//   sacrificing much of what makes HTTP attractive. Obviously, the
//   protocol structure is simple. It takes a maximum of four lines to
//   specify all legal traces. Yet, it's rich enough to model basic
//   HTTP. Beyond that it provides an explicit representation of
//   session state, while making client and server roles completely
//   symmetric. Of equal importance, this model maps directly to
//   models of Linear Logic. As a result, all of the tools of nearly
//   30 years of investigation into the computational interpretation
//   of that logic are available to allow us to reason about protocols
//   structured in this way.
//   ------------------------------------------------------------------------

package com.biosimilarity.mdp4tw.strategies

// These are the basic elements of our framework
trait Move                                                      // any action an agent can take
trait Opening                                                   // all actions either open a line of inquiry
trait Closing                                                   // or close it

trait Player                                                    // when engaging with another agent, an agent
trait Opponent                                                  // may engage in the role of Player or Opponent

trait Strategy                                                  // a plan for how to engage, which includes explicitly
                                                                // listing all moves and responses

// They combine to give us a specification of basic actions
trait PlayerQuestionT extends Move with Opening with Player     // "("
trait OpponentAnswerT extends Move with Closing with Opponent   // ")"

trait OpponentQuestionT extends Move with Opening with Opponent // "["
trait PlayerAnswerT extends Move with Closing with Player       // "]"

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


