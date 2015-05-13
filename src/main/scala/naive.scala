// -*- mode: Scala;-*- 
// Filename:    naive.scala 
// Authors:     lgm                                                    
// Creation:    Tue May 12 20:10:56 2015 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package com.biosimilarity.mdp4tw.strategies

trait NaivePlayerStrategies[+PQ,+OA,+OQ,+PA]
{
  type WOS[+PQ,+OA,+OQ,+PA] <: Strategy
  case class WinningPlayerStrategy[+PQ,+OA,+OQ,+PA](
    oq : OQ, s : Stream[WOS[PQ,OA,OQ,PA]], pa: PA
  ) extends Strategy {
    override def toString() = {
      Utilities.strategyStreamToString( s, oq, pa )      
    }
  }
}

trait NaiveOpponentStrategies[+PQ,+OA,+OQ,+PA] {
  type WPS[+PQ,+OA,+OQ,+PA] <: Strategy
  case class WinningOpponentStrategy[+PQ,+OA,+OQ,+PA](
    pq : PQ, s : Stream[WPS[PQ,OA,OQ,PA]], oa: OA
  ) extends Strategy {
    override def toString() = {
      Utilities.strategyStreamToString( s, pq, oa )            
    }
  }
}

trait NaiveGameStrategies[+PQ,+OA,+OQ,+PA]
       extends NaivePlayerStrategies[PQ,OA,OQ,PA]
       with NaiveOpponentStrategies[PQ,OA,OQ,PA]
{
  override type WOS[+PQ,+OA,+OQ,+PA] = WinningOpponentStrategy[PQ,OA,OQ,PA]
  override type WPS[+PQ,+OA,+OQ,+PA] = WinningPlayerStrategy[PQ,OA,OQ,PA]
}

object ModelNaiveGameStrategies
       extends NaiveGameStrategies[PlayerQuestion.type, OpponentAnswer.type, OpponentQuestion.type,PlayerAnswer.type]


package usage {     
  object BasicNaiveStrategies extends FuzzyStreams {
    import ModelNaiveGameStrategies._
    type TeamPlayer =
      WinningPlayerStrategy[PlayerQuestion.type,OpponentAnswer.type,OpponentQuestion.type,PlayerAnswer.type]
    type TeamOpponent =
      WinningOpponentStrategy[PlayerQuestion.type,OpponentAnswer.type,OpponentQuestion.type,PlayerAnswer.type]

    val emptyTPStream : Stream[TeamPlayer] = Nil.toStream
    val emptyTOStream : Stream[TeamOpponent] = Nil.toStream

    // Opponent opens, Player responds, and that's it for the game
    val winningPlayerOne =
      WinningPlayerStrategy( OpponentQuestion, emptyTOStream, PlayerAnswer )

    // Player opens, Opponent responds, and that's it for the game
    val winningOpponentOne =
      WinningOpponentStrategy( PlayerQuestion, emptyTPStream, OpponentAnswer )    

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
                OpponentQuestion,
                ( 1 to maxb ).map(
                  ( i ) => {
                    randomOpponentStrategy( Some( winningPlayerOne ), depth + 1, maxd, maxb, rndm )
                  }
                ).toStream,
                PlayerAnswer
              )
            }
            case 1 => {
              WinningPlayerStrategy(
                OpponentQuestion,
                List(
                  WinningOpponentStrategy(
                    PlayerQuestion,
                    ( 1 to maxb ).map(
                      ( i ) => {
                        randomPlayerStrategy( Some( winningOpponentOne ), depth + 1, maxd, maxb, rndm )
                      }
                    ).toStream,
                    OpponentAnswer
                  )
                ).toStream,
                PlayerAnswer
              )
            }
          }
        }
        case ( None, false ) => {       
          rndm.nextInt( 2 ) match {
            case 0 => {
              winningPlayerOne
            }
            case 1 => {
              WinningPlayerStrategy(
                OpponentQuestion,
                List( winningOpponentOne ).toStream,
                PlayerAnswer
              )
            }
          }
        }
        case ( Some( wos ), true ) =>  {          
          WinningPlayerStrategy(
            OpponentQuestion,
            List( wos ).toStream,
            PlayerAnswer
          )
        }
        case ( Some( strat ), false ) => {
          WinningPlayerStrategy(
            OpponentQuestion,
            List( strat ).toStream,
            PlayerAnswer
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
              WinningOpponentStrategy(
                PlayerQuestion,
                List(
                  WinningPlayerStrategy(
                    OpponentQuestion,
                    ( 1 to maxb ).map(
                      ( i ) => {
                        randomOpponentStrategy( Some( winningPlayerOne ), depth + 1, maxd, maxb, rndm )
                      }
                    ).toStream,
                    PlayerAnswer
                  )
                ).toStream,
                OpponentAnswer
              )
            }
            case 1 => {
              WinningOpponentStrategy(
                PlayerQuestion,
                ( 1 to maxb ).map(
                  ( i ) => {
                    randomPlayerStrategy( Some( winningOpponentOne ), depth + 1, maxd, maxb, rndm )
                  }
                ).toStream,
                OpponentAnswer
              )
            }
          }
        }
        case ( None, false ) => {       
          rndm.nextInt( 2 ) match {
            case 0 => {
              WinningOpponentStrategy(
                PlayerQuestion,
                List( winningPlayerOne ).toStream,
                OpponentAnswer
              )
            }
            case 1 => {
              winningOpponentOne
            }
          }
        }
        case ( Some( wps ), true ) => {
          WinningOpponentStrategy(
            PlayerQuestion,
            List( wps ).toStream,
            OpponentAnswer
          )
        }
        case ( Some( strat ), false ) => {
          WinningOpponentStrategy(
            PlayerQuestion,
            List( strat ).toStream,
            OpponentAnswer
          )
        }
      }      
    }    
  }
}
