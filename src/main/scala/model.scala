// -*- mode: Scala;-*- 
// Filename:    model.scala 
// Authors:     lgm                                                    
// Creation:    Wed May 13 10:49:22 2015 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package com.biosimilarity.mdp4tw.strategies

object ModelGameStrategies
       extends GameStrategies[PlayerQuestionT,OpponentAnswerT,OpponentQuestionT,PlayerAnswerT]
       with GameOps[PlayerQuestionT,OpponentAnswerT,OpponentQuestionT,PlayerAnswerT]
{
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

    def show( tp : TeamPlayer ) : Unit = {
      val l = tp.s.length;
      println( tp );
      for( wbs <- tp.s ) { show( wbs.strategy ) }
    }
    def show( to : TeamOpponent ) : Unit = {
      val l = to.s.length;
      println( to );
      for( wbs <- to.s ) { show( wbs.strategy ) }
    }

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
}
