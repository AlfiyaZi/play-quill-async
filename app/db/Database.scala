package db

import io.getquill._
import io.getquill.naming.SnakeCase

object Database {
  lazy val db = source {
    new MysqlAsyncSourceConfig[SnakeCase]("db.default")
  }
}
