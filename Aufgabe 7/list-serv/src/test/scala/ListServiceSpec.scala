//Aufbau analog zu http://www.scalatest.org/quick_start

import org.scalatest.{FlatSpec, Matchers}

/**ScalaTest-Specification testing the implementation of the trait ListService.
  *
  * @author Christoph Knabe
  * @since 2018-05-18*/
class ListServiceSpec extends FlatSpec with Matchers {

  /** An instance of the ListService, which has to be developed by the students. */
  val testee: ListService = new ListServiceImpl

  //Test the mergeSorted method:

  "mergeSorted" should "ignore Nil in left position" in {
    val a = Nil
    val b = List(2, 4, 6, 8, 10)
    val result = testee.mergeSorted[Int](a, b, _<_)
    result shouldBe b
  }

  it should "ignore Nil in right position" in {
    val a = List(1, 3, 5, 7, 9)
    val b = Nil
    val result = testee.mergeSorted[Int](a, b, _<_)
    result shouldBe a
  }
  
  it should "refuse left unsorted list" in {
    val a = List(3, 5, 7, 9, 8)
    val b = List(2, 4, 6, 8, 10)
    assertThrows[IllegalArgumentException]{testee.mergeSorted[Int](a, b, _<_)}
  }

  it should "refuse right unsorted list" in {
    val a = List(1, 3, 5, 7, 9)
    val b = List(2, 1, 6, 8, 10)
    assertThrows[IllegalArgumentException]{testee.mergeSorted[Int](a, b, _<_)}
  }

  it should "merge 2 sorted Lists of same length with a sorted result" in {
    val a = List(1, 3, 5, 7, 9)
    val b = List(2, 4, 6, 8, 10)
    val resultAB = testee.mergeSorted[Int](a, b, _<_)
    resultAB shouldBe List(1,2,3,4,5,6,7,8,9,10)
    val resultBA = testee.mergeSorted[Int](b, a, _<_)
    resultBA shouldBe List(1,2,3,4,5,6,7,8,9,10)
  }

  it should "merge 2 sorted Lists of unsame length with a sorted result" in {
    val a = List(1, 3, 5)
    val b = List(2, 4, 6, 8, 10)
    val resultAB = testee.mergeSorted[Int](a, b, _<_)
    resultAB shouldBe List(1,2,3,4,5,6,8,10)
    val resultBA = testee.mergeSorted[Int](b, a, _<_)
    resultBA shouldBe List(1,2,3,4,5,6,8,10)
  }

  //Test the Person related methods:

  import testee.Person

  "ageList" should "compute the ages of a List of Persons" in {
    testee.ageList(Nil, 2017) shouldBe Nil
    val clients = Person("Hans", 2001) :: Person("Lina", 1999) :: Person("Omar", 2004) :: Nil
    val result = testee.ageList(clients, 2029)
    result shouldBe List(28, 30, 25)
  }

  "youngPersonNames" should "return all persons younger than 20 years" in {
    testee.youngPersonNames(Nil, 2017) shouldBe ""
    val clients = Person("Hans", 2001) :: Person("Lina", 1999) :: Person("Omar", 2004) :: Nil
    testee.youngPersonNames(clients, 2018) shouldBe "Hans;Lina;Omar;"
    testee.youngPersonNames(clients, 2019) shouldBe "Hans;Omar;"
    testee.youngPersonNames(clients, 2021) shouldBe "Omar;"
    testee.youngPersonNames(clients, 2023) shouldBe "Omar;"
    testee.youngPersonNames(clients, 2024) shouldBe ""
  }

  "averageAge" should "compute the average age of all persons" in {
    assertThrows[UnsupportedOperationException]{testee.averageAge(Nil, 2017)}

    val clients = Person("Hans", 1960) :: Person("Lina", 1970) :: Person("Omar", 1980) :: Nil
    testee.averageAge(clients, 2000) shouldBe 30.0 +- 0.0001
    testee.averageAge(clients, 1980) shouldBe 10.0 +- 0.0001

    val children = Person("Hans", 2002) :: Person("Lina", 2000) :: Person("Omar", 2003) :: Nil
    testee.averageAge(children, 2005) shouldBe 3.3333333333 +- 0.0000001
  }

  
}
