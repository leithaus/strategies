// -*- mode: Scala;-*- 
// Filename:    elements.scala 
// Authors:     lgm                                                    
// Creation:    Tue May 12 20:09:10 2015 
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

