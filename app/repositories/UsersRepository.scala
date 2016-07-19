package repositories

import db.Repository
import tables.User

trait UsersRepository extends Repository { self: DevicesRepository =>
  import ctx._

  val users = quote(query[User].schema(_.entity("users").generated(_.id)))

  val r = scala.util.Random

  def byId(id: Long) = quote(users.filter(_.id == lift(id)))

  def byIdWithDevices(id: Long) = quote {
    users
      .leftJoin(devices)
      .on((u, d) => u.id == d.userId)
      .filter(_._1.id == lift(id))
  }

  def dynamicQuote: Quoted[Query[User]] = quote {
    users.filter(_.isActive == lift(r.nextBoolean()))
  }
}
