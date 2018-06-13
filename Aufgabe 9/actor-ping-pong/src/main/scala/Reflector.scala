import akka.actor.{Actor, ActorLogging, ActorRef, Props}

object Reflector {

  /**Returns a Props object for creation of a Reflector actor.*/
  def props: Props = Props(new Reflector)

  /**The message types an actor can understand are usually defined in his companion object as case classes.*/
  case class Pang(id: Int)

}

/**An Actor which will respond to each Ping message by a Pong message.*/
class Reflector extends Actor with ActorLogging {

  import Reflector._
  private var count = 0
  val receiver: ActorRef = context.actorOf(Props[Thrower])
  
  def receive: Receive = {
    case p: Pang => {
      count += 1 
      log.debug(s"Received $p as #$count.")
      receiver ! Thrower.Pong(p.id)
    }
  }
  
}
