import java.io.File

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import play.api.{ApplicationLoader, Environment, Mode}

package object test {
  trait BaseSpec extends ScalaFutures {
    implicit override val patienceConfig = PatienceConfig(
      timeout = Span(2, Seconds),
      interval = Span(5, Millis)
    )
  }

  def fakeApp = {
    val appLoader = new AppLoader

    val context = ApplicationLoader.createContext(
      new Environment(new File("."), ApplicationLoader.getClass.getClassLoader, Mode.Test)
    )

    appLoader.load(context)
  }
}
