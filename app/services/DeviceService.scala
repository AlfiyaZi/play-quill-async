package services

import db.DBContext
import tables.Device
import repositories.DevicesRepository

import scala.concurrent.{ExecutionContext, Future}

class DeviceService(implicit val ex: ExecutionContext, val ctx: DBContext) extends DevicesRepository {
  def findDevicesForUser(userId: Long): Future[Option[Device]] = {
    ctx.run(byUserId(userId)).map(_.headOption)
  }
}
