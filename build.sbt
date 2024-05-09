name := "synth-kit4s"

version := "1.1"

scalaVersion := "3.4.1"

libraryDependencies ++= Seq(
)
libraryDependencies += "org.typelevel" %% "cats-effect" % "3.5.4"
libraryDependencies += "org.scalafx" %% "scalafx" % "16.0.0-R24"

libraryDependencies ++= {
  // Determine OS version of JavaFX binaries
  lazy val osName = System.getProperty("os.name") match {
    case n if n.startsWith("Linux") => "linux"
    case n if n.startsWith("Mac") => "mac"
    case n if n.startsWith("Windows") => "win"
    case _ => throw new Exception("Unknown platform!")
  }
  Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
    .map(m => "org.openjfx" % s"javafx-$m" % "16" classifier osName)
}
