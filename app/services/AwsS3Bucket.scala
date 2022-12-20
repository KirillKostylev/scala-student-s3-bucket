package services

import awscala.Region0
import awscala.s3.{Bucket, S3, S3Object}
import com.amazonaws.services.s3.model.ObjectMetadata
import com.typesafe.config.ConfigFactory
import models.Student
import play.api.libs.json.{Json, OFormat}

import java.net.URL
import javax.inject.Singleton

@Singleton
class AwsS3Bucket[M <: Student]() {
  val JSON_EXTENSION = ".json"
  val JSON_CONTENT_TYPE = "application/json"

  val presignedUrlTtl: Int =
    ConfigFactory.load().getInt("s3.presigned.url.ttl.minutes")

  implicit val format: OFormat[Student] = Json.format[Student]
  implicit val s3: S3 = S3.at(Region0.EU_CENTRAL_1)

  val bucketName: String = ConfigFactory.load().getString("s3.bucket.name")
  val bucket: Bucket =
    s3.bucket(bucketName).getOrElse(s3.createBucket(bucketName))

  def add(key: String, value: Student): Unit = {

    val bytes = Json.toJson[Student](value).toString().getBytes()
    bucket.putObject(
      key,
      bytes,
      createMetadata(bytes)
    )
  }

  def getPresignedUrl(key: String): Option[URL] = {
    getS3Object(key).map(s3Object =>
      s3Object.generatePresignedUrl(
        awscala.DateTime.now().plusMinutes(presignedUrlTtl)
      )
    )
  }

  def getObjectByKey(key: String): Option[Student] = {

    getS3Object(key)
      .map(s3Obj => Json.parse(s3Obj.content))
      .map(jsValue => Json.fromJson[Student](jsValue).get)

//      .map(s3Object => Json.parse(s3Object.content))
//      .map(node => Json.fromJson(node, asInstanceOf[M].getClass))
  }

  private def getS3Object(key: String): Option[S3Object] = {
    bucket.get(key)
  }

  private def createMetadata(bytes: Array[Byte]): ObjectMetadata = {
    val metadata = new ObjectMetadata()
    metadata.setContentLength(bytes.length)
    metadata.setContentType(JSON_CONTENT_TYPE)

    metadata
  }
}
