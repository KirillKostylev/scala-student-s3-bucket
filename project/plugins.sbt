logLevel := Level.Warn

resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.18")
addSbtPlugin("com.iheart" % "sbt-play-swagger" % "0.11.0")
