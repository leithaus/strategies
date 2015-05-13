// -*- mode: Scala;-*- 
// Filename:    strategies.scala 
// Authors:     lgm                                                    
// Creation:    Sun May 10 03:29:13 2015 
// Copyright:   Not supplied 
// Description: 
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
case object PlayerQuestion extends Move with Opening with Player {
  override def toString() : String = "["
}
case object OpponentAnswer extends Move with Closing with Opponent {
  override def toString() : String = "]"
}

case object OpponentQuestion extends Move with Opening with Opponent {
  override def toString() : String = "("
}
case object PlayerAnswer extends Move with Closing with Player {
  override def toString() : String = ")"
}

// The specification of basic agents, aka a strategy, is parametric in
// a specification of basic actions

// Further, a Player strategy is parametric in an Opponent strategy
trait PlayerStrategies[+PQ,+OA,+OQ,+PA]
{
  type WOS[+PQ,+OA,+OQ,+PA] <: OpponentStrategies[PQ,OA,OQ,PA]#WinningOpponentStrategy[PQ,OA,OQ,PA]
  case class WellBracketedWOS[+PQ,+OA,+OQ,+PA]( oq : OQ, strategy : WOS[PQ,OA,OQ,PA], pa : PA ) {
    override def toString() = {
      Utilities.strategyStreamToString( strategy.s, oq, pa )            
    }
  }
  case class WinningPlayerStrategy[+PQ,+OA,+OQ,+PA](
    s : Stream[WellBracketedWOS[PQ,OA,OQ,PA]]
  ) extends Strategy {
    override def toString() = {
      Utilities.bracketedStrategyStreamToString( s )
    }
  }
}

// While an Opponent strategy is parametric in a Player strategy
trait OpponentStrategies[+PQ,+OA,+OQ,+PA] {
  type WPS[+PQ,+OA,+OQ,+PA] <: PlayerStrategies[PQ,OA,OQ,PA]#WinningPlayerStrategy[PQ,OA,OQ,PA]
  case class WellBracketedWPS[+PQ,+OA,+OQ,+PA]( pq : PQ, strategy : WPS[PQ,OA,OQ,PA], oa : OA ) {
    override def toString() = {
      Utilities.strategyStreamToString( strategy.s, pq, oa )                  
    }
  }
  case class WinningOpponentStrategy[+PQ,+OA,+OQ,+PA](
    s : Stream[WellBracketedWPS[PQ,OA,OQ,PA]]
  ) extends Strategy {
    override def toString() = {
      Utilities.bracketedStrategyStreamToString( s )
    }
  }
}

// We can tie the recursive knot to get a pair of interdependent
// agents, yet still parametric in some notion of basic actions
trait GameStrategies[+PQ,+OA,+OQ,+PA]
       extends PlayerStrategies[PQ,OA,OQ,PA]
       with OpponentStrategies[PQ,OA,OQ,PA]
{
  override type WOS[+PQ,+OA,+OQ,+PA] =
    OpponentStrategies[PQ,OA,OQ,PA]#WinningOpponentStrategy[PQ,OA,OQ,PA]
  override type WPS[+PQ,+OA,+OQ,+PA] =
    PlayerStrategies[PQ,OA,OQ,PA]#WinningPlayerStrategy[PQ,OA,OQ,PA]
}

object ModelGameStrategies
       extends GameStrategies[PlayerQuestion.type, OpponentAnswer.type, OpponentQuestion.type,PlayerAnswer.type]

object Utilities {
  def bracketedStrategyStreamToString[Strat](
    stratStrm : Stream[Strat]
  ) = {
    if ( stratStrm.hasDefiniteSize ) {
      stratStrm.length match {
        case 0 => ""
        case l => {
          val cs = ( "" /: stratStrm.toList.take( l - 1 ) )(
            { ( acc, e ) => e.toString + "," + acc }
          )
          cs + stratStrm.last.toString
        }
      }
    }
    else {
      stratStrm( 0 ).toString + " ..."
    }
  }

  def strategyStreamToString[Strat,Q,A](
    stratStrm : Stream[Strat], q : Q, a : A
  ) = {
      val sstr = 
        if ( stratStrm.hasDefiniteSize ) {
          stratStrm.length match {
            case 0 => ""
            case l => {
              val cs = ( "" /: stratStrm.toList.take( l - 1 ) )(
                { ( acc, e ) => e.toString + "," + acc }
              )
              cs + stratStrm.last.toString
            }
          }
        }
        else {
          "..."
        }
      sstr match {
        case "" => {
          q.toString + a.toString
        }
        case _ => {
          q.toString + " " + sstr + " " + a.toString
          //q.toString + sstr + a.toString
        }
      }      
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
      WPS[PlayerQuestion.type,OpponentAnswer.type,OpponentQuestion.type,PlayerAnswer.type]
    type TeamOpponent =
      WOS[PlayerQuestion.type,OpponentAnswer.type,OpponentQuestion.type,PlayerAnswer.type]

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
