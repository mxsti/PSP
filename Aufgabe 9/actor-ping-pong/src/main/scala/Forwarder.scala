import akka.actor.{Actor, ActorLogging, Props, ActorRef}

object Forwarder {

    /**Returns a Props object for creation of a Reflector actor.*/
    def props: Props = Props(new Forwarder)

    /**The message types an actor can understand are usually defined in his companion object as case classes.*/
    case class Ping(id: Int)

}

class Forwarder extends Actor with ActorLogging {

  import Forwarder._
  private var count = 0
  val receiver: ActorRef = context.actorOf(Props[Reflector])

  def receive: Receive = {
    case p: Ping =>
      count += 1
      log.debug(s"Received $p as #$count.")
      receiver ! Reflector.Pang(p.id)
  }

}
