ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "Clustering_Scala"
  )

ThisBuild / libraryDependencies ++= Seq(
  "com.github.tototoshi" %% "scala-csv" % "1.3.10",
  "io.github.pityka" %% "nspl-awt" % "0.9.0",
//  "io.github.pityka" %% "nspl-canvas-js" % "0.9.0"
)
