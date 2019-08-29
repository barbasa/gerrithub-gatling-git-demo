package functional

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class Login extends Simulation {

  val baseUrl =
    System.getProperty("baseUrl", "https://review.gerrithub.io")
  val username = System.getProperty("username", "theUsername")
  val password =
    System.getProperty("password", "thePassword")

  val httpProtocol = http
    .baseUrl(baseUrl)
    .userAgentHeader("Gatling-test/functional-login")

  val request_headers = Map(
    "Content-Type" -> "application/json",
    "Upgrade-Insecure-Requests" -> "1"
  )

  val scn = scenario("Login")
    .exec(
      http("Successful Login")
        .get(s"/accounts/barbasa/detail")
        .headers(request_headers)
        .basicAuth(username, password)
        .check(status.is(200))
    )

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}
