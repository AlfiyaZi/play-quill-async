package services

import test._
import tables.User

import play.api._
import org.scalatest.Matchers._
import org.scalatest.{TestData, WordSpec}
import org.scalatestplus.play.OneAppPerTest

class UserServicesSpec extends WordSpec with OneAppPerTest with BaseSpec {
  override def newAppForTest(testData: TestData): Application = fakeApp

  "UserServices" should {
    "create and find" in {
      val userServices = app.injector.instanceOf(classOf[UserService])
      val user = userServices.create(User(0L, "test1", true)).futureValue
      user.id !== 0L
      val userFound = userServices.find(user.id).futureValue
      userFound shouldBe defined
      userFound.foreach(_.name shouldBe "test1")
    }
  }
}
