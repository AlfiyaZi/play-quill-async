package services

import db.DBContext
import repositories.{DevicesRepository, UsersRepository}
import tables.{Device, User}

import scala.concurrent.{ExecutionContext, Future}

class UserService(implicit val ex: ExecutionContext, val ctx: DBContext)
  extends UsersRepository with DevicesRepository {

  import ctx._

  def find(id: Long): Future[Option[User]] =
    ctx.run(byId(id)).map(_.headOption)

  def findWithDevices(id: Long): Future[List[(User, Option[Device])]] =
    ctx.run(byIdWithDevices(id))

  def create(user: User): Future[User] = {
    ctx.run(quote(users.insert))(List(user)).map { newId =>
      user.copy(id = newId.head)
    }
  }

  def update(user: User): Future[List[Long]] = {
    ctx.run(byId(user.id).update)(List(user))
  }

  def delete(user: User): Future[Long] = {
    ctx.run(byId(user.id).delete)
  }

  def dynamic(): Future[List[User]] = {
    ctx.run(dynamicQuote)
  }
}
