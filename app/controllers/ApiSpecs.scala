package controllers

import com.iheart.playSwagger.SwaggerSpecGenerator
import play.api.Configuration
import play.api.libs.json.JsString
import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject.Singleton

class ApiSpecs(cc: ControllerComponents, config: Configuration)
    extends AbstractController(cc) {

  implicit val cl = getClass.getClassLoader

  val domainPackage = ""
  lazy val generator = SwaggerSpecGenerator(false, domainPackage)

  // Get's host configuration.
  val host = config.get[String]("swagger.host")

  lazy val swagger = Action { request =>
    generator
      .generate()
      .map(_ + ("host" -> JsString(host)))
      .fold(e => InternalServerError("Couldn't generate swagger."), s => Ok(s))
  }

  def specs = swagger
}
