package services

import io.getquill._

import db.Database
import tables.Device
import repositories.DevicesRepository

import scala.concurrent.{ExecutionContext, Future}

class DeviceService(implicit ex: ExecutionContext) {
  def findDevicesForUser(userId: Long): Future[Seq[Device]] = {
    Database.db.run(DevicesRepository.table.filter(_.userId == lift(userId)))
  }
}
