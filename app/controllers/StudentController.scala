package controllers

import models.{PresignedUrlResponse, Student, StudentRequest, StudentResponse}
import play.api.Logging
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.StudentService

import javax.inject.{Inject, Singleton}

@Singleton
class StudentController @Inject() (
    val controllerComponents: ControllerComponents,
    val studentService: StudentService
) extends BaseController
    with Logging {

  def save(): Action[AnyContent] = Action { implicit request =>
    val content = request.body
    val jsonObj: Option[JsValue] = content.asJson

    val student: Option[StudentRequest] = jsonObj.flatMap(
      Json.fromJson[StudentRequest](_).asOpt
    )

    student match {
      case Some(value) =>
        val studentEntity = studentService.save(value)
        val presignedUtl = studentService.getPresignedUrlById(studentEntity.id)
        val newStudent = Json.toJson[StudentResponse](
          StudentResponse(studentEntity, presignedUtl.orNull)
        )
        Created(newStudent)
      case None => BadRequest
    }
  }

  def findById(itemId: String): Action[AnyContent] = Action {
    studentService
      .findById(itemId)
      .map(student => Ok(Json.toJson[Student](student)))
      .getOrElse(NotFound)
  }

  def getPresignedUrlById(itemId: String): Action[AnyContent] = Action {
    studentService
      .getPresignedUrlById(itemId)
      .map(dto => Ok(Json.toJson[PresignedUrlResponse](dto)))
      .getOrElse(NotFound)
  }

  def deleteById(studentId: String): Action[AnyContent] = Action {
    studentService
      .findById(studentId)
      .map(_ => studentService.deleteById(studentId))
      .map(_ => Ok)
      .getOrElse(NotFound)
  }
}
