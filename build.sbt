lazy val root = (project in file(".")).settings(
  name := "SparkKafkaCassandra",
  version := "1.0",
  scalaVersion := "2.11.8",
  startYear := Some(2017),
  mainClass in Compile := Some("hr.fpopic.streaming.Main"),
  assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false),
  parallelExecution in Test := true,
  test in assembly := {} // remove if you want to start tests
)

lazy val unprovidedDependencies = Seq(
  "org.scalactic" %% "scalactic" % "3.0.1",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)

libraryDependencies ++= unprovidedDependencies

val sparkVersion = "2.1.1"

lazy val providedDependencies = Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-streaming-kafka-0-8" % sparkVersion,

  "org.apache.avro" % "avro" % "1.8.2",
  //"com.twitter" %% "bijection-avro" % "0.9.5",
  "com.sksamuel.avro4s" %% "avro4s-core" % "1.7.0",
  "io.confluent" % "kafka-avro-serializer" % "3.2.2"
)

libraryDependencies ++= providedDependencies //.map(_ % "provided")

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
  case PathList("org", "apache", "spark", "unused", "UnusedStubClass.class") => MergeStrategy.first
  case _ => MergeStrategy.first
}

resolvers ++= Seq(
  "Confluent Repo" at "http://packages.confluent.io/maven/"
)