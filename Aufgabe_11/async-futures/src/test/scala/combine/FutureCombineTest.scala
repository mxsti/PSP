package combine

import org.scalatest._
import akka.actor.ActorSystem

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.Failure

/**Asynchronous Test Specification for class cappucino.FutureCombineImpl used as trait FutureCombine.
  * @author Christoph Knabe
  * @since 2018-06-04
  * See [[http://www.scalatest.org/user_guide/async_testing ScalaTest - Asynchronous testing]]
  */
class FutureCombineTest extends AsyncFlatSpec with Matchers {


  private val testee: FutureCombine = new FutureCombineImpl
  private val system = ActorSystem("FutureDelay")

  private def sayHello(millis: Long) = actionFuture("Say HELLO", millis) {
    "Hello World"
  }

  private def askWhere(millis: Long) = actionFuture("Ask where", millis)("Berlin")

  private def askHowdy(millis: Long) = actionFuture("Howdy", millis)("Great!")

  private def greetNowhere(millis: Long) = actionFuture("Greet to Nowhere", millis) {
    throw new AddresseeUnknown
  }


  /**Example synchronous computation*/
  private def addNow(addends: Int*): Int = addends.sum

  // A synchronous test. The body must have result type Assertion:
  "addNow" should "immediately compute a sum of passed Ints" in {
    val sum: Int = addNow(1, 2)
    assert(sum == 3)
  }

  //An asynchronous test. The body must have result type Future[Assertion].
  //When the Future completes, ScalaTest will be informed about its result.
  "actionFuture" should "return argument after some time" in {
    val helloFut: Future[String] = actionFuture("Say Hello", 1000) {
      "HELLO"
    }
    helloFut map {
      _ shouldBe "HELLO"
    }
  }

  "firstOf" should "return result of first completed Future 1" in {
    val resultFut = testee.firstOf(Seq(sayHello(0), askWhere(1000), askHowdy(2000)))
    resultFut map {
      _ shouldBe "Hello World"
    }
  }

  it should "return result of first completed Future 2" in {
    val resultFut = testee.firstOf(Seq(sayHello(3000), askWhere(1000), askHowdy(2000)))
    resultFut map {
      _ shouldBe "Berlin"
    }
  }

  it should "return result of first completed Future 3" in {
    val resultFut = testee.firstOf(Seq(sayHello(3000), askWhere(4000), askHowdy(2000)))
    resultFut map {
      _ shouldBe "Great!"
    }
  }

  private class AddresseeUnknown extends Exception

  /** Waits up to 3 seconds for the completion of {future} and asserts that it has failed with an exception of the given {exceptionClass}. */
  private def assertException(exceptionClass: Class[_])(future: Future[_]): Future[Assertion] = {
    //Await.ready(future, 3.seconds)
    future map {
      _ match {
        case Some(Failure(x)) => withClue(s"Unexpected exception $x instead of $exceptionClass: ") {
          exceptionClass.isAssignableFrom(x.getClass) shouldBe true
        }
        case x => fail(x.toString)
      }
    }
  }

  it should "return failure of first completed Future 1" in {
    val act1 = actionFuture("Say HELLO", 10) {
      throw new AddresseeUnknown
    }
    val act2 = askWhere(1000)
    val act3 = askHowdy(2000)
    val resultFut = testee.firstOf(Seq(act1, act2, act3))
    recoverToSucceededIf[AddresseeUnknown] { // Result type: Future[Assertion]
      resultFut
    }
  }

  it should "return failure of first completed Future 2" in {
    val act1 = sayHello(1000)
    val act2 = actionFuture("Ask Where", 10) {
      throw new IllegalArgumentException
    }
    val act3 = askHowdy(2000)
    val resultFut = testee.firstOf(Seq(act1, act2, act3))
    recoverToSucceededIf[IllegalArgumentException] {
      resultFut
    }
  }

  it should "return failure of first completed Future 3" in {
    val act1 = sayHello(1000)
    val act2 = askWhere(2000)
    val act3 = actionFuture("Greet Nowhere", 10) {
      throw new AddresseeUnknown
    }
    val resultFut = testee.firstOf(Seq(act1, act2, act3))
    recoverToSucceededIf[AddresseeUnknown] {
      resultFut
    }
  }

  "allOf" should "collect all results if all succeed" in {
    val act1 = actionFuture("Say HELLO", 500) {
      "Hello World,"
    }
    val act2 = actionFuture("What about", 1000)("Berlin")
    val act3 = actionFuture("How is", 2000)("is great!")
    val resultFut = testee.allOf(Seq(sayHello(0), askWhere(1000), askHowdy(2000)))
    resultFut map {
      _ shouldBe Seq("Hello World", "Berlin", "Great!")
    }
  }

  it should "fail if 1st of Futures fails" in {
    val act1 = actionFuture("Say HELLO", 555) {
      throw new AddresseeUnknown
    }
    val act2 = askWhere(1000)
    val act3 = askHowdy(2000)
    val resultFut = testee.allOf(Seq(act1, act2, act3))
    recoverToSucceededIf[AddresseeUnknown] { // Result type: Future[Assertion]
      resultFut
    }
  }

  it should "fail if 2nd of Futures fails" in {
    val act1 = sayHello(10)
    val act2 = actionFuture("Ask where", 1000) {
      throw new IllegalAccessException("test")
    }
    val act3 = askHowdy(2000)
    val resultFut = testee.allOf(Seq(act1, act2, act3))
    recoverToSucceededIf[IllegalAccessException] { // Result type: Future[Assertion]
      resultFut
    }
  }

  it should "fail if 3rd of Futures fails" in {
    val act1 = sayHello(10)
    val act2 = askWhere(1000)
    val act3 = actionFuture("Ask Howdy", 2000) {
      throw new IllegalStateException("test")
    }
    val resultFut = testee.allOf(Seq(act1, act2, act3))
    recoverToSucceededIf[IllegalStateException] { // Result type: Future[Assertion]
      resultFut
    }
  }

  /** This method can be used as a placeholder for code which should return something
    * which should be matched.
    * For example you could write ' XXX shouldBe "Nirvana" ' if you want to compile the test
    * and replace XXX later by an expression, which returns "Nirvana".
    *
    * @throws `NotImplementedError` always */
  private def XXX = convertToAnyShouldWrapper {
    throw new NotImplementedError("Code-to-test still missing. See 2nd line of stack trace.")
  }


  /** Returns a Future, which will after {millis} milliseconds compute {expression} and contain its result. */
  //See https://doc.akka.io/docs/akka/2.5/futures.html#after
  private def actionFuture[T](actionName: String, millis: Long)(expression: => T): Future[T] = {
    //println(s"Start $actionName ...")
    akka.pattern.after(millis.millis, using = system.scheduler) {
      Future {
        val result = expression
        //println(s"""Completing "$actionName" gave "$result"""")
        result
      }
    }
  }


}
