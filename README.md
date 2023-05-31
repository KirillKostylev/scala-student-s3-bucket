# Play Scala project

It's a learning project based on Play Framework, performing actions with Student model and storing them in AWS S3 Bucket

### Provided endpoints:
| METHOD | PATH | DESCRIPTION                          |
|----|----|--------------------------------------|
|POST|/students| Create new student                   |
|DELETE|/students/:id | Delete user by Identifier            |
|GET|/students/:id | Read user by Identifier              |
|GET|/students/:id/presigned-url | Create presigned URL to read student |

### Project Prerequisites
* Scala 2.13
* Java 8
* SBT 1.5
* Play Framework 2.8.18
* AWS Scala client - awscala-s3
* AWS credentials (.aws/credentials)
