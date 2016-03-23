package services

import io.getquill._
import db.JdbcDatabase

case class User(id: Long, name: String, isActive: Boolean)

class UserServices(db: JdbcDatabase) {

  val users = quote(query[User](_.entity("users").generated(_.id)))

  def find(id: Long) =
    db.run(users.filter(c => c.id == id && c.isActive)).headOption


  def create(user: User) = {
    val newId = db.run(users.insert(user))
    user.copy(id = newId)
  }

  def delete(user: User) =
    db.run(users.filter(_.id == user.id).delete )


  def update(user: User) =
    db.run(users.filter(_.id == user.id).update(_.name -> user.name, _.isActive -> user.isActive))

}
