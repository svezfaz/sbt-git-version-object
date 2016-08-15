package eu.svez.sbt

import java.time.LocalDateTime

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.junit.RepositoryTestCase
import org.scalatest._

class GitVersionSpec extends RepositoryTestCase with FlatSpecLike with Matchers with BeforeAndAfter {

  before {
    setUp()
  }

  "GitVersion" should "extract info from a given Git repo" in {
    val git = new Git(db)

    val before = LocalDateTime.now
    val commit = git.commit().setCommitter("John Doe", "john.doe.gmail.com").setMessage("message").call
    val gitVersion = GitVersion(db)
    val after = LocalDateTime.now

    gitVersion.buildDate.compareTo(after) should be <= 0
    gitVersion.buildDate.compareTo(before) should be >= 0
    gitVersion.gitBranch shouldBe "master"
    gitVersion.gitHeadCommit shouldBe commit.getName
    gitVersion.gitRepoIsClean shouldBe true
    gitVersion.gitCommitAuthor shouldBe Some("John Doe")
    gitVersion.gitCommitDate shouldBe defined
  }
}
