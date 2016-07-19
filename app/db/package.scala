import io.getquill.{MysqlAsyncContext, SnakeCase}

package object db {
  class DBContext(config: String) extends MysqlAsyncContext[SnakeCase](config)

  trait Repository {
    val ctx: DBContext
  }
}
