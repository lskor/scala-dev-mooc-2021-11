
import sbt.ModuleID
import sbt._

object Dependencies {

  lazy val KindProjectorVersion = "0.10.3"
  lazy val kindProjector = "org.typelevel" %% "kind-projector" % KindProjectorVersion

  lazy val JunitVersion = "4.12"
  lazy val junit = "junit" % "junit" % JunitVersion  % "test"

  lazy val JunitInterfaceVersion = "0.11"
  lazy val junitInterface = "com.novocode" % "junit-interface" % JunitInterfaceVersion % "test"
}
