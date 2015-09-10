name := "slicktrix"

organization := "com.synereo"

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.4"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies ++= Seq(
  /* Database access related dependencies */
  "com.h2database" % "h2" % "1.4.177",
  //"com.typesafe.slick" %% "slick" % "2.0.1",
  "com.typesafe.slick" %% "slick" % "2.1.0-M1",
  //"com.typesafe.slick" %% "slick" % "3.0.2",
  //"com.typesafe.slick" %% "slick-testkit" % "2.0.1",
  "com.typesafe.slick" %% "slick-testkit" % "2.1.0-M1",
  //"com.typesafe.slick" %% "slick-testkit" % "3.0.2",
  "com.github.tototoshi" %% "slick-joda-mapper" % "1.0.1",
  //"com.github.tototoshi" %% "slick-joda-mapper" % "2.0.0",
  "com.jolbox" % "bonecp" % "0.8.0.RELEASE",
  /* Logging */
  "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",
  "ch.qos.logback" % "logback-classic" % "1.1.2",
  /* Testing */
  // "org.scalatest" %% "scalatest" % "2.1.3" % "test",
  /* Utilities */
  "com.novocode" % "junit-interface" % "0.10" % "test",
  "com.typesafe" % "config" % "1.2.0",
  "joda-time" % "joda-time" % "2.3",
  "org.joda" % "joda-convert" % "1.6",
  "postgresql" % "postgresql" % "9.1-901.jdbc4" % "test"
)

// Disable all other test frameworks to silence the warnings in Activator
testFrameworks := Seq(new TestFramework("com.novocode.junit.JUnitFramework"))

testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v", "-s", "-a")

parallelExecution in Test := false

logBuffered := false
