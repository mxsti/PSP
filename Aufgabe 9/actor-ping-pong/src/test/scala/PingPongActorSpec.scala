import akka.actor.ActorSystem
import akka.testkit.{ TestKit, ImplicitSender }
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfterAll
 
class PingPongActorSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {
 
  def this() = this(ActorSystem("PingPongActorSpec"))
 
  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "A Reflector actor" must {
    "send back a Pong with the same id on a Ping" in {
      val reflectorActor = system.actorOf(Reflector.props)
      reflectorActor ! Reflector.Pang(99)
      expectMsg(Thrower.Pong(99))
    }
  }

  "A Thrower actor" must {
    "send 100 indexed Ping messages to passed actor" in {
      val throwerActor = system.actorOf(Thrower.props)
      //throwerActor ! PongActor.PongMessage("pong")
      for(i <- 1 to 100){
        expectMsg(Reflector.Pang(i))
      }
    }
  }

}
