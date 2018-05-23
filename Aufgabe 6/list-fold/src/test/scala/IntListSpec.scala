//Aufbau analog zu http://www.scalatest.org/quick_start

import org.scalatest.{FlatSpec, Matchers}

/**ScalaTest-Specification testing the implementation IntListServiceImpl
  * for trait IntListService and the predefined class List[Int].
  *
  * @author Christoph Knabe
  * @since 2018-05-10*/
class IntListSpec extends FlatSpec with Matchers {

  /** An instance of the IntListService, which has to be developed by the students. */
  val testee: IntListService = new IntListServiceImpl

  "A List" should "contain all its elements, but no others" in {
    val list123 = List(1, 2, 3)
    testee.containedIn(0, list123) shouldBe false
    testee.containedIn(1, list123) shouldBe true
    testee.containedIn(2, list123) shouldBe true
    testee.containedIn(3, list123) shouldBe true
    testee.containedIn(4, list123) shouldBe false
  }

  it should "remove given element if contained" in {
    val list1231 = List(1, 2, 3, 1)
    testee.removeFrom(0, list1231) shouldBe List(1, 2, 3, 1)
    testee.removeFrom(1, list1231) shouldBe List(2, 3)
    testee.removeFrom(2, list1231) shouldBe List(1, 3, 1)
    testee.removeFrom(3, list1231) shouldBe List(1, 2, 1)
    testee.removeFrom(4, list1231) shouldBe List(1, 2, 3, 1)
  }

  it should "be concatenable with another List" in {
    val list123 = 1 :: 2 :: 3 :: Nil
    val list456 = List(4, 5, 6)
    testee.concatenate(Nil, list123) shouldBe List(1, 2, 3)
    testee.concatenate(list123, Nil) shouldBe List(1, 2, 3)
    testee.concatenate(list123, list456) shouldBe List(1, 2, 3, 4, 5, 6)
    testee.concatenate(list456, list123) shouldBe List(4, 5, 6, 1, 2, 3)
  }

  //Lehrmaterial für Folien "Unveränderbare Listen Scala predefined":

  ".::" should "build a List step by step" in {
    val numbers = Nil.::(7).::(5).::(3).::(2)
    numbers shouldBe List(2, 3, 5, 7)
  }

  "::" should "build a List step by step" in {
    val numbers = 2 :: 3 :: 5 :: 7 :: Nil
    numbers shouldBe List(2, 3, 5, 7)
  }

  "apply" should "build a List in one call" in {
    val numbers = List(2, 3, 5, 7)
    numbers shouldBe List(2, 3, 5, 7)
  }

  /** Liefert eine Liste mit den natürlichen Zahlen von low bis high. */
  def range(low: Int, high: Int): List[Int] = {
    if (low > high) Nil
    else low :: range(low + 1, high)
  }

  "range" should "build a List from low to high inclusive" in {
    val result = range(1, 6)
    result shouldBe List(1, 2, 3, 4, 5, 6)
  }

  /** Liefert die Anzahl von Int-Zahlen in dieser Liste. */
  def size(list: List[Int]): Int = {
    if (list.isEmpty) 0 else 1 + size(list.tail)
  }

  "size" should "count the elements in a List" in {
    val list1to6 = range(1, 6)
    size(list1to6) shouldBe 6
  }

  /** Returns the sum of all elements of the given list. Returns 0 if the list is empty. */
  def sum(list: List[Int]): Int = {
    if (list.isEmpty) 0 else list.head + sum(list.tail)
  }

  "sum" should "sum up the elements of a List" in {
    val list1to10 = range(1, 10)
    sum(list1to10) shouldBe 55
  }

  def elemString(list: List[Int]): String = {
    if (list.isEmpty) ""
    else list.head + " " + elemString(list.tail)
  }

