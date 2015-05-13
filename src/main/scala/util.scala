// -*- mode: Scala;-*- 
// Filename:    util.scala 
// Authors:     lgm                                                    
// Creation:    Tue May 12 20:18:25 2015 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package com.biosimilarity.mdp4tw.strategies

object Utilities {
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
        }
      }      
    }
}
