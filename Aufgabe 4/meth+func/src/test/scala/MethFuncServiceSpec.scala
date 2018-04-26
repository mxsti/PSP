//Aufbau analog zu http://www.scalatest.org/quick_start

import org.scalatest.{FlatSpec, Matchers}

/**ScalaTest-Spezifikation für das zu auszufüllende MethFuncService-Objekt mit einer Methode und einer Funktion zum Potenzieren durch Quadrieren und Multiplizieren
  * sowie einer höheren Methode zum Integrieren.
  *
  * @author Christoph Knabe
  * @since 2017-04-27*/
class MethFuncServiceSpec extends FlatSpec with Matchers {

  private val testee = new MethFuncService

  "The squareMultiplyMethod" should "compute powers of 2 up to exponent 30" in {
    _testSquareMultiply(testee.squareMultiplyMethod(_,_), 2, 30)
  }

  it should "compute powers of 3 up to exponent 19" in {
    val f: (Int,Int)=>Int = testee.squareMultiplyMethod
    _testSquareMultiply(testee.squareMultiplyMethod(_,_), 3, 19)
  }

  "The squareMultiplyFunction" should "be a function (Int, Int) => Int" in {
    val f = testee.squareMultiplyFunction
    assert(f.isInstanceOf[Function2[Int,Int,Int]], "MethFuncService.squareMultiplyFunction ist keine zweistellige Funktion im Typ Int")
  }

  it should "compute powers of 2 up to exponent 30" in {
    _testSquareMultiply(testee.squareMultiplyFunction, 2, 30)
  }

  it should "compute powers of 3 up to exponent 19" in {
    _testSquareMultiply(testee.squareMultiplyFunction, 3, 19)
  }

  private def _testSquareMultiply(power: (Int,Int)=>Int, base: Int, maxExponent: Int): Unit = {
    println()
    println("Potenzen von " + base)
    power(base,0) shouldBe 1
    var expected = 1
    for(i <- 1 to maxExponent){
      expected = expected*base
      val actual = power(base,i)
      actual shouldBe expected
      println(actual)
    }
    intercept[IllegalArgumentException] {
      power(base, maxExponent+1)
    }
  }

//=============================================================
  //Alles für den Test der integerate-Methode:

  /**Eine Funktion, die konstant 2.0 liefert.*/
  private val fConst: Double=>Double = x=>2.0

  /**Die erwartete Stammfunktion von dieser.*/
  private def expectedFConstIntegral(x: Double) = 2 * x

  /**Eine lineare Funktion (die Identität).*/
  private val fLin1: Double=>Double = x=>x

  /**Die erwartete Stammfunktion von dieser.*/
  private val expectedFLin1Integral: Double=>Double = x => x*x / 2

  /**Eine quadratische Funktion.*/
  private val fQuad: Double=>Double = x => 3*x*x + 5

  /**Die erwartete Stammfunktion von dieser.*/
  private val expectedFQuadIntegral: Double=>Double = x => x*x*x + 5*x


  //Tests der integrate-Methode:

  /**Der Faktor, um den das errechnete Ergebnis vom mathematisch korrekten abweichen darf.*/
  private val errorFactor = 0.01

  "integrate" should "compute the integral of constant 2.0 from 0 to 10 in steps of 0.1" in {
    val actual = testee.integrate(fConst, 10.0, 0.1)
    val expected = expectedFConstIntegral(10.0)
    actual shouldBe expected +- (expected * errorFactor)
  }

  it should "compute the integral of identity from 0 to 5 in steps of 0.02" in {
    val actual = testee.integrate(fLin1, 5, 0.02)
    val expected = expectedFLin1Integral(5.0)
    actual shouldBe expected +- (expected * errorFactor)
  }

  it should "compute the integral of 3*x*x+5 from 0 to 5 in steps of 0.01" in {
    val actual = testee.integrate(fQuad, 5, 0.01)
    val expected = expectedFQuadIntegral(5.0)
    actual shouldBe expected +- (expected * errorFactor)
  }

}
