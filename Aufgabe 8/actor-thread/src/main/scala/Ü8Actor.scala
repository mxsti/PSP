import akka.actor.{Actor, ActorLogging, Props}

object Ü8Actor{
  def props: Props = Props(new Ü8Actor)
}

class Ü8Actor extends Actor with ActorLogging {
  override def receive = Actor.emptyBehavior
}
