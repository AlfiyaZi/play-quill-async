package repositories

import db.Repository
import tables.Device

trait DevicesRepository extends Repository {
  import ctx._

  val devices = quote(query[Device].schema(_.entity("devices").generated(_.id)))

  def byUserId(id: Long) = quote {
    devices.filter(_.id == lift(id))
  }
}
