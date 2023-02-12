package models

import play.api.libs.json.{Json, OFormat}

case class StudentRequest(
    firstName: String,
    secondName: String,
    faculty: String,
    age: Int,
    email: String
)

object StudentRequest {
  implicit val format: OFormat[StudentRequest] =
    Json.format[StudentRequest]
}
