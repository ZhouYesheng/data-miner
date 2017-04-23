import sbtassembly.MergeStrategy

name := "spark-miner"

version := "1.0"

scalaVersion := "2.11.8"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Maven Repository" at "http://repo1.maven.org/maven2/",
  "maven-restlet" at "http://maven.restlet.org")

libraryDependencies ++= {
  val spark_version = "1.6.2"
  val jsckson_version= "1.9.13"
  Seq(
    "org.apache.spark" %% "spark-core" % spark_version,
    "org.apache.spark" %% "spark-mllib" % spark_version,
    "redis.clients" % "jedis" % "2.8.1",
    "org.ansj" % "ansj_seg" % "5.1.1",
    "mysql" % "mysql-connector-java" % "5.1.39"
  )
}
assemblyMergeStrategy in assembly := {
  case "application.conf"                            => MergeStrategy.concat
  case "unwanted.txt"                                => MergeStrategy.discard
  case PathList(ps @ _*) if ps.contains("META-INF") => MergeStrategy.discard
  case x => MergeStrategy.first
}