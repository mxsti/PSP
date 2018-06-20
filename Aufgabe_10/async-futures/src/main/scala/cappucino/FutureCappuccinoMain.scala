package cappucino

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.Random
import scala.concurrent.Await

//scala.concurrent.Future[T] kapselt eine Berechnung, die eventuell in der Zukunft fertig wird.

//Future ist ein einmal schreibbarer Behälter.
//Typ Future stellt nur Lesemethoden zur Verfügung.
//Man schreibt in die Future mit einem Promise. Behandeln wir später.

/** Introduction of Futures from
  * http://danielwestheide.com/blog/2013/01/09/the-neophytes-guide-to-scala-part-8-welcome-to-the-future.html */

object FutureCappuccinoMain extends /*A Scala application */ App {

  /** Man benötigt einen ExecutionContext implizit verfügbar,
    * in dem die Future-Aktionen ausgeführt werden.
    * Der implizite ExecutionContext global basiert auf einem Thread-sparenden ForkJoinPool.
    * http://blog.jessitron.com/2014/02/scala-global-executioncontext-makes.html
    */

  import scala.concurrent.ExecutionContext.Implicits.global

  /**Mahlen der Kaffeebohnen*/
  def grind(beans: CoffeeBeans) = Future[GroundCoffee] {
    SequentialCappuccinoMain.grind("arabica")
  }

  def heatWater(water: Water) = Future[Water] {
    SequentialCappuccinoMain.heatWater(water)
  }

  def frothMilk(milk: Milk) = Future[FrothedMilk] {
    SequentialCappuccinoMain.frothMilk(milk)
  }

  def brew(coffee: GroundCoffee, heatedWater: Water): Future[Espresso] = Future {
    SequentialCappuccinoMain.brew(coffee, heatedWater)
  }

  def combine(espresso: Espresso, frothedMilk: FrothedMilk) = Future{
    SequentialCappuccinoMain.combine(espresso, frothedMilk)
  }

  def prepareCappuccino(): Future[Cappuccino] = {
    //Starte 3 asynchrone Berechnungen, die sofort eine Future liefern:
    val groundCoffeeFuture: Future[GroundCoffee] = grind("arabica")
    val heatedWaterFuture: Future[Water] = heatWater(Water(20))
    val frothedMilkFuture: Future[FrothedMilk] = frothMilk("milk")
    val result = for {
      ground <- groundCoffeeFuture
      water <- heatedWaterFuture
      foam <- frothedMilkFuture
      espresso <- brew(ground, water)
    } yield SequentialCappuccinoMain.combine(espresso, foam)
    result
  }

  val result = Await.result(prepareCappuccino(), 200.seconds)
  println(s"Whole process produced $result")

}
