/**Trait for List operations.
 * A Scala trait is much like a Java interface, but can contain concrete methods and attributes.
 * You can implement it either by extending or by withing it. For this exercise you should write:
 * class ListServiceImpl extends ListService { ... }
 * Generic parameters are notated by square brackets []
*/
trait ListService {
  
  /**Merges the two sorted Lists a and b into a sorted result List.
   * Sorting is done according to the predicate.
   * @param before A predicate judging two List elements, if the left one should be listed before the right one.
   * @throws IllegalArgumentException List a or List b is not sorted according to the predicate.*/
  def mergeSorted[T](a: List[T], b: List[T], before: (T,T)=>Boolean): List[T]

  case class Person(name: String, bornIn: Int)

  /**Returns a List of the ages of all given persons. Each individual age of a Person is calculated as the difference between nowYear and the bornIn attribute of the Person.*/
  def ageList(persons: List[Person], nowYear: Int): List[Int]

  /**Returns a String containing the names of all persons who are born less than 20 years before nowYear.
    * Each name is followed by a semicolon character.*/
  def youngPersonNames(persons: List[Person], nowYear: Int): String

  /**Returns the average age of all persons. Each individual age of a Person is calculated as the difference between nowYear and the bornIn attribute of the Person.
    * @throws UnsupportedOperationException The List persons is empty.*/
  def averageAge(persons: List[Person], nowYear: Int): Double

}
