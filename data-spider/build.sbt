name := "data-spider"

version := "1.0"

scalaVersion := "2.11.8"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Maven Repository" at "http://repo1.maven.org/maven2/",
  "maven-restlet" at "http://maven.restlet.org")

libraryDependencies ++= {
  val httpclient_version = "4.5.3"
  val httpcore_version="4.4.6"
  val commons_collections_version="3.2.1"
  val commons_io_version="2.4"
  val jsoup_version="1.10.2"
  val commons_lang_version="3.4"
  val jackson_version="1.9.13"
  val curator_version="2.11.1"
  val xstream_version="1.4.9"
  val selenium_version="3.2.0"
  val guava_version="20.0"
  val slf4j_version="1.7.25"
  Seq(
    "org.apache.httpcomponents" % "httpclient" % httpclient_version,
    "org.apache.httpcomponents" % "httpmime" % httpclient_version,
    "org.apache.httpcomponents" % "httpcore" % httpcore_version,
    "commons-collections" % "commons-collections" % commons_collections_version,
    "commons-io" % "commons-io" % commons_io_version,
    "org.jsoup" % "jsoup" % jsoup_version,
    "org.apache.commons" % "commons-lang3" % commons_lang_version,
    "org.codehaus.jackson" % "jackson-mapper-asl" % jackson_version,
    "org.codehaus.jackson" % "jackson-core-asl" % jackson_version,
    "org.apache.curator" % "curator-framework" % curator_version,
    "org.apache.curator" % "curator-recipes" % curator_version,
    "org.apache.curator" % "curator-client" % curator_version exclude("com.google.guava","guava"),
    "com.google.guava" % "guava" % guava_version,
    "com.thoughtworks.xstream" % "xstream" % xstream_version,
    "org.seleniumhq.selenium" % "selenium-java" % selenium_version,
    "org.seleniumhq.selenium" % "selenium-chrome-driver" % selenium_version exclude("com.google.guava","guava"),
    "org.slf4j" % "slf4j-api" % slf4j_version
  )
}
    