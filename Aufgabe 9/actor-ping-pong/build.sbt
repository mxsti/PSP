name := "actor-ping-pong"

scalaVersion := "2.12.4"

lazy val akkaVersion = "2.5.12"

scalacOptions ++= Seq("-encoding", "UTF-8", "-unchecked", "-deprecation", "-feature")

libraryDependencies ++= Seq(
   //vendor %% scalaVersionDependentArtifact % ownVersion % scope
  "org.scalatest" %% "scalatest" % "3.0.4",
  // Akka
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion
)
/*The operator %% builds an artifact name from the specified scalaVersionDependentArtifact name, 
* an underscore sign, and the upper mentioned scalaVersion. 
* So the artifact name will result here in scalatest_2.11,
* as the last number in a Scala version is not API relevant.
*/

/*Das von SBT für Java vorgesehene Qelltextverzeichnis wird umkonfiguriert, um für Lösungen der Übungsaufgaben in Scala verwendet zu werden.*/
javaSource in Compile := baseDirectory.value / "_private"

//See http://www.scalatest.org/user_guide/using_scalatest_with_sbt
logBuffered in Test := false


//Tell the SBT Eclipse plugin to download all sources along with binary .jar files and make them available for source code navigation:
EclipseKeys.withSource := true
