package models

import play.api.libs.json.{Json, OFormat}

case class StudentResponse(
    student: Student,
    presignedUrl: String
)

object StudentResponse {
  implicit val format: OFormat[StudentResponse] =
    Json.format[StudentResponse]
}
