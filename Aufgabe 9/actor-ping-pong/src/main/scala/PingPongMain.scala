import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.Props
import akka.actor.ActorRef

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
 * A little Akka actor demonstration.
 *
 * @author Christoph Knabe
 * @since 2014-12-04
 */
object PingPongMain extends App {
  
  val system = ActorSystem("PingPong")  
  val reflector: ActorRef = system.actorOf(Reflector.props, "Reflector")
  system.actorOf(Thrower.props(reflector), "Thrower")
  Await.ready(system.whenTerminated, Duration(1, TimeUnit.MINUTES))
  println("ActorSystem finally shut down.")
}
