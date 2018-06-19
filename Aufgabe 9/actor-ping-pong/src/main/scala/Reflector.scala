import akka.actor.{Actor, ActorLogging, ActorRef, Props}

object Reflector {

  /**Returns a Props object for creation of a Reflector actor.*/
  def props: Props = Props(new Reflector)

  /**The message types an actor can understand are usually defined in his companion object as case classes.*/
  case class Pang(id: Int)
  case class Ref(referenz: ActorRef)

}

/**An Actor which will respond to each Ping message by a Pong message.*/
class Reflector extends Actor with ActorLogging {

  import Reflector._
  private var count = 0
  
  def receive: Receive = {
    case Ref(referenz) => {
      referenz ! Thrower.Pong(count)
    }
    case p: Pang => {
      count+=1
      println(s"Received $p as #$count.")
    }
  }
  
}
