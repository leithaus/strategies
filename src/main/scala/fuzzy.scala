// -*- mode: Scala;-*- 
// Filename:    fuzzy.scala 
// Authors:     lgm                                                    
// Creation:    Tue May 12 20:12:34 2015 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package com.biosimilarity.mdp4tw.strategies

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
}
