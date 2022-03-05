name := "CirceOverview"

version := "0.1"

scalaVersion := "2.12.12"

val circeVersion = "0.12.3"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "com.jakewharton.fliptables" % "fliptables" % "1.1.0"
)
