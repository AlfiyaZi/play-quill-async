package repositories

import io.getquill._
import tables.User

object UsersRepository {
  val table = quote(query[User].schema(_.entity("users").generated(_.id)))

  val r = scala.util.Random

  def byId(id: Long) = quote(table.filter(_.id == lift(id)))

  def byIdWithDevices(id: Long) = quote {
    table
      .leftJoin(DevicesRepository.table)
      .on((u, d) => u.id == d.userId)
      .filter(_._1.id == lift(id))
  }

  def dynamic: Quoted[Query[User]] = quote {
    table.filter(_.isActive == lift(r.nextBoolean()))
  }
}
