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
  import Utilities.stream
  trait FuzzyStreams {    
    def tStream[T]( seed : T )( fresh : T => T ) : Stream[T] = {
      lazy val loopStrm : Stream[T] =
        stream( seed ) append ( loopStrm map fresh );
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
                stream(
                  WellBracketedWOS( OpponentQuestion, opponentZero, PlayerAnswer )
                )
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
                    stream( WellBracketedWOS( OpponentQuestion, wos, PlayerAnswer ) )
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
            stream( WellBracketedWOS( OpponentQuestion, wos, PlayerAnswer ) )
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
                stream(
                  WellBracketedWPS( PlayerQuestion, playerZero, OpponentAnswer )
                )
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
                    stream( WellBracketedWPS( PlayerQuestion, wps, OpponentAnswer ) )
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
            stream( WellBracketedWPS( PlayerQuestion, wps, OpponentAnswer ) )
          )
        }
      }      
    }
  }
  
  object BasicSets extends FuzzyStreams {
    import ModelRBSets._
    type TeamRed =
      RA[PlayerQuestionT,OpponentAnswerT,OpponentQuestionT,PlayerAnswerT]
    type TeamBlack =
      BA[PlayerQuestionT,OpponentAnswerT,OpponentQuestionT,PlayerAnswerT]

    val redZero : TeamRed = RSet( RedOpen, Nil.toStream, RedClose )
    val blackZero : TeamBlack = BSet( BlackOpen, Nil.toStream, BlackClose )

    def randomRedSet(      
      justification : Option[TeamBlack],
      depth : Int,
      maxd : Int,
      maxb : Int,
      rndm : scala.util.Random = new scala.util.Random
    ) : TeamRed = {
      ( justification, ( depth < maxd ) ) match {
        case ( None, true ) => {
          RSet(
            RedOpen,
            ( 1 to rndm.nextInt( maxb ) ).map(
              ( i ) => {
                rndm.nextInt( 2 ) match {
                  case 0 => {
                    Left( randomBlackSet( None, depth + 1, maxd, maxb, rndm ) )
                  }
                  case 1 => {
                    Right( randomRedSet( None, depth + 1, maxd, maxb, rndm ) )
                  }
                }                    
              }
            ).toStream,
            RedClose
          )
        }
        case ( None, false ) => {       
          rndm.nextInt( 2 ) match {
            case 0 => {
              redZero
            }
            case 1 => {
              RSet( RedOpen, stream( Left( blackZero ) ), RedClose )
            }
          }
        }
        case ( Some( wos ), true ) =>  {          
          randomRedSet(
            Some(
              randomBlackSet(
                Some(
                  RSet( RedOpen, stream( Left( wos ) ), RedClose )
                ),
                depth + 1, maxd, maxb, rndm
              )
            ),
            depth + 1, maxd, maxb, rndm
          )
        }
        case ( Some( wos ), false ) => {
          RSet( RedOpen, stream( Left( wos ) ), RedClose )
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
          BSet(
            BlackOpen,
            ( 1 to rndm.nextInt( maxb ) ).map(
              ( i ) => {
                rndm.nextInt( 2 ) match {
                  case 0 => {
                    Left( randomRedSet( None, depth + 1, maxd, maxb, rndm ) )
                  }
                  case 1 => {
                    Right( randomBlackSet( None, depth + 1, maxd, maxb, rndm ) )
                  }
                }                    
              }
            ).toStream,
            BlackClose
          )
        }
        case ( None, false ) => {       
          rndm.nextInt( 2 ) match {
            case 0 => {
              BSet( BlackOpen, stream( Left( redZero ) ), BlackClose )
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
                Some( BSet( BlackOpen, stream( Left( wps ) ), BlackClose ) ),
                depth + 1, maxd, maxb, rndm
              )
            ),
            depth + 1, maxd, maxb, rndm
          )
        }
        case ( Some( wps ), false ) => {
          BSet( BlackOpen, stream( Left( wps ) ), BlackClose )
        }
      }      
    }
  }
}
