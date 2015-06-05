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

  implicit def opponentAnswerF[PQ,OQ,PA]() : Functor[({type L[+OA] = WinningOpponentStrategyT[PQ,OA,OQ,PA]})#L] =
    new Functor[({type L[+OA] = WinningOpponentStrategyT[PQ,OA,OQ,PA]})#L] {
      def fmap[RA, TA >: RA, SA](
        f : TA => SA
      ) : WinningOpponentStrategyT[PQ,TA,OQ,PA] => WinningOpponentStrategyT[PQ,SA,OQ,PA] = {
	( wos : WinningOpponentStrategyT[PQ,TA,OQ,PA] ) => {
	  WinningOpponentStrategy[PQ,SA,OQ,PA](
            {
	      wos.s.map(
                ( wbps : WellBracketedWPST[PQ,TA,OQ,PA] ) => {
                  WellBracketedWPS[PQ,SA,OQ,PA](
                    wbps.pq,
                    WinningPlayerStrategy[PQ,SA,OQ,PA](
                      wbps.strategy.s.map(
                        {
                          ( wbos : WellBracketedWOST[PQ,TA,OQ,PA] ) => {
                            WellBracketedWOS[PQ,SA,OQ,PA](
                              wbos.oq,
                              fmap[RA,TA,SA]( f )( wbos.strategy ),
                              wbos.pa
                            )
                          }
                        }
                      )
                    ),
                    f( wbps.oa )
                  )
                }
              )
            }
	  )
	}
      }
    }
}

