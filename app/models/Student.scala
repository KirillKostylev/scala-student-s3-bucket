package models

import play.api.libs.json.{Json, OFormat}

import java.util.UUID

case class Student(
    id: String = UUID.randomUUID().toString,
    firstName: String,
    secondName: String,
    faculty: String,
    age: Int,
    email: String,
    var isValid: Boolean = false
)

case object Student {
  lazy implicit val format: OFormat[Student] = Json.format[Student]
}
