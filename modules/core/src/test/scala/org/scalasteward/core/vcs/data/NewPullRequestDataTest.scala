package org.scalasteward.core.vcs.data

import io.circe.syntax._
import org.scalasteward.core.git.{Branch, Sha1}
import org.scalasteward.core.model.Update
import org.scalasteward.core.nurture.UpdateData
import org.scalasteward.core.repoconfig.RepoConfig
import org.scalasteward.core.util.Nel
import org.scalatest.{FunSuite, Matchers}

class NewPullRequestDataTest extends FunSuite with Matchers {
  test("asJson") {
    val data = UpdateData(
      Repo("foo", "bar"),
      Repo("scala-steward", "bar"),
      RepoConfig(),
      Update.Single("ch.qos.logback", "logback-classic", "1.2.0", Nel.of("1.2.3")),
      Branch("master"),
      Sha1(Sha1.HexString("d6b6791d2ea11df1d156fe70979ab8c3a5ba3433")),
      Branch("update/logback-classic-1.2.3")
    )
    NewPullRequestData
      .from(data, "scala-steward:update/logback-classic-1.2.3")
      .asJson
      .spaces2 shouldBe
      """|{
         |  "title" : "Update logback-classic to 1.2.3",
         |  "body" : "Updates ch.qos.logback:logback-classic from 1.2.0 to 1.2.3.\n\nlabels: semver-patch",
         |  "head" : "scala-steward:update/logback-classic-1.2.3",
         |  "base" : "master"
         |}
         |""".stripMargin.trim
  }
}
