import akka.actor.ActorSystem

object PingPangPongMain {

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("PingPangPong")
    val reflector = system.actorOf(Reflector.props,"Reflector")
    val forwarder = system.actorOf(Forwarder.props(reflector), "Forwarder")
    system.actorOf(Thrower.props(forwarder), "Thrower")
  }

}
