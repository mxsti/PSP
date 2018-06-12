import akka.actor.{Actor, ActorLogging, Props}

object Forwarder {

    /**Returns a Props object for creation of a Reflector actor.*/
    def props: Props = Props(new Reflector)

    /**The message types an actor can understand are usually defined in his companion object as case classes.*/
    case class Ping(id: Int)

}

class Forwarder extends Actor with ActorLogging {

  import Forwarder._

  def receive = {
    case p: Ping =>
    println("Nachricht erhalten")
  }

}
