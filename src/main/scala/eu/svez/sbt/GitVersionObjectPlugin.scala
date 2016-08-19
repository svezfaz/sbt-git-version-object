package eu.svez.sbt

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit.SECONDS

import sbt.Keys._
import sbt._

object GitVersionObjectPlugin extends Plugin {

  def gitVersionSettings(pkg: String, filename: String): Seq[Setting[_]] = inConfig(Compile)(Seq(
    resourceGenerators <+= generateVersion(resourceManaged, _ / s"$filename.conf",
      """|build.date = "%s"
        |git.branch = "%s"
        |git.repo-is-clean = "%s"
        |git.head.commit = "%s"
        |git.head.commit.author = "%s"
        |git.head.commit.date = "%s"
        | """),
    sourceGenerators <+= generateVersion(sourceManaged, _ / pkg / s"$filename.scala",
      s"""|package $pkg
        |
        |object $filename {
        |  val buildDate: String = "%s"
        |  val gitBranch: String = "%s"
        |  val gitRepoIsClean: String = "%s"
        |  val gitHeadCommit: String = "%s"
        |  val gitCommitAuthor: String = "%s"
        |  val gitCommitDate: String = "%s"
        |}
        | """)
  ))

  private def generateVersion(dir: SettingKey[File], locate: File => File, template: String) = Def.task[Seq[File]] {
    val gitVersion = GitVersion()
    val file = locate(dir.value)
    val content = template.stripMargin.format(
      formatDate(gitVersion.buildDate),
      gitVersion.gitBranch,
      gitVersion.gitRepoIsClean.toString,
      gitVersion.gitHeadCommit,
      gitVersion.gitCommitAuthor.getOrElse(""),
      gitVersion.gitCommitDate.map(formatDate).getOrElse(""))
    if (!file.exists || IO.read(file) != content) IO.write(file, content)
    Seq(file)
  }

  private def formatDate(date: LocalDateTime) = date.truncatedTo(SECONDS).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

}
