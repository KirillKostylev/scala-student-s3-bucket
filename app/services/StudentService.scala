package services

import models.{Student, StudentRequest}
import play.api.Logging

import java.net.URL
import javax.inject.{Inject, Singleton}

@Singleton
case class StudentService @Inject() (studentsBucket: AwsS3Bucket[Student])
    extends Logging {

  def save(studentRequest: StudentRequest): Student = {
    val newStudent = validate(
      Student(
        firstName = studentRequest.firstName,
        secondName = studentRequest.secondName,
        faculty = studentRequest.faculty,
        age = studentRequest.age,
        email = studentRequest.email
      )
    )

    studentsBucket.add(newStudent.id, newStudent)

    newStudent
  }

  def findById(id: String): Option[Student] = {
    studentsBucket.getObjectByKey(id)
  }

  def getPresignedUrlById(id: String): Option[URL] = {
    studentsBucket.getPresignedUrl(id)
  }

  private def validate(student: Student): Student = {
    if (student.age > 0) student.validated = true
    student
  }

}
