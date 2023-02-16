package services

import models.{PresignedUrlResponse, Student, StudentRequest}
import play.api.Logging

import javax.inject.{Inject, Singleton}

@Singleton
case class StudentService @Inject() (
    studentsBucket: AwsS3StudentsBucket
) extends Logging {

  def save(studentRequest: StudentRequest): Student = {
    val newStudent = validateAndUpdate(
      Student(
        firstName = studentRequest.firstName,
        secondName = studentRequest.secondName,
        faculty = studentRequest.faculty,
        age = studentRequest.age,
        email = studentRequest.email
      )
    )

    studentsBucket.add(newStudent)
    logger.info("Student was created with id = " + newStudent.id)
    newStudent
  }

  def findById(id: String): Option[Student] = {
    studentsBucket.getObjectByKey(id)
  }

  def getPresignedUrlById(id: String): Option[PresignedUrlResponse] = {
    studentsBucket.getPresignedUrl(id)
  }

  def deleteById(id: String): Unit = {
    studentsBucket.deleteObjectByKey(id)
    logger.info("Student was deleted with id = " + id)

  }

  private def validateAndUpdate(student: Student): Student = {
    if (student.age > 0) student.isValid = true
    student
  }
}
