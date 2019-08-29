package load

import com.github.barbasa.gatling.git.protocol.GitProtocol
import com.github.barbasa.gatling.git.request.builder.GitRequestBuilder
import io.gatling.core.Predef.{Simulation, _}
import io.gatling.core.structure.ScenarioBuilder

import com.github.barbasa.gatling.git.{
  GatlingGitConfiguration,
  GitRequestSession
}
import scala.concurrent.duration._

class CloneFromFeeder extends Simulation {
  val gitProtocol = GitProtocol()
  implicit val conf = GatlingGitConfiguration()

  val feeder = jsonFile("data/requests.json").circular

  val replayCallsScenario: ScenarioBuilder =
    scenario("Git commands constant users")
      .forever() {
        feed(feeder)
          .exec(new GitRequestBuilder(GitRequestSession("${cmd}", "${url}")))
      }

  setUp(replayCallsScenario.inject(atOnceUsers(5)))
    .protocols(gitProtocol)
    .maxDuration(1 minute)

}
