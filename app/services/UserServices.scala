package services

import io.getquill._
import db.JdbcDatabase

case class User(id: Long, name: String, isActive: Boolean)

class UserServices(db: JdbcDatabase) {

  val users = quote(query[User](_.entity("users").generated(_.id)))

  def find(id: Long) =
    db.run(users.filter(c => c.id == id && c.isActive)).headOption


  def create(user: User) = {
    val newId = db.run(users.insert)(List(user))
    user.copy(id = newId.head)
  }

  def delete(user: User) = {
    val id = user.id
    db.run(users.filter(_.id == id).delete)
  }


  def update(user: User) = {
    val id = user.id
    val name = user.name
    val isActive = user.isActive
    db.run(users.filter(_.id == id).update(_.name -> name, _.isActive -> isActive))
  }

}
