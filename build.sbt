name := "playTestProject"

version := "1.0"

lazy val `playtestproject` =
  (project in file(".")).enablePlugins(PlayScala, SwaggerPlugin)

swaggerDomainNameSpaces := Seq("models")
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

scalaVersion := "2.13.10"

libraryDependencies ++= Seq(
  ws,
  specs2 % Test,
  guice,
  "com.github.seratch" %% "awscala-s3" % "0.8.5",
  "com.iheart" %% "play-swagger" % "0.10.7"
)
