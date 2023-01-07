ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "Clustering_Scala"
  )

ThisBuild / resolvers += Resolver.bintrayRepo("cibotech", "public")

ThisBuild / libraryDependencies ++= Seq(
  "com.github.tototoshi" %% "scala-csv" % "1.3.10",
  "com.github.haifengl" %% "smile-scala" % "3.0.0"
)
