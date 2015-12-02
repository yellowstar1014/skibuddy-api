name := """skibuddy-api"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  javaWs,
  cache,
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "5.0.4.Final",
  "mysql" % "mysql-connector-java" % "5.1.18",
  "com.google.api-client" % "google-api-client" % "1.19.1",
  "com.google.api-client" % "google-api-client-gson" % "1.19.1",
  "org.postgresql" % "postgresql" % "9.4-1205-jdbc42"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
