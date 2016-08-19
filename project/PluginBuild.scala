import sbt.Keys._
import sbt._
import bintray.Plugin._
import bintray.Keys._

object PluginBuild extends Build {

  lazy val commonSettings = Seq(
    version in ThisBuild := "0.0.3",
    organization in ThisBuild := "eu.svez"
  )

  val pluginName = "sbt-git-version-object"

  lazy val root = Project(pluginName, base = file("."))
    .settings(commonSettings ++ bintrayPublishSettings: _*)
    .settings(
      sbtPlugin := true,
      scalaVersion := "2.10.4",
      licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
      publishMavenStyle := false,
      repository in bintray := "sbt-plugins",
      bintrayOrganization in bintray := None,
      libraryDependencies ++= Seq(
        "org.eclipse.jgit" % "org.eclipse.jgit"       % "4.4.1.201607150455-r",
        "org.eclipse.jgit" % "org.eclipse.jgit.junit" % "4.4.1.201607150455-r",
        "junit"            % "junit"                  % "4.12"    % Test,
        "org.scalatest"    %% "scalatest"             % "2.2.4"   % Test
      )
    )
}
