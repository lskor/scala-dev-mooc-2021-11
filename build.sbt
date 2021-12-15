import sbt.Keys.libraryDependencies

import Dependencies._


lazy val root = (project in file("."))
  .settings(
    name := "scala-dev-mooc-2021-11",
    version := "0.1",
    scalaVersion := "2.13.3",
    libraryDependencies ++= Seq(
      kindProjector, junit, junitInterface
    ),
    addCompilerPlugin(Dependencies.kindProjector)
  )

testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework"))

scalacOptions += "-Ymacro-annotations"
