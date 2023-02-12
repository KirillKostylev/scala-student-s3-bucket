name := "playTestProject"

version := "1.0"

lazy val `playtestproject` =
  (project in file(".")).enablePlugins(PlayScala, SwaggerPlugin)

swaggerDomainNameSpaces := Seq("models")
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
resolvers += Resolver.jcenterRepo

scalaVersion := "2.13.10"

libraryDependencies ++= Seq(
  jdbc,
  ehcache,
  ws,
  specs2 % Test,
  guice,
  "commons-io" % "commons-io" % "2.11.0",
  "com.github.seratch" %% "awscala-s3" % "0.8.5",
//  "io.swagger" %% "swagger-play2" % "1.7.1",
  "com.iheart" %% "play-swagger" % "0.10.7"
)
