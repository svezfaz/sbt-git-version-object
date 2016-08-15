import sbt.Keys._
import sbt._

object PluginBuild extends Build {

  val pluginName = "sbt-git-version-object"

  lazy val root = Project(pluginName, base = file("."))
    .settings(
      sbtPlugin := true,
      version := "0.2-SNAPSHOT",
      organization := "eu.svez",
      scalaVersion := "2.10.4",
      libraryDependencies ++= Seq(
        "org.eclipse.jgit" % "org.eclipse.jgit"       % "4.4.1.201607150455-r",
        "org.eclipse.jgit" % "org.eclipse.jgit.junit" % "4.4.1.201607150455-r",
        "junit"            % "junit"                  % "4.12"    % "test",
        "org.scalatest"    %% "scalatest"             % "2.2.4"   % "test"
      )
    )
}
