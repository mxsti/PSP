import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
 * A little Akka actor demonstration.
 *
 * @author Christoph Knabe
 * @since 2014-12-04
 */
object PingPongMain extends App {
  
  val system = ActorSystem("PingPangPong")
  system.actorOf(Thrower.props, "Thrower")
  system.actorOf(Forwarder.props, "Forwarder")
  system.actorOf(Reflector.props, "Reflector")
  Await.ready(system.whenTerminated, Duration(1, TimeUnit.MINUTES))
  println("ActorSystem finally shut down.")

}
