package controllers

import tables.User
import services.UserService

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.mvc.{Action, Controller}

import scala.concurrent.{ExecutionContext, Future}

class UserController(usersRepository: UserService)
                    (implicit ex: ExecutionContext) extends Controller {

  implicit val userReads: Reads[User] = (
    Reads.pure(0L) and
    (JsPath \ "name").read[String] and
    (JsPath \ "isActive").read[Boolean]
  )(User.apply _)

  implicit val userWrites: Writes[User] = Json.writes[User]

  def get(id: Long) = Action.async { request =>
    usersRepository.find(id) map {
      case None => NotFound
      case Some(user) => Ok(Json.toJson(user))
    }
  }

  def create = Action.async(parse.json) { request =>
    Json.fromJson[User](request.body).fold(
      invalid => Future.successful(BadRequest),
      user => {
        usersRepository.create(user).map { userCreated =>
          Created.withHeaders(LOCATION -> s"/users/${userCreated.id}")
        }
      }
    )
  }

  def delete(id: Long) = Action.async { request =>
    usersRepository.find(id) flatMap {
      case None => Future.successful(NotFound)
      case Some(user) =>
        usersRepository.delete(user).map(_ => NoContent)
    }
  }

  def update(id: Long) = Action.async(parse.json) { request =>
    Json.fromJson[User](request.body).fold(
      invalid => Future.successful(BadRequest),
      user => {
        usersRepository.update(user.copy(id = id)).map(_ => NoContent)
      }
    )
  }
}
