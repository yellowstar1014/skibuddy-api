name := """skibuddy-api"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  javaWs,
  cache,
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "4.3.9.Final",
  //"postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  "com.google.api-client" % "google-api-client" % "1.19.1",
  "com.google.api-client" % "google-api-client-gson" % "1.19.1",
  "org.postgresql" % "postgresql" % "9.4-1205-jdbc42"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
