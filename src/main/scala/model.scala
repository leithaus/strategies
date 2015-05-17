// -*- mode: Scala;-*- 
// Filename:    model.scala 
// Authors:     lgm                                                    
// Creation:    Wed May 13 10:49:22 2015 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package com.biosimilarity.mdp4tw.strategies

case object PlayerQuestion extends PlayerQuestionT {
  override def toString() : String = "["
}
case object OpponentAnswer extends OpponentAnswerT {
  override def toString() : String = "]"
}  
case object OpponentQuestion extends OpponentQuestionT {
  override def toString() : String = "("
}
case object PlayerAnswer extends PlayerAnswerT {
  override def toString() : String = ")"
}

case object BlackOpen extends PlayerQuestionT {
  override def toString() : String = "{B|"
}
case object BlackClose extends OpponentAnswerT {
  override def toString() : String = "|B}"
}  
case object RedOpen extends OpponentQuestionT {
  override def toString() : String = "{R|"
}
case object RedClose extends PlayerAnswerT {
  override def toString() : String = "|R}"
}

object ModelGameStrategies
extends GameStrategies[PlayerQuestionT,OpponentAnswerT,OpponentQuestionT,PlayerAnswerT]
with GameOps[PlayerQuestionT,OpponentAnswerT,OpponentQuestionT,PlayerAnswerT]
{
  override def negatePQ[Q >: PlayerQuestionT]( pq : Q ) : OpponentQuestionT = {
    OpponentQuestion
  }
  override def negateOQ[Q >: OpponentQuestionT]( oq : Q ) : PlayerQuestionT = {
    PlayerQuestion
  }
  override def negatePA[A >: PlayerAnswerT]( pa : A ) : OpponentAnswerT = {
    OpponentAnswer
  }
  override def negateOA[A >: OpponentAnswerT]( oa : A ) : PlayerAnswerT = {
    PlayerAnswer
  }
}

object ModelRBSets
extends RBSets[PlayerQuestionT,OpponentAnswerT,OpponentQuestionT,PlayerAnswerT]
{
}

