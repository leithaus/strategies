// -*- mode: Scala;-*- 
// Filename:    mstrat.scala 
// Authors:     lgm                                                    
// Creation:    Sat May 16 13:32:41 2015 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package com.biosimilarity.mdp4tw.strategies

import com.biosimilarity.mdp4tw.monad._

object StrategyMonad
extends GameStrategies[PlayerQuestionT,OpponentAnswerT,OpponentQuestionT,PlayerAnswerT]
with GameOps[PlayerQuestionT,OpponentAnswerT,OpponentQuestionT,PlayerAnswerT] {
  import MonadicEvidence._

  implicit def playerAnswerF[PQ,OA,OQ]() : Functor[({type L[+PA] = WinningPlayerStrategyT[PQ,OA,OQ,PA]})#L] =
    new Functor[({type L[+PA] = WinningPlayerStrategyT[PQ,OA,OQ,PA]})#L] {
      def fmap[RA, TA >: RA, SA](
        f : TA => SA
      ) : WinningPlayerStrategyT[PQ,OA,OQ,TA] => WinningPlayerStrategyT[PQ,OA,OQ,SA] = {
	( wps : WinningPlayerStrategyT[PQ,OA,OQ,TA] ) => {
	  WinningPlayerStrategy[PQ,OA,OQ,SA](
            {
	      wps.s.map(
                ( wbos : WellBracketedWOST[PQ,OA,OQ,TA] ) => {
                  WellBracketedWOS[PQ,OA,OQ,SA](
                    wbos.oq,
                    WinningOpponentStrategy[PQ,OA,OQ,SA](
                      wbos.strategy.s.map(
                        {
                          ( wbps : WellBracketedWPST[PQ,OA,OQ,TA] ) => {
                            WellBracketedWPS[PQ,OA,OQ,SA](
                              wbps.pq,
                              fmap[RA,TA,SA]( f )( wbps.strategy ),
                              wbps.oa
                            )
                          }
                        }
                      )
                    ),
                    f( wbos.pa )
                  )
                }
              )
            }
	  )
	}
      }
    }
}
