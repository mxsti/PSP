import akka.actor.{Actor, ActorLogging, Props, ActorRef}

object Forwarder {

    /**Returns a Props object for creation of a Reflector actor.*/
    def props(reflector: ActorRef): Props = Props(new Forwarder(reflector))

    /**The message types an actor can understand are usually defined in his companion object as case classes.*/
    case class Ping(id: Int)

}

class Forwarder(reflector: ActorRef) extends Actor with ActorLogging {

  import Forwarder._
  private var count = 0

  def receive: Receive = {
    case p: Ping =>
      count += 1
      println(s"Received $p as #$count.")
      reflector ! Reflector.Pang(p.id)
      reflector ! Reflector.Ref(sender())
  }

}
