// -*- mode: Scala;-*- 
// Filename:    linearlogic.scala 
// Authors:     lgm                                                    
// Creation:    Wed May 13 10:46:11 2015 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package com.biosimilarity.mdp4tw.strategies

trait GameOps[+PQ,+OA,+OQ,+PA] {
  self : GameStrategies[PQ,OA,OQ,PA] =>

  override type WOS[+PQ,+OA,+OQ,+PA] =
    OpponentStrategies[PQ,OA,OQ,PA]#WinningOpponentStrategyT[PQ,OA,OQ,PA]
  override type WPS[+PQ,+OA,+OQ,+PA] =
    PlayerStrategies[PQ,OA,OQ,PA]#WinningPlayerStrategyT[PQ,OA,OQ,PA]  

  type WBWOS[+PQ,+OA,+OQ,+PA] = PlayerStrategies[PQ,OA,OQ,PA]#WellBracketedWOST[PQ,OA,OQ,PA]
  type WBWPS[+PQ,+OA,+OQ,+PA] = OpponentStrategies[PQ,OA,OQ,PA]#WellBracketedWPST[PQ,OA,OQ,PA]

  implicit def bracket[PQ1 >: PQ,OA1 >: OA,OQ1 >: OQ, PA1 >: PA](
    wbs : PlayerStrategies[PQ1,OA1,OQ1,PA1]#WellBracketedWOST[PQ1,OA1,OQ1,PA1]
  ) : WellBracketedWOS[PQ1,OA1,OQ1,PA1] = {
    WellBracketedWOS[PQ1,OA1,OQ1,PA1](
      wbs.oq,
      wbs.strategy,
      wbs.pa
    )
  }

  implicit def bracket[PQ1 >: PQ,OA1 >: OA,OQ1 >: OQ, PA1 >: PA](
    wbs : OpponentStrategies[PQ1,OA1,OQ1,PA1]#WellBracketedWPST[PQ1,OA1,OQ1,PA1]
  ) : WellBracketedWPS[PQ1,OA1,OQ1,PA1] = {
    WellBracketedWPS[PQ1,OA1,OQ1,PA1](
      wbs.pq,
      wbs.strategy,
      wbs.oa
    )
  }

  def negatePQ[Q >: PQ]( pq : Q ) : OQ = throw new Exception( "Not yet implemented" )
  def negateOQ[Q >: OQ]( oq : Q ) : PQ = throw new Exception( "Not yet implemented" )
  def negatePA[A >: PA]( pa : A ) : OA = throw new Exception( "Not yet implemented" )
  def negateOA[A >: OA]( oa : A ) : PA = throw new Exception( "Not yet implemented" )

  def negate[PQ1 >: PQ,OA1 >: OA,OQ1 >: OQ, PA1 >: PA]( wbs : WellBracketedWOST[PQ1,OA1,OQ1,PA1] ) : WellBracketedWPST[PQ1,OA1,OQ1,PA1] = {
    WellBracketedWPS[PQ1,OA1,OQ1,PA1](
      negateOQ( wbs.oq ),
      negate( wbs.strategy ),
      negatePA( wbs.pa )
    )
  }

  def negate[PQ1 >: PQ,OA1 >: OA,OQ1 >: OQ, PA1 >: PA]( wbs : WellBracketedWPST[PQ1,OA1,OQ1,PA1] ) : WellBracketedWOST[PQ1,OA1,OQ1,PA1] = {
    WellBracketedWOS[PQ1,OA1,OQ1,PA1](
      negatePQ( wbs.pq ),
      negate( wbs.strategy ),
      negateOA( wbs.oa )
    )
  }

  def negate[PQ1 >: PQ,OA1 >: OA,OQ1 >: OQ, PA1 >: PA]( ws : WOS[PQ1,OA1,OQ1,PA1] ) : WPS[PQ1,OA1,OQ1,PA1] = {
    WinningPlayerStrategy[PQ1,OA1,OQ1,PA1](
      ws.s.map( ( wbs ) => negate( wbs ) )
    )
  }
  def negate[PQ1 >: PQ,OA1 >: OA,OQ1 >: OQ, PA1 >: PA]( ws : WPS[PQ1,OA1,OQ1,PA1] ) : WOS[PQ1,OA1,OQ1,PA1] = {
    WinningOpponentStrategy[PQ1,OA1,OQ1,PA1](
      ws.s.map( ( wbs ) => negate( wbs ) )
    )
  }
}


