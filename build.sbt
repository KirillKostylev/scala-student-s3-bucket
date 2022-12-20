name := "playTestProject"

version := "1.0"

lazy val `playtestproject` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
scalaVersion := "2.13.5"

libraryDependencies ++= Seq(
  jdbc,
  ehcache,
  ws,
  specs2 % Test,
  guice,
  "commons-io" % "commons-io" % "2.11.0",
  "com.github.seratch" %% "awscala-s3" % "0.9.2"
)
