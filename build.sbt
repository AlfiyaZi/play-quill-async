name := """play-quill-jdbc"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "io.getquill" %% "quill-jdbc" % "0.8.0",
  "io.getquill" %% "quill-async" % "0.8.0",
  "mysql" % "mysql-connector-java" % "5.1.36",
  "com.typesafe.play" % "play-jdbc-evolutions_2.11" % "2.5.0",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0-RC1" % Test
)

resolvers ++= Seq(
  "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
)

unmanagedClasspath in Compile += baseDirectory.value / "conf"

routesGenerator := InjectedRoutesGenerator
