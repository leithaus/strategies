package com.example.tutorial.slick

import org.joda.time.DateTime
import scala.slick.driver.H2Driver.simple._
import com.github.tototoshi.slick.H2JodaSupport._

case class User(id: Option[Long], email: String, password: String, registredAt: DateTime, locked: Boolean)

class Users(tag: Tag) extends Table[User](tag, None, "users") {
  def id = column[Long]("user_id", O.PrimaryKey, O.AutoInc)

  def email = column[String]("email")

  def password = column[String]("password")

  def registeredAt = column[DateTime]("registered_at")

  def locked = column[Boolean]("locked")

  def * = (id.?, email, password, registeredAt, locked) <> (User.tupled, User.unapply)

  def idxEmail = index("idx_email", email, unique = true)
}

object Users {
  val users = new TableQuery(new Users(_)) {
    def count = this.length
    def findByEmail(email: String) = this.filter(_.email === email)
  }
}
