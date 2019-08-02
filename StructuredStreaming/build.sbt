import AssemblyPlugin._ // put this at the top of the file,leave the next line blank

assemblySettings

name := "StructuredStreaming"

version := "0.1"

scalaVersion := "2.12.8"


// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.3"


// https://mvnrepository.com/artifact/org.apache.spark/spark-sql
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.3"

// https://mvnrepository.com/artifact/org.apache.spark/spark-streaming
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.4.3"

// https://mvnrepository.com/artifact/org.apache.hadoop/hadoop-aws
libraryDependencies += "org.apache.hadoop" % "hadoop-aws" % "3.1.2"

// https://mvnrepository.com/artifact/org.apache.hadoop/hadoop-common
libraryDependencies += "org.apache.hadoop" % "hadoop-common" % "3.1.2"
//
//// https://mvnrepository.com/artifact/org.apache.hadoop/hadoop-mapreduce-client-core
libraryDependencies += "org.apache.hadoop" % "hadoop-mapreduce-client-core" % "3.1.2"

// https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.7"

// https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % "2.8.7"

// https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-scala
dependencyOverrides += "com.fasterxml.jackson.module" % "jackson-module-scala_2.12" % "2.8.7"

// https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-scala
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.9"


// https://mvnrepository.com/artifact/commons-io/commons-io
libraryDependencies += "commons-io" % "commons-io" % "2.6"

// https://mvnrepository.com/artifact/org.apache.commons/commons-exec
libraryDependencies += "org.apache.commons" % "commons-exec" % "1.3"

libraryDependencies += "org.apache.logging.log4j" % "log4j-api" % "2.12.0"

libraryDependencies += "org.apache.logging.log4j" % "log4j-core" % "2.12.0"
// dependancy for vegas===============
//libraryDependencies += "org.vegas-viz" %% "vegas" % "0.3.11"

// https://mvnrepository.com/artifact/org.vegas-viz/vegas-spark
//libraryDependencies += "org.vegas-viz" %% "vegas-spark" % "0.3.11"

resolvers += Resolver.url("bintray-sbt-plugins", url("http://dl.bintray.com/sbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)

resolvers += Resolver.bintrayIvyRepo("com.eed3si9n", "sbt-plugins")

lazy val commonSettings = Seq(
  organization := "com.bridgelbz",
  version := "0.1.0-SNAPSHOT"
)

// set the main class for packaging the main jar
mainClass in (Compile, packageBin) := Some("com.bridgelbz.TwitterStreamingData")

// set the main class for the main 'sbt run' task
mainClass in (Compile, run) := Some("com.bridgelbz.TwitterStreamingData")


resolvers in Global ++= Seq(
  "Sbt plugins" at "https://dl.bintray.com/sbt/sbt-plugin-releases",
  "Maven Central Server" at "http://repo1.maven.org/maven2",
  "TypeSafe Repository Releases" at "http://repo.typesafe.com/typesafe/releases/",
  "TypeSafe Repository Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/"
)

lazy val root = (project in file(".")).
  settings(
    name := "Streaming",
    version := "0.1",
    scalaVersion := "2.12.8",
    Compile / mainClass := Some("com.bridgelbz.TwitterStreamingData")
  )
  .settings(commonSettings: _*)
  .enablePlugins(AssemblyPlugin)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}