# sbt-git-version-object

[![Build Status](https://travis-ci.org/svezfaz/sbt-git-version-object.svg?branch=master)](https://travis-ci.org/svezfaz/sbt-git-version-object)  [ ![Download](https://api.bintray.com/packages/svezfaz/sbt-plugins/sbt-git-version-object/images/download.svg) ](https://bintray.com/svezfaz/sbt-plugins/sbt-git-version-object/_latestVersion)

This SBT plugin allows to automatically generate two files containing information about your GIT repository at build time.

1. a ``Version.scala`` object
2. a ``version.conf`` properties file

## Setup
1. In your ``project/plugins.sbt`` add ``addSbtPlugin("eu.svez" % "sbt-git-version-object" % "$PLUGIN_VERSION")``. You can find the latest version at the top of this document.
2. In your ``build.sbt`` (or full build file) add
   * ``import eu.svez.sbt.GitVersionObjectPlugin._``
   * the following settings to your project: ``Seq( gitVersionSettings(pkg = "com.example.yourproject"): _* )`` . The `pkg` parameter can be used to specify what package you want the `Version.scala` file to be generated

## Usage
You can now refer to the `Version` object directly, e.g.

``println("GIT revision: " + com.example.yourproject.Version.gitHeadCommit)``

The same applies to the `version.conf` file.

You can find a full example of the generated files below:

**Version.scala**

```
package com.example.yourproject
object Version extends scala.AnyRef {
  val buildDate : scala.Predef.String = { /* compiled code */ }
  val gitBranch : scala.Predef.String = { /* compiled code */ }
  val gitRepoIsClean : scala.Predef.String = { /* compiled code */ }
  val gitHeadCommit : scala.Predef.String = { /* compiled code */ }
  val gitCommitAuthor : scala.Predef.String = { /* compiled code */ }
  val gitCommitDate : scala.Predef.String = { /* compiled code */ }
}
```

**version.conf**
```
build.date = "2016-08-18T15:08:38"
git.branch = "master"
git.repo-is-clean = "false"
git.head.commit = "90d2b932a40417c653853da0bd589e87c8b54f02"
git.head.commit.author = "John Doe"
git.head.commit.date = "2016-08-18T13:41:45"
```

## License
This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
