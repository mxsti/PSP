abstract class ListServiceImpl extends ListService {

  def mergeSorted[T](a: List[T], b: List[T], before: (T,T)=>Boolean): List[T]

  case class Person(name: String, bornIn: Int)

  def ageList(persons: List[Person], nowYear: Int): List[Int]

  def youngPersonNames(persons: List[Person], nowYear: Int): String

  def averageAge(persons: List[Person], nowYear: Int): Double

}
