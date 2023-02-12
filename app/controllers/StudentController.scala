package controllers

import models.{Student, StudentRequest, StudentResponse}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.StudentService

import javax.inject.{Inject, Singleton}

@Singleton
class StudentController @Inject() (
    val controllerComponents: ControllerComponents,
    val studentService: StudentService
) extends BaseController {

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
          StudentResponse(
            studentEntity,
            presignedUtl.getOrElse("").toString
          )
        )

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

  def deleteById(studentId: String): Action[AnyContent] = Action {
    studentService.deleteById(studentId)
    Ok
  }
}
