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
  
  class NewQuerySemanticsTest extends TestkitTest[RelationalTestDB] {
    import tdb.profile.simple._
    
    val doRun = false
    
    override val reuseInstance = true
    
    def testNewComposition {
      
      class SuppliersStd(tag: Tag) extends Table[(Int, String, String, String, String, String)](tag, "SUPPLIERS") {
        def id = column[Int]("SUP_ID", O.PrimaryKey) // This is the primary key column
        def name = column[String]("SUP_NAME")
        def street = column[String]("STREET")
        def city = column[String]("CITY")
        def state = column[String]("STATE")
        def zip = column[String]("ZIP")
        def * = (id, name, street, city, state, zip)
      }
      val suppliersStd = TableQuery[SuppliersStd]
      
      class CoffeesStd(tag: Tag) extends Table[(String, Int, Int, Int, Int)](tag, "COFFEES") {
        def name = column[String]("COF_NAME", O.PrimaryKey)
        def supID = column[Int]("SUP_ID")
        def price = column[Int]("PRICE")
        def sales = column[Int]("SALES")
        def total = column[Int]("TOTAL")
        def * = (name, supID, price, sales, total)
          def supplier = foreignKey("SUP_FK", supID, suppliersStd)(_.id)
      }
      val coffeesStd = TableQuery[CoffeesStd]
      
      class Suppliers(tag: Tag) extends Table[(Int, String, String)](tag, "SUPPLIERS") {
        def id = column[Int]("SUP_ID", O.PrimaryKey) // This is the primary key column
        def name = column[String]("SUP_NAME")
        def street = column[String]("STREET")
        def city = column[String]("CITY")
        def state = column[String]("STATE")
        def zip = column[String]("ZIP")
        def * = (id, name, street)
      }
      val suppliers = TableQuery[Suppliers]
      
      class Coffees(tag: Tag) extends Table[(String, Int, Int, Int, Int)](tag, "COFFEES") {
        def name = column[String]("COF_NAME", O.PrimaryKey)
        def supID = column[Int]("SUP_ID")
        def price = column[Int]("PRICE")
        def sales = column[Int]("SALES")
        def total = column[Int]("TOTAL")
        def * = (name, supID, price, sales, (total * 10))
          def totalComputed = sales * price
        def supplier = foreignKey("SUP_FK", supID, suppliers)(_.id)
      }
      val coffees = TableQuery[Coffees]
      
      (suppliersStd.ddl ++ coffeesStd.ddl).create
      
      suppliersStd += (101, "Acme, Inc.",      "99 Market Street", "Groundsville", "CA", "95199")
      suppliersStd += ( 49, "Superior Coffee", "1 Party Place",    "Mendocino",    "CA", "95460")
      suppliersStd += (150, "The High Ground", "100 Coffee Lane",  "Meadows",      "CA", "93966")
      
      coffeesStd ++= Seq(
        ("Colombian",         101, 799, 1, 0),
        ("French_Roast",       49, 799, 2, 0),
        ("Espresso",          150, 999, 3, 0),
        ("Colombian_Decaf",   101, 849, 4, 0),
        ("French_Roast_Decaf", 49, 999, 5, 0)
      )
      
      def show[C[_]](name: String, g: Query[_,_]) =
        println("=========================================== "+name)
      
      val qa = for {
        c <- coffees.take(3)
      } yield (c.supID, (c.name, 42))
        show("qa", qa)
      if(doRun) {
        val ra = qa.run.toSet
        println("ra: "+ra)
        assertEquals(3, ra.size)
        // No sorting, so result contents can vary
        assertAllMatch(ra){ case (s: Int, (i: String, 42)) => () }
      }
    }
  }
}
