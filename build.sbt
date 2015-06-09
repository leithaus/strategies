import AssemblyKeys._

organization  := "com.synereo"

name := "strategies"

version       := "0.1"

scalaVersion  := "2.10.2"

autoCompilerPlugins := true

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8",
  "-P:continuations:enable")

resolvers ++= Seq(
  "local-maven-cache repo" at "file://" + Path.userHome.absolutePath + "/.m2/repository/",
  "sonatype.repo" at "https://oss.sonatype.org/content/repositories/public/",      
  "Java.net Maven2 Repository" at "http://download.java.net/maven/2/",
  "modafocas" at "http://repo.modafocas.org/nexus/content/repositories/modafocas-release/",
  "apache.snapshots" at "http://repository.apache.org/snapshots/",
  "repository.codehaus.org" at "http://repository.codehaus.org/com/thoughtworks",
  "milton" at "http://milton.io/maven/com/ettrema/milton",
  "biosim repo" at "http://biosimrepomirror.googlecode.com/svn/trunk/"
)   

publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository")))

libraryDependencies ++= Seq(
  "org.scala-lang"         %   "scala-actors"       % "2.10.2",
  "org.scala-lang"         %   "scala-reflect"      % "2.10.2",
  "javax.persistence" % "persistence-api" % "1.0" % "provided",
  "javax.servlet" % "servlet-api" % "2.5" % "provided",
  "org.coconut.forkjoin" % "jsr166y" % "070108",
  "ru.circumflex" % "circumflex-core" % cxVersion.value,
  "ru.circumflex" % "circumflex-web" % cxVersion.value,
  "ru.circumflex" % "circumflex-ftl" % cxVersion.value,
  "jlex" % "JLex-local" % "local",
  "cup" % "java-cup-11a" % "local",
  "cup" % "java-cup-11a-runtime" % "local",
  "com.rabbitmq" % "amqp-client" % "3.5.3",      
  "com.thoughtworks.xstream" % "xstream" % "1.4.4",
  "com.typesafe" % "config" % "1.0.0",
  "ch.qos.logback" % "logback-classic" % "0.9.26",      
  "ch.qos.logback" % "logback-core" % "0.9.26",
  "log4j" % "log4j" % "1.2.17",
  "junit" % "junit" % "4.10" % "test",      
  "org.scalatest" % "scalatest_2.9.2" % "1.8" % "test",
  compilerPlugin("org.scala-lang.plugins" % "continuations" % "2.10.2")
)

//seq(Revolver.settings: _*)

sbtassembly.Plugin.assemblySettings

test in assembly := {}

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case PathList("org", "fusesource", "jansi", xs @ _*) => MergeStrategy.first
    case PathList("META-INF", "native", "osx", "libjansi.jnilib") => MergeStrategy.first
    case PathList("META-INF", "ECLIPSEF.RSA") => MergeStrategy.last
    case "plugin.properties" => MergeStrategy.last
    case "about.html" => MergeStrategy.discard
    case x => old(x)
  }
}
