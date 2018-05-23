/**Contains service methods for List[Int].*/
trait IntListService {
    
    /**Returns true, if the passed value is contained in the passed list.*/
    def containedIn(value: Int, list: List[Int]): Boolean
    
    /**Returns a List with all elements of list in the same order, but without all occurrences of value.*/
    def removeFrom(value: Int, list: List[Int]): List[Int]
    
    /**Returns a List consisting of the elements of List head, followed by the elements of List tail.*/
    def concatenate(head: List[Int], tail: List[Int]): List[Int]

}