object RBSetMonad
extends RBSets[PlayerQuestionT,OpponentAnswerT,OpponentQuestionT,PlayerAnswerT]{
  import MonadicEvidence._
  implicit def redF[PQ,OA,OQ]() : Functor[({type L[+PA] = RSetT[PQ,OA,OQ,PA]})#L] =
    new Functor[({type L[+PA] = RSetT[PQ,OA,OQ,PA]})#L] {
      def fmap[UA, TA >: UA, SA](
        f : TA => SA
      ) : RSetT[PQ,OA,OQ,TA] => RSetT[PQ,OA,OQ,SA] = {
        def bloop( batm : BA[PQ,OA,OQ,TA] ) : BA[PQ,OA,OQ,SA] = {
          BSet[PQ,OA,OQ,SA](
            batm.pq,
            batm.s.map(
              {
                ( RAorBSet : Either[RA[PQ,OA,OQ,TA],BSetT[PQ,OA,OQ,TA]] ) => {
                  RAorBSet match {
                    case Left( ratm ) => 
                      Left[RA[PQ,OA,OQ,SA],BSetT[PQ,OA,OQ,SA]]( fmap( f )( ratm ) )
                    case Right( bset ) => Right[RA[PQ,OA,OQ,SA],BSetT[PQ,OA,OQ,SA]]( bloop( bset ) )
                  }
                }
              }
            ),
            batm.oa
          )
        }

	( wps : RSetT[PQ,OA,OQ,TA] ) => {
	  RSet[PQ,OA,OQ,SA](
            wps.oq,
            wps.s.map(
              ( wbos : Either[BA[PQ,OA,OQ,TA],RSetT[PQ,OA,OQ,TA]] ) => {
                wbos match {
                  case Left( batm ) => {
                    Left[BA[PQ,OA,OQ,SA],RSetT[PQ,OA,OQ,SA]](
                      bloop( batm )
                    )
                  }
                  case Right( rset ) => {
                    Right[BA[PQ,OA,OQ,SA],RSetT[PQ,OA,OQ,SA]]( fmap( f )( rset ) )
                  }
                }
              }
            ),
            f( wps.pa )
          )
	}
      }
    }

  // implicit def redMnd[PQ,OA,OQ]() : MonadB[({type L[+PA] = RSetT[PQ,OA,OQ,PA]})#L] = {
//     new MonadB[({type L[+PA] = RSetT[PQ,OA,OQ,PA]})#L] {
//       override def fmap[UA, TA >: UA, SA](
//         f : TA => SA
//       ) : RSetT[PQ,OA,OQ,TA] => RSetT[PQ,OA,OQ,SA] = {
//         RBSetMonad.refF[PQ,OQ,PA]().fmap( f )
//       }
//       override def apply[SA >: PA, SQ >: OQ](
//         data : SA
//       )( implicit justification : SA => SQ ) : RSetT[PQ,OA,OQ,SA] = {
//         RSet(
//           justification( data ),
//           List[Either[BA[PQ,OA,SQ,SA],RSetT[PQ,OA,SQ,SA]]].toStream(),
//           data
//         )
//       }
//       override def bind[S, P >: S, T](
//         mp : RSetT[PQ,OA,OQ,P]
//       )( t : P => RSetT[PQ,OA,OQ,T] )(
//         implicit join : ( OQ, OQ ) => OQ
//       ) : RSetT[PQ,OA,OQ,T] = {
//         def bloop(
//           s : Stream[Either[BA[PQ,OA,OQ,P],RSetT[PQ,OA,OQ,P]]]
//         ) : Stream[Either[BA[PQ,OA,OQ,T],RSetT[PQ,OA,OQ,T]]] = {
//           s.map(
//             ( eBAorRSet : Either[BA[PQ,OA,OQ,P],RSetT[PQ,OA,OQ,P]] ) => {
//               eBAorRSet match {
//                 case Left( ba ) => {
//                   Left(
//                     BSet(
//                       ba.pq,
//                       ba.s.map(
//                         ( eBAorRSet : Either[RA[PQ,OA,OQ,P],BSetT[PQ,OA,OQ,P]] ) => {
//                           case Left( ra ) => {
//                             Left( bind( ra )( t ) )
//                           }
//                           case Right( bset ) => {
//                             Right( bloop( bset ) )
//                           }
//                         }
//                       ),
//                       ba.oa
//                     )
//                   )
//                 }
//                 case Right( rset ) => {
//                   Right( bind( rset )( t ) )
//                 }
//               }
//             }
//           )
//         }
//         val RSet( toq, ts, tpa ) = t( mp.pa )
//         RSet(
//           join( mp.oq, toq ),
//           bloop( mp.s ).union( ts ),
//           tpa
//         )
//       }
//     }
//   }

  implicit def blkF[PQ,OQ,PA]() : Functor[({type L[+OA] = BSetT[PQ,OA,OQ,PA]})#L] =
    new Functor[({type L[+OA] = BSetT[PQ,OA,OQ,PA]})#L] {
      def fmap[UA, TA >: UA, SA](
        f : TA => SA
      ) : BSetT[PQ,TA,OQ,PA] => BSetT[PQ,SA,OQ,PA] = {
        def rloop( ratm : RA[PQ,TA,OQ,PA] ) : RA[PQ,SA,OQ,PA] = {
          RSet[PQ,SA,OQ,PA](
            ratm.oq,
            ratm.s.map(
              {
                ( BAorRSet : Either[BA[PQ,TA,OQ,PA],RSetT[PQ,TA,OQ,PA]] ) => {
                  BAorRSet match {
                    case Left( batm ) => 
                      Left[BA[PQ,SA,OQ,PA],RSetT[PQ,SA,OQ,PA]]( fmap( f )( batm ) )
                    case Right( rset ) => Right[BA[PQ,SA,OQ,PA],RSetT[PQ,SA,OQ,PA]]( rloop( rset ) )
                  }
                }
              }
            ),
            ratm.pa
          )
        }

	( wps : BSetT[PQ,TA,OQ,PA] ) => {
	  BSet[PQ,SA,OQ,PA](
            wps.pq,
            wps.s.map(
              ( wbos : Either[RA[PQ,TA,OQ,PA],BSetT[PQ,TA,OQ,PA]] ) => {
                wbos match {
                  case Left( ratm ) => {
                    Left[RA[PQ,SA,OQ,PA],BSetT[PQ,SA,OQ,PA]](
                      rloop( ratm )
                    )
                  }
                  case Right( bset ) => {
                    Right[RA[PQ,SA,OQ,PA],BSetT[PQ,SA,OQ,PA]]( fmap( f )( bset ) )
                  }
                }
              }
            ),
            f( wps.oa )
          )
	}
      }
    }
}
