package combine

import org.scalatest.{AsyncFlatSpec, Matchers}
import scala.concurrent.duration._
import scala.util.Success
import scala.util.Failure
import scala.util.Try
import scala.concurrent.Await
import concurrent.Future
import concurrent.Promise

/**Lehrmaterial für Futures und Promises. 
 * Beispiele aus http://danielwestheide.com/blog/2013/01/16/the-neophytes-guide-to-scala-part-9-promises-and-futures-in-practice.html
 * Enthält alle nötigen Mittel zur Implementierung der Übungsaufgabe FutureCombine.
 * */
class FuturePromiseTest extends AsyncFlatSpec with Matchers {

    
    case class TaxCut(reduction: Int)

  // A synchronous test. The body must have result type Assertion:
  "1) Future.apply" should "be implemented by a Promise" in {
    val result: Future[String] = Future { "Hello world!" }
    result.getClass.getName shouldBe "scala.concurrent.impl.Promise$DefaultPromise"
    //DefaultPromise implementiert Future und Promise.
    // => Eine Future kann nur durch ihre Promise fertig werden.
    // Eine Future ist eine Nur-Lese-Ansicht!
  }
  
  "a Promise" should "promise a rosy future" in {
    // either give the type as generic parameter to the factory method:
    val taxcut = Promise[TaxCut]()
    // or give the compiler a hint by specifying the target type of the Promise:
    val taxcut2: Promise[TaxCut] = Promise()
    taxcut shouldBe a [Promise[_]]
    //???Wird das bei AsyncTest geprüft?
    taxcut.getClass.getName shouldBe "scala.concurrent.impl.Promise$DefaultPromise"
    //Getting the Future for a Promise:
    val taxcutF: Future[TaxCut] = taxcut.future
    //Ein neuer Aufruf wird dasselbe Objekt liefern:
    taxcut.future should be theSameInstanceAs taxcutF    
  }
  
  "a Promise" should "03 be successfully completable" in {
    // Ein Promise ist eine Einmal-Schreib-Ansicht und kann erfolgreich oder fehlerhaft fertig werden.
    val taxcut = Promise[TaxCut]()
    // Erfolgreiche Beendigung einer Promise: 
    taxcut.success(TaxCut(20))
    //Nochmal geht nicht:
    an [IllegalStateException] should be thrownBy taxcut.success(TaxCut(20))
    //Jetzt ist auch die zugehörige Future beendet:
    taxcut.future.isCompleted shouldBe true
    //An das Ergebnis kommt man z.B. mit dem blockierenden Await.result und einem Timeout:
    Await.result(taxcut.future, 0.seconds) shouldBe TaxCut(20)
  }
  
  "a Promise" should "be completable asynchronously"in {
    /**Wahlversprechen einlösen.*/
    def redeemCampaignPledge(): Future[TaxCut] = {
      val p = Promise[TaxCut]
      //Ein Promise wird normalerweise asynchron, d.h. in anderem Thread oder Future eingelöst:
      new Thread(new Runnable{
        def run(){
          println("Starting the new legislative period.")
          Thread.sleep(2000)
          p.success(TaxCut(20))
          println("We reduced the taxes! You must reelect us!!!!")          
        }
      }).start()
      p.future
    }
    //Wahlversprechen zur Kenntnis nehmen:
    val taxCutF: Future[TaxCut] = redeemCampaignPledge()
    println("Now that they're elected, let's see if they remember their promises...")
    //Sag mir, wenn die Zukunft da ist:
    taxCutF.onComplete {
      case Success(TaxCut(reduction)) =>
        println(s"A miracle! They really cut our taxes by $reduction percentage points!")
      case Failure(ex) =>
        println(s"They broke their promises! Again! Because of a ${ex.getMessage}")
    }
    true shouldBe true
  }
  
  "a Promise" should "be failable asynchronously" in {
    case class LameExcuse(msg: String) extends Exception(msg)
    
    /**Wahlversprechen einlösen.*/
    def redeemCampaignPledge(): Future[TaxCut] = {
      val p = Promise[TaxCut]
      //Ein Promise wird normalerweise asynchron, d.h. in anderen Thread eingelöst:
      new Thread(new Runnable{
        def run(){
          println("Starting the new legislative period.")
          Thread.sleep(2000)
          p.failure(LameExcuse("global economy crisis"))
          println("We didn't fulfill our promises, but surely they'll understand.")
        }
      }).start()
      p.future
    }
    //Wahlversprechen zur Kenntnis nehmen:
    val taxCutF: Future[TaxCut] = redeemCampaignPledge()
    println("Now that they're elected, let's see if they remember their promises...")
    //Sag mir, wenn die Zukunft da ist:
    taxCutF.onComplete {
      case Success(TaxCut(reduction)) =>
        println(s"A miracle! They really cut our taxes by $reduction percentage points!")
      case Failure(ex) =>
        println(s"They broke their promises! Again! Because of a ${ex.getMessage}")
    }
    true shouldBe true
  }
  
  "a Promise" should "be completable with Try" in {
    {
	    val quotientPromise = Promise[Int]()
	    //Sollte eigentlich die ArithmeticException verpacken statt sie weiterzureichen:
	    val quotientTry = Try{77/0}
	    quotientTry.isFailure shouldBe true
	    // Beendigung des Promise: 
	    quotientPromise.complete(quotientTry)
	    //Jetzt ist auch die zugehörige Future beendet:
	    quotientPromise.future.isCompleted shouldBe true
	    Await.result(quotientPromise.future, 0.seconds) shouldBe Failure(new ArithmeticException())
	}
    {
	    val quotientPromise = Promise[Int]()
	    val quotientTry = Try{77/5}
	    quotientTry.isFailure shouldBe false
	    quotientTry.isSuccess shouldBe true
	    // Beendigung des Promise: 
	    quotientPromise.complete(quotientTry)
	    //Jetzt ist auch die zugehörige Future beendet:
	    quotientPromise.future.isCompleted shouldBe true
	}
  }
  
  "zzz - Test" should "be completed" in {
    Thread.sleep(10*1000)
    true shouldBe true
  }
  
  
  
  /**This method can be used as a placeholder for code which should return something
   * which should be matched.
   * For example you could write ' XXX shouldBe "Nirvana" ' if you want to compile the test 
   * and replace XXX later by an expression, which returns "Nirvana".
   *  @throws `NotImplementedError` always */
  private def XXX = convertToAnyShouldWrapper{
    throw new NotImplementedError("Code-to-test still missing. See 2nd line of stack trace.")
  } 
  
}
