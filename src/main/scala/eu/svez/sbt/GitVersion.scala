package eu.svez.sbt

import java.time.{LocalDateTime, ZoneOffset}

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Constants._
import org.eclipse.jgit.lib.{ObjectId, Repository}
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.storage.file.FileRepositoryBuilder

import scala.collection.JavaConversions._

case class GitVersion(buildDate: LocalDateTime,
                      gitBranch: String,
                      gitRepoIsClean: Boolean,
                      gitHeadCommit: String,
                      gitCommitAuthor: Option[String],
                      gitCommitDate: Option[LocalDateTime])

object GitVersion{

  def apply(): GitVersion = apply(new FileRepositoryBuilder().readEnvironment.findGitDir.build)

  def apply(repository: Repository): GitVersion = {
    val git = new Git(repository)

    val headId = repository.findRef(HEAD).getObjectId
    val headIdStr = ObjectId.toString(headId)
    val headCommit: Option[RevCommit] = git.log().add(headId).setMaxCount(1).call().toSeq.headOption
    val repoIsClean: Boolean = git.status.call.isClean
    val commitDateTime: Option[LocalDateTime] =
      headCommit.map(r => LocalDateTime.ofEpochSecond(r.getCommitTime.toLong, 0, ZoneOffset.UTC))
    val commitAuthorName: Option[String] = headCommit.map(_.getCommitterIdent.getName)

    GitVersion(LocalDateTime.now, repository.getBranch, repoIsClean, headIdStr, commitAuthorName, commitDateTime)
  }
}
