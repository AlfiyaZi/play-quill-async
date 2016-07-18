package repositories

import io.getquill._
import tables.Device

object DevicesRepository {
  val table = quote(query[Device].schema(_.entity("devices").generated(_.id)))
}
