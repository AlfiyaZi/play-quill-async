import org.scalatest.TestData
import org.scalatestplus.play._
import play.api.Application
import play.api.test._
import play.api.test.Helpers._
import test._

class ApplicationSpec extends PlaySpec with OneAppPerTest {
  override def newAppForTest(testData: TestData): Application = fakeApp

  "Routes" should {
    "send 404 on a bad request" in  {
      route(app, FakeRequest(GET, "/boum")).map(status(_)) mustBe Some(NOT_FOUND)
    }
  }
}