  "elemString" should "render all elements with space separator" in {
    elemString(Nil) shouldBe ""
    elemString(1 :: Nil).trim shouldBe "1"
    val list1to6 = range(1, 6)
    elemString(list1to6).trim shouldBe "1 2 3 4 5 6"
  }

  /** A general operation alias to integrate an Int value into a RESULT */
  type Operation[RESULT] = (RESULT, Int) => RESULT

  /** Returns the accumulated result of applying the operation op to each value in the given list from right to left,
    * and the previous result, starting with the initial value.
    *
    * @throws java.lang.StackOverflowError This List is too long. */
  def foldRight[R](list: List[Int], op: Operation[R], initial: R): R = {
    if (list.isEmpty) initial
    else op(foldRight(list.tail, op, initial), list.head);
  }

  /** Returns a String sequence of all elements of the given List delimited by spaces. Done by foldRight. */
  def elemStringFR(list: List[Int]): String =
    foldRight(list, (accu: String, value) => value + " " + accu, "")

  "elemStringFR" should "render all elements with space separator" in {
    elemStringFR(Nil) shouldBe ""
    elemStringFR(1 :: Nil).trim shouldBe "1"
    val list1to6 = range(1, 6)
    elemStringFR(list1to6).trim shouldBe "1 2 3 4 5 6"
  }

  /** Returns the sum of all elements of this list. If the list is empty, 0 is returned. */
  def sumFR(list: List[Int]): Int = foldRight(list, (x: Int, y: Int) => x + y, 0)

  "sumFR" should "sum up the elements of a List" in {
    val list1to10 = range(1, 10)
    sumFR(list1to10) shouldBe 55
  }

  /** Returns a String sequence of all elements of the given List delimited by spaces. Done efficiently by a while loop with a StringBuilder. */
  def elemStringEffizient(list: List[Int]): String = {
    val result = new StringBuilder
    var current = list
    while (!current.isEmpty) {
      result append current.head append " "
      current = current.tail
    }
    result.toString
  }

  "elemStringEffizient" should "render all elements with space separator" in {
    elemStringEffizient(Nil) shouldBe ""
    elemStringEffizient(1 :: Nil).trim shouldBe "1"
    val list1to6 = range(1, 6)
    elemStringEffizient(list1to6).trim shouldBe "1 2 3 4 5 6"
  }

  /** Returns the accumulated result of applying the operation op to each value in the list from left to right,
    * and the previous result, starting with the initial value. */
  def foldLeft[RESULT](list: List[Int], op: Operation[RESULT], initial: RESULT): RESULT = {
    var result = initial
    var current = list
    while (!current.isEmpty) {
      result = op(result, current.head)
      current = current.tail
    }
    result
  }

  /** Returns a String representation of the list's content in the format of for example "1 2 3" in an efficient manner by foldLeft using a StringBuilder. */
  def elemStringFL(list: List[Int]): String = {
    val result = new StringBuilder
    foldLeft(
      list,
      (accu: StringBuilder, value: Int)
      => accu append value append ' '
      , result
    );
    result.toString
  }

  "elemStringFL" should "render all elements with space separator" in {
    elemStringFL(Nil) shouldBe ""
    elemStringFL(1 :: Nil).trim shouldBe "1"
    val list1to6 = range(1, 6)
    elemStringFL(list1to6).trim shouldBe "1 2 3 4 5 6"
  }

  "scala.List" should "have working HOM" in {
    (1 to 5).toList shouldBe List(1, 2, 3, 4, 5)
    List().size shouldBe 0
    List(1, 2, 3).size shouldBe 3

    List[Int]().sum shouldBe 0
    List(1, 2, 3).sum shouldBe 6

    List(1, 2, 3).toString shouldBe "List(1, 2, 3)"

    List(1, 2, 3, 4).foldLeft("+")((a: String, x: Int) => a + x) shouldBe "+1234"
    List(1, 2, 3, 4).foldRight("+")((x: Int, a: String) => x + a) shouldBe "1234+"
  }

}
