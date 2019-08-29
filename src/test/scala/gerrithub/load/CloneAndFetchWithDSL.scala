package load

import com.github.barbasa.gatling.git.protocol.GitProtocol
import com.github.barbasa.gatling.git.request.builder.{Git}
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import com.github.barbasa.gatling.git.{GatlingGitConfiguration}

import scala.concurrent.duration._

class CloneAndFetchWithDSL extends Simulation {
  val gitProtocol = GitProtocol()
  implicit val conf = GatlingGitConfiguration()
  val clonesAndFetchesScenario: ScenarioBuilder =
    scenario("Clone and Fetch constant users")
      .forever {
        exec(
          Git.clone(
            "ssh://barbasa@review.gerrithub.io:29418/barbasa/test-gatling"
          )
        ).exec(
          Git.fetch(
            "ssh://barbasa@review.gerrithub.io:29418/barbasa/test-gatling"
          )
        )
      }
  setUp(clonesAndFetchesScenario.inject(atOnceUsers(1)))
    .protocols(gitProtocol)
    .maxDuration(10 seconds)
}
