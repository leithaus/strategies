// -*- mode: Scala;-*- 
// Filename:    SpecialKQuery.scala 
// Authors:     lgm                                                    
// Creation:    Thu Aug 27 17:02:24 2015 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package com.synereo.slicktrix.specialk.query

import slick.compiler._
import scala.collection.immutable.HashMap
import slick.SlickException
import slick.util._
import slick.ast.{SymbolNamer, Node}
import org.slf4j.LoggerFactory

class SpecialKQueryCompiler(
  override val phases: Vector[Phase]
) extends QueryCompiler( phases ) {
}

object SpecialKQueryCompiler {
  /** The standard phases of the query compiler */
  val standardPhases = Vector(
    /* Clean up trees from the lifted embedding */
    Phase.assignUniqueSymbols,
    /* Distribute and normalize */
    Phase.inferTypes,
    Phase.expandTables,
    Phase.forceOuterBinds,
    //Phase.removeMappedTypes,
    /* Convert to column form */
    //Phase.expandSums,
    // optional removeTakeDrop goes here
    // optional emulateOuterJoins goes here
    //Phase.expandConditionals,
    Phase.expandRecords,
    Phase.flattenProjections,
    /* Optimize for SQL */
    //Phase.rewriteJoins,
    //Phase.verifySymbols,
    Phase.relabelUnions
  )

  /** Extra phases for translation to SQL comprehensions */
  val specialkPhases = Vector(
  )

  /** Extra phases needed for the QueryInterpreter */
  val interpreterPhases = Vector(
  )

  /** The default compiler */
  val standard = new SpecialKQueryCompiler(standardPhases)

  /** Construct a new `QueryCompiler` with the given phases */
  def apply(phases: Phase*) = new SpecialKQueryCompiler(phases.toVector)
}

package usage {  
  import scala.language.higherKinds
  import org.junit.Assert._
  import com.typesafe.slick.testkit.util.{RelationalTestDB, TestkitTest}
    
}
