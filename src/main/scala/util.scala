// -*- mode: Scala;-*- 
// Filename:    util.scala 
// Authors:     lgm                                                    
// Creation:    Wed May 13 11:00:31 2015 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package com.biosimilarity.mdp4tw.strategies

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

  def bracketedSetStreamToString[Strat](
    setStrm : Stream[Strat]
  ) = {
    if ( setStrm.hasDefiniteSize ) {
      setStrm.length match {
        case 0 => ""
        case l => {
          val cs = ( "" /: setStrm.toList.take( l - 1 ) )(
            {
              ( acc, e ) => {
                val s =
                  e match {
                    case Left( wbs ) => wbs
                    case Right( ns ) => ns
                  }
                s.toString + "," + acc
              }
            }
          )
          val lst = 
            setStrm.last match {
              case Left( wbs ) => wbs
              case Right( ns ) => ns
            }
          cs + lst.toString
        }
      }
    }
    else {
      setStrm( 0 ).toString + " ..."
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

  def setStreamToString[Strat,Q,A](
    setStrm : Stream[Strat], q : Q, a : A
  ) = {
      val sstr = 
        if ( setStrm.hasDefiniteSize ) {
          setStrm.length match {
            case 0 => ""
            case l => {
              val cs = ( "" /: setStrm.toList.take( l - 1 ) )(
                {
                  ( acc, e ) => {
                    val s =
                      e match {
                        case Left( wbs ) => wbs
                        case Right( ns ) => ns
                      }
                    s.toString + "," + acc
                  }
                }
              )
              val lst = 
                setStrm.last match {
                  case Left( wbs ) => wbs
                  case Right( ns ) => ns
                }
              cs + lst.toString
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
