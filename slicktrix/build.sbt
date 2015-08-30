name := "slicktrix"

organization := "com.synereo"

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.4"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies ++= Seq(
  /* Database access related dependencies */
  "com.h2database" % "h2" % "1.4.177",
  "com.typesafe.slick" %% "slick" % "2.0.1",
  "com.github.tototoshi" %% "slick-joda-mapper" % "1.0.1",
  "com.jolbox" % "bonecp" % "0.8.0.RELEASE",
  /* Logging */
  "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",
  "ch.qos.logback" % "logback-classic" % "1.1.2",
  /* Testing */
  "org.scalatest" %% "scalatest" % "2.1.3" % "test",
  /* Utilities */
  "com.typesafe" % "config" % "1.2.0",
  "joda-time" % "joda-time" % "2.3",
  "org.joda" % "joda-convert" % "1.6"
)
