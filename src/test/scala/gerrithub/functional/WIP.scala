package functional

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class WIP extends Simulation {

  val baseUrl =
    System.getProperty("baseUrl", "https://review.gerrithub.io")
  val username = System.getProperty("username", "theUserName")
  val password =
    System.getProperty("password", "thePassword")

  val httpProtocol = http
    .baseUrl(baseUrl)
    .userAgentHeader("Gatling-test/functional-wip")

  val request_headers = Map("Content-Type" -> "application/json")

  val scn = scenario("WIP")
    .exec(
      http("Create WIP change")
        .post("/a/changes/")
        .headers(request_headers)
        .basicAuth(username, password)
        .body(StringBody("""{
                  "project" : "barbasa/test-gatling",
                  "subject" : "Let's test this Gerrit!",
                  "branch" : "master",
                  "work_in_progress": "true",
                  "status" : "NEW"
                  }"""))
        .check(status.is(201))
        .check(substring("\"status\": \"NEW\"").exists)
        .check(substring("\"work_in_progress\": true").exists)
        .check(substring("\"has_review_started\": false").exists)
        .check(regex("\"id\": \"(.*?)\"").saveAs("changeId"))
    )
    .exec(
      http("Set Ready for review")
        .post("/a/changes/${changeId}/ready")
        .basicAuth(username, password)
        .check(status.is(200))
    )
    .exec(
      http("Check change properties")
        .get("/a/changes/${changeId}")
        .headers(request_headers)
        .basicAuth(username, password)
        .check(status.is(200))
        .check(substring("\"work_in_progress\":").notExists)
        .check(substring("\"has_review_started\": true").exists)
    )

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}
