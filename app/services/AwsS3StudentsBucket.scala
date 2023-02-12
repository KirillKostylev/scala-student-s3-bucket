package services

import awscala.s3.{Bucket, S3Object}
import com.amazonaws.services.s3.model.ObjectMetadata
import com.typesafe.config.ConfigFactory
import models.Student
import play.api.libs.json.Json

import java.net.URL
import javax.inject.Singleton

@Singleton
class AwsS3StudentsBucket extends AwsS3Bucket {
  lazy val bucketName: String = ConfigFactory.load().getString("s3.bucket.name")
  lazy val presignedUrlTtl: Int =
    ConfigFactory.load().getInt("s3.presigned.url.ttl.minutes")

  lazy val bucket: Bucket = getBucket(bucketName)

  def add(value: Student): Unit = {
    val bytes = Json.toJson[Student](value).toString().getBytes()
    bucket.putObject(
      value.id,
      bytes,
      createMetadata(bytes)
    )
  }

  def getPresignedUrl(key: String): Option[URL] = {
    getS3Object(key)
      .map(s3Object =>
        s3Object.generatePresignedUrl(
          awscala.DateTime.now().plusMinutes(presignedUrlTtl)
        )
      )
  }

  def getObjectByKey(key: String): Option[Student] = {
    getS3Object(key)
      .map(s3Obj => Json.parse(s3Obj.content).as[Student])
  }

  def deleteObjectByKey(key: String): Unit = {
    bucket.delete(key)
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
