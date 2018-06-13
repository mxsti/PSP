import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorRef
import java.util.concurrent.TimeUnit

object Thrower {

  /** Returns a Props object for creation of a parameterized Thrower actor. */
  def props: Props = Props(new Thrower)

  /*The message types an actor can understand, are usually defined in his companion object as case classes.*/
  case class Pong(id: Int)

  case object Shutdown

}

/** An Actor which will send quantity Ping messages to the reflector awaiting corresponding Pong messages. */
class Thrower extends Actor with ActorLogging {

  import Thrower._

  {
    import scala.concurrent.duration._
    context.system.scheduler.scheduleOnce(4.millis, self, Shutdown)(context.dispatcher, self)
  }
  val receiver: ActorRef = context.actorOf(Props[Forwarder])
  for (i <- 1 to 100) {
    receiver ! Forwarder.Ping(i)
  }

  def receive: Receive = {
    case p: Pong => log.info(s"Received $p")
    case Shutdown => {
      log.info("Received Shutdown Command")
      context.system.terminate()
    }
  }

}
