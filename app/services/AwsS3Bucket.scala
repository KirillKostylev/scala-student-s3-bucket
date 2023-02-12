package services

import awscala.Region0
import awscala.s3.{Bucket, S3}

trait AwsS3Bucket {
  lazy val JSON_CONTENT_TYPE = "application/json"

  lazy implicit val s3: S3 = S3.at(Region0.EU_CENTRAL_1)

  def getBucket(bucketName: String): Bucket = {
    s3.bucket(bucketName).getOrElse(s3.createBucket(bucketName))
  }
}
