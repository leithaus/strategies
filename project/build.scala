import sbt._
import Keys._

object StrategiesBuild extends Build {
  
  val cxVersion = SettingKey[String]("cx-version", "2.5")
  val cxPort = SettingKey[String]("cx-port", "8180")
  val cxRouter = SettingKey[String]("cx-router", "com.biosimilarity.mdp4tw.Main")
  
  override lazy val settings =
    super.settings ++ Seq(
      cxVersion := "2.5",
      cxPort := "8180",
      cxRouter := "com.biosimilarity.mdp4tw.Main"
    )

  lazy val root =
    Project(id = "strategies",
            base = file("."),
            settings = Project.defaultSettings
          )
}
