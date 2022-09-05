import Dependencies._

ThisBuild / scalaVersion     := "3.1.3"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "uk.m0nom"
ThisBuild / organizationName := "norad"

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-lambda-java-core" % "1.2.1",
  "com.amazonaws" % "aws-lambda-java-events" % "3.11.0",
  "com.amazonaws" % "aws-java-sdk-core" % "1.12.290",
  "com.amazonaws" % "aws-java-sdk-s3" % "1.12.290",
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
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
