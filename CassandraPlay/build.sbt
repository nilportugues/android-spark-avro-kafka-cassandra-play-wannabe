name := "CassandraPlay"

scalaVersion := "2.12.2"

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  ehcache, guice, openId,
  "com.typesafe.play" %% "play-slick" % "3.0.0",
  "com.typesafe.play" %% "play-json" % "2.6.0",

  "com.outworkers" %% "phantom-dsl" % "2.12.1",
  "com.outworkers" %% "phantom-connectors" % "2.12.1",

  specs2 % Test
)

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")
