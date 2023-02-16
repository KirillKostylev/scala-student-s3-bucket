package models

import awscala.DateTime
import play.api.libs.json.{Json, OFormat}

case class PresignedUrlResponse(
    presignedUrl: String,
    expiredTime: String
)

case object PresignedUrlResponse {
  implicit val format: OFormat[PresignedUrlResponse] =
    Json.format[PresignedUrlResponse]
}
