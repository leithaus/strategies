package com.example.tutorial.slick

import Application.db
import Users._
import scala.slick.driver.H2Driver.simple._
import org.slf4j.LoggerFactory
import com.typesafe.scalalogging.slf4j.Logger
import org.joda.time.DateTime
import scala.util.Random

import scala.language.reflectiveCalls

object Main extends App {

  val logger = Logger(LoggerFactory.getLogger(this.getClass))
  val MaxUserCount = 100

  val random = new Random

  db withTransaction {
    implicit session =>
      users.ddl.create
      users.ddl.createStatements.foreach(x => logger info s"DDL statement executed: $x")
  }

  db withTransaction {
    implicit session =>
      for (i <- 0 until random.nextInt(MaxUserCount))
        users.insert(User(None, s"example$i@example.com", "password", DateTime.now(), false))
  }

  db.withTransaction {
    implicit session =>
      logger.info(s"There are ${users.count.run} users in the database")
      val emailToFind = s"example${random.nextInt(MaxUserCount)}@example.com"
      logger.info(s"User with email '$emailToFind' has registered at: ${users.findByEmail(emailToFind).list().headOption.map(_.registredAt).getOrElse("Not yet")}")
  }

  db withTransaction {
    implicit session =>


      users.ddl.dropStatements.foreach(x => logger info s"DROP statement executed: $x")
      users.ddl.drop
  }

}