package usage {     
  trait FuzzyStreams {
    def tStream[T]( seed : T )( fresh : T => T ) : Stream[T] = {
      lazy val loopStrm : Stream[T] =
        ( List( seed ) ).toStream append ( loopStrm map fresh );
      loopStrm
    }  
    def randomIntStream( bound : Int = 2 ) : Stream[Int] = {
      val rndm = new scala.util.Random
      tStream[Int]( rndm.nextInt( bound ) )(
        {
          ( rint : Int ) => {
            rndm.nextInt( bound )
          }
        }
      )
    }
  }

  object BasicStrategies extends FuzzyStreams {
    import ModelGameStrategies._
    type TeamPlayer =
      WPS[PlayerQuestionT,OpponentAnswerT,OpponentQuestionT,PlayerAnswerT]
    type TeamOpponent =
      WOS[PlayerQuestionT,OpponentAnswerT,OpponentQuestionT,PlayerAnswerT]

    val playerZero : TeamPlayer = WinningPlayerStrategy( Nil.toStream )
    val opponentZero : TeamOpponent = WinningOpponentStrategy( Nil.toStream )

    def randomPlayerStrategy(      
      justification : Option[TeamOpponent],
      depth : Int,
      maxd : Int,
      maxb : Int,
      rndm : scala.util.Random = new scala.util.Random
    ) : TeamPlayer = {
      ( justification, ( depth < maxd ) ) match {
        case ( None, true ) => {       
          rndm.nextInt( 2 ) match {
            case 0 => {
              WinningPlayerStrategy(
                ( 1 to rndm.nextInt( maxb ) ).map(
                  ( i ) => {
                    WellBracketedWOS(
                      OpponentQuestion,
                      randomOpponentStrategy( None, depth + 1, maxd, maxb, rndm ),
                      PlayerAnswer
                    )
                  }
                ).toStream
              )
            }
            case 1 => {              
              randomPlayerStrategy(
                Some( randomOpponentStrategy( None, depth + 1, maxd, maxb, rndm ) ),
                depth + 1, maxd, maxb, rndm 
              )
            }
          }
        }
        case ( None, false ) => {       
          rndm.nextInt( 2 ) match {
            case 0 => {
              playerZero
            }
            case 1 => {
              WinningPlayerStrategy(
                List(
                  WellBracketedWOS( OpponentQuestion, opponentZero, PlayerAnswer )
                ).toStream
              )
            }
          }
        }
        case ( Some( wos ), true ) =>  {          
          randomPlayerStrategy(
            Some(
              randomOpponentStrategy(
                Some(
                  WinningPlayerStrategy(
                    List( WellBracketedWOS( OpponentQuestion, wos, PlayerAnswer ) ).toStream
                  )
                ),
                depth + 1, maxd, maxb, rndm
              )
            ),
            depth + 1, maxd, maxb, rndm
          )
        }
        case ( Some( wos ), false ) => {
          WinningPlayerStrategy(
            List( WellBracketedWOS( OpponentQuestion, wos, PlayerAnswer ) ).toStream
          )
        }
      }      
    }
    
    def randomOpponentStrategy(      
      justification : Option[TeamPlayer],
      depth : Int,
      maxd : Int,
      maxb : Int,
      rndm : scala.util.Random = new scala.util.Random
    ) : TeamOpponent = {
      ( justification, ( depth < maxd ) ) match {
        case ( None, true ) => {       
          rndm.nextInt( 2 ) match {
            case 0 => {
              randomOpponentStrategy(
                Some( randomPlayerStrategy( None, depth + 1, maxd, maxb, rndm ) ),
                depth + 1, maxd, maxb, rndm 
              )
            }
            case 1 => {
              WinningOpponentStrategy(
                ( 1 to rndm.nextInt( maxb ) ).map(
                  ( i ) => {
                    WellBracketedWPS(
                      PlayerQuestion,
                      randomPlayerStrategy( None, depth + 1, maxd, maxb, rndm ),
                      OpponentAnswer
                    )
                  }
                ).toStream
              )
            }
          }
        }
        case ( None, false ) => {       
          rndm.nextInt( 2 ) match {
            case 0 => {
              WinningOpponentStrategy(
                List(
                  WellBracketedWPS( PlayerQuestion, playerZero, OpponentAnswer )
                ).toStream
              )
            }
            case 1 => {
              opponentZero
            }
          }
        }
        case ( Some( wps ), true ) =>  {          
          randomOpponentStrategy(
            Some(
              randomPlayerStrategy(
                Some(
                  WinningOpponentStrategy(
                    List( WellBracketedWPS( PlayerQuestion, wps, OpponentAnswer ) ).toStream
                  )
                ),
                depth + 1, maxd, maxb, rndm
              )
            ),
            depth + 1, maxd, maxb, rndm
          )
        }
        case ( Some( wps ), false ) => {
          WinningOpponentStrategy(
            List( WellBracketedWPS( PlayerQuestion, wps, OpponentAnswer ) ).toStream
          )
        }
      }      
    }
  }
  
  object BasicSets extends FuzzyStreams {
    import ModelRBSets._
    type TeamRed =
      RS[PlayerQuestionT,OpponentAnswerT,OpponentQuestionT,PlayerAnswerT]
    type TeamBlack =
      BS[PlayerQuestionT,OpponentAnswerT,OpponentQuestionT,PlayerAnswerT]

    val redZero : TeamRed = RSet( Nil.toStream )
    val blackZero : TeamBlack = BSet( Nil.toStream )

    def randomRedSet(      
      justification : Option[TeamBlack],
      depth : Int,
      maxd : Int,
      maxb : Int,
      rndm : scala.util.Random = new scala.util.Random
    ) : TeamRed = {
      ( justification, ( depth < maxd ) ) match {
        case ( None, true ) => {       
          rndm.nextInt( 2 ) match {
            case 0 => {
              RSet(
                ( 1 to rndm.nextInt( maxb ) ).map(
                  ( i ) => {
                    rndm.nextInt( 2 ) match {
                      case 0 => {
                        Left(
                          WellBracketedBS(
                            RedOpen,
                            randomBlackSet( None, depth + 1, maxd, maxb, rndm ),
                            RedClose
                          )
                        )
                      }
                      case 1 => {
                        Right(
                          randomRedSet( None, depth + 1, maxd, maxb, rndm )
                        )
                      }
                    }                    
                  }
                ).toStream
              )
            }
            case 1 => {              
              randomRedSet(
                Some( randomBlackSet( None, depth + 1, maxd, maxb, rndm ) ),
                depth + 1, maxd, maxb, rndm 
              )
            }
          }
        }
        case ( None, false ) => {       
          rndm.nextInt( 2 ) match {
            case 0 => {
              redZero
            }
            case 1 => {
              RSet(
                List(
                  Left(
                    WellBracketedBS( RedOpen, blackZero, RedClose )
                  )
                ).toStream
              )
            }
          }
        }
        case ( Some( wos ), true ) =>  {          
          randomRedSet(
            Some(
              randomBlackSet(
                Some(
                  RSet(
                    List(
                      Left(
                        WellBracketedBS( RedOpen, wos, RedClose )
                      )
                    ).toStream
                  )
                ),
                depth + 1, maxd, maxb, rndm
              )
            ),
            depth + 1, maxd, maxb, rndm
          )
        }
        case ( Some( wos ), false ) => {
          RSet(
            List(
              Left(
                WellBracketedBS( RedOpen, wos, RedClose )
              )
            ).toStream
          )
        }
      }      
    }
    
    def randomBlackSet(      
      justification : Option[TeamRed],
      depth : Int,
      maxd : Int,
      maxb : Int,
      rndm : scala.util.Random = new scala.util.Random
    ) : TeamBlack = {
      ( justification, ( depth < maxd ) ) match {
        case ( None, true ) => {       
          rndm.nextInt( 2 ) match {
            case 0 => {
              randomBlackSet(
                Some( randomRedSet( None, depth + 1, maxd, maxb, rndm ) ),
                depth + 1, maxd, maxb, rndm 
              )
            }
            case 1 => {
              BSet(
                ( 1 to rndm.nextInt( maxb ) ).map(
                  ( i ) => {
                    rndm.nextInt( 2 ) match {
                      case 0 => {
                        Left(
                          WellBracketedRS(
                            BlackOpen,
                            randomRedSet( None, depth + 1, maxd, maxb, rndm ),
                            BlackClose
                          )
                        )
                      }
                      case 1 => {
                        Right(
                          randomBlackSet( None, depth + 1, maxd, maxb, rndm )
                        )
                      }
                    }                    
                  }
                ).toStream
              )
            }
          }
        }
        case ( None, false ) => {       
          rndm.nextInt( 2 ) match {
            case 0 => {
              BSet(
                List(
                  Left(
                    WellBracketedRS( BlackOpen, redZero, BlackClose )
                  )
                ).toStream
              )
            }
            case 1 => {
              blackZero
            }
          }
        }
        case ( Some( wps ), true ) =>  {          
          randomBlackSet(
            Some(
              randomRedSet(
                Some(
                  BSet(
                    List(
                      Left(
                        WellBracketedRS( BlackOpen, wps, BlackClose )
                      )
                    ).toStream
                  )
                ),
                depth + 1, maxd, maxb, rndm
              )
            ),
            depth + 1, maxd, maxb, rndm
          )
        }
        case ( Some( wps ), false ) => {
          BSet(
            List(
              Left(
                WellBracketedRS( BlackOpen, wps, BlackClose )
              )
            ).toStream
          )
        }
      }      
    }
  }
}
