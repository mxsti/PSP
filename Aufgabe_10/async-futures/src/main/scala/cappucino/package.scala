import scala.io.StdIn

/**Common definitions for both main objects.
 * Follows Tutorial of Daniel Westheide "The Neophyte's Guide to Scala Part 8: Welcome to the Future"
 * http://danielwestheide.com/blog/2013/01/09/the-neophytes-guide-to-scala-part-8-welcome-to-the-future.html
 * Modifications: Without Exceptions.
 *
 * @author Christoph Knabe
 * @since 2014-12-18*/
package object cappucino {

  // Some type aliases, just for getting more meaningful method signatures:
  type CoffeeBeans = String
  /**Gemahlener Kaffee*/
  type GroundCoffee = String
  case class Water(temperature: Int)
  type Milk = String
  type FrothedMilk = String
  type Espresso = String
  type Cappuccino = String

  val startMillis = System.currentTimeMillis

  /**Sleeps some time below 5 seconds and then returns the result of the computation of {expression}. */
  def slowly[T](millis: Long, actionName: String)(expression: =>T): T = {
    def time = System.currentTimeMillis - startMillis + " ms"
    println(s"$time: Start $actionName...")
    import scala.concurrent.blocking
    //blocking{ //Markiert diesen Codeteil als potentiell zeitverbrauchend. Erlaubt Thread-Allokation bei Bedarf.
      //Thread sleep millis //Random.nextInt(10000)
    //}
    scala.io.StdIn.readLine(s"$time: Enter to Finish $actionName\n")
    val result = expression
    println(s"$time: Finished $actionName gave $result\n")
    result
  }

}
