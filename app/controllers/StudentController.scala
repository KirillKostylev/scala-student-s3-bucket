package controllers

import models.{Student, StudentRequest}
import play.api.Logging
import play.api.libs.json.{Json, OFormat}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.StudentService

import javax.inject.{Inject, Singleton}

@Singleton
class StudentController @Inject() (
    val controllerComponents: ControllerComponents,
    val studentService: StudentService
) extends BaseController
    with Logging {

  private implicit val studentFormat: OFormat[Student] = Json.format[Student]
  private implicit val studentRequestFormat: OFormat[StudentRequest] =
    Json.format[StudentRequest]

  def save(): Action[AnyContent] = Action { implicit request =>
    val content = request.body
    val jsonObj = content.asJson

    val student: Option[StudentRequest] = jsonObj.flatMap(
      Json.fromJson[StudentRequest](_).asOpt
    )

    student match {
      case Some(value) =>
        val student1 = studentService.save(value)
        val newStudent = Json.toJson[Student](student1)
        logger.error("User was created with id = " + student1.id)

        Created(newStudent)
      case None => BadRequest
    }
  }

  def findById(itemId: String): Action[AnyContent] = Action {
    studentService.findById(itemId) match {
      case Some(value) =>
        Ok(Json.toJson[Student](value))
      case None => NotFound
    }
  }

  def getPresignedUrlById(itemId: String): Action[AnyContent] = Action {
    studentService.getPresignedUrlById(itemId) match {
      case Some(value) =>
        Ok(Json.toJson[String](value.toString))
      case None => NotFound
    }
  }
}
