import Dependencies._

ThisBuild / scalaVersion     := "3.1.3"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "uk.m0nom"
ThisBuild / organizationName := "norad"

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-lambda-java-core" % "1.2.1",
  "com.amazonaws" % "aws-lambda-java-events" % "3.11.0",
  "software.amazon.awssdk" % "core" % "2.17.267",
  "software.amazon.awssdk" % "s3" % "2.17.267",
  "software.amazon.awssdk" % "auth" % "2.17.267",
  "software.amazon.awssdk" % "lambda" % "2.17.267",
  scalaTest % Test,
  scalaTestFunSuite % Test
)

lazy val root = (project in file("."))
  .settings(
    name := "norad-tle-lambda",
    libraryDependencies += scalaTest % Test
  )

assemblyJarName in assembly := "norad-tle-lambda.jar"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) =>
    xs map {
      _.toLowerCase
    } match {
      case "services" :: xs =>
        MergeStrategy.filterDistinctLines
      case _ => MergeStrategy.discard
    }
  case x => MergeStrategy.first
}
// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
