import akka.actor.{ActorSystem}

object ActorTest {

  val system = ActorSystem("TestSystem")

  def main(args: Array[String]): Unit = {
    var count = 0
    while (true) {
      count += 1
      system.actorOf(Ü8Actor.props)
      if (count%10000 == 0) println ("Actor " + count + " started")
    }
  }
}
