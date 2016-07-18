package services

import io.getquill._
import db.Database
import tables.{Device, User}
import repositories.UsersRepository

import scala.concurrent.{ExecutionContext, Future}

class UserService(implicit ex: ExecutionContext) {
  def find(id: Long): Future[Option[User]] =
    Database.db.run(UsersRepository.byId(id)).map(_.headOption)

  def findWithDevices(id: Long): Future[List[(User, Option[Device])]] =
    Database.db.run(UsersRepository.byIdWithDevices(id))

  def create(user: User): Future[User] = {
    Database.db.run(UsersRepository.table.insert)(List(user)).map { newId =>
      user.copy(id = newId.head)
    }
  }

  def update(user: User): Future[List[Long]] = {
    Database.db.run(UsersRepository.byId(user.id).update)(List(user))
  }

  def delete(user: User): Future[Long] = {
    Database.db.run(UsersRepository.byId(user.id).delete)
  }

  def dynamic(): Future[List[User]] = {
    Database.db.run(UsersRepository.dynamic)
  }
}
