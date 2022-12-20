package models

import java.util.UUID

case class Student(
    id: String = UUID.randomUUID().toString,
    firstName: String,
    secondName: String,
    faculty: String,
    age: Int,
    email: String,
    var validated: Boolean = false
)
