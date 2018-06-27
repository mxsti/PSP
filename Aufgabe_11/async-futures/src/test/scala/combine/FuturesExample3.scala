package combine

import scala.concurrent.Future
//We need an ExecutionContext in scope to do parallel executions, e.g. by Futures! 
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scala.util.Random
 
/**
 * A Scala 'Future' example from the Scala Cookbook.
 * @see <a href="http://shop.oreilly.com/product/0636920026914.do" title="http://shop.oreilly.com/product/0636920026914.do">http://shop.oreilly.com/product/0636920026914.do</a>
 * @see http://alvinalexander.com/scala/future-example-scala-cookbook-oncomplete-callback
 */
object FuturesExample3 extends App {
 
  // not too exciting, the result will always be 42. but more importantly, when?
  println("1 - starting calculation ...")
  val f = Future[Int] {
    sleep(Random.nextInt(2000))
    42
  }
 
  println("2- before onComplete")
  /*
  f.onComplete {
    case Success(value) => println(s"Got the callback, meaning = $value")
    case Failure(e) => e.printStackTrace
  }
  */
  for(value:Int <- f){
    println(s"Future completed and unpacked, it returned = $value")
  }
 
  // do the rest of your work
  println("A ..."); sleep(100)
  println("B ..."); sleep(100)
  println("C ..."); sleep(100)
  println("D ..."); sleep(100)
  println("E ..."); sleep(100)
  println("F ..."); sleep(100)
   
  // keep the jvm alive (may be needed depending on how you run the example)
  //sleep(2000)
  
  /**Lets the current Thread sleep {duration} milliseconds. This method blocks the current Thread! Do not use it for massive parallel applications. */
  def sleep(duration: Long) { Thread.sleep(duration) }
 
}
