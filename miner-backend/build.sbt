name := """miner-backend"""
organization := "com.young.cms"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies += filters
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % Test
libraryDependencies += "org.codehaus.jackson" % "jackson-core-asl" % "1.9.13"
libraryDependencies += "org.codehaus.jackson" % "jackson-mapper-asl" % "1.9.13"
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.38"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.young.cms.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.young.cms.binders._"
