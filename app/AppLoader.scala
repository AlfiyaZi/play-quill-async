import controllers.UserController
import services.UserService
import play.api.ApplicationLoader.Context
import play.api.db.evolutions.Evolutions
import play.api.db.{DBComponents, HikariCPComponents}
import play.api.{Application, ApplicationLoader, BuiltInComponentsFromContext}
import play.api.inject.{Injector, NewInstanceInjector, SimpleInjector}
import play.api.routing.Router
import play.api.routing.sird._

class AppLoader extends ApplicationLoader {
  implicit val ex = scala.concurrent.ExecutionContext.Implicits.global

  override def load(context: Context): Application =
    new BuiltInComponentsFromContext(context) with DBComponents with HikariCPComponents {

    lazy val userServices = new UserService
    lazy val userController = new UserController(userServices)

    val router = Router.from {
      case GET(p"/users/${long(id)}")    => userController.get(id)
      case POST(p"/users")               => userController.create
      case DELETE(p"/users/${long(id)}") => userController.delete(id)
      case PUT(p"/users/${long(id)}")    => userController.update(id)
    }

    override lazy val injector: Injector =
      new SimpleInjector(NewInstanceInjector) +
        userServices + router + cookieSigner + csrfTokenSigner +
        httpConfiguration + tempFileCreator + global

    Evolutions.applyEvolutions(dbApi.database("default"))
  }.application
}