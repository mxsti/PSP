class ListServiceImpl extends ListService {

  def mergeSorted[T](a: List[T], b: List[T], before: (T, T) => Boolean): List[T] = {
    require(a == a.sortWith(before))
    require(b == b.sortWith(before))
    if (a == Nil) return b
    if (b == Nil) return a
    if (before(a.head, b.head)) {
      a.head :: mergeSorted(a.tail, b, before)
    } else {
      b.head :: mergeSorted(a, b.tail, before)
    }
  }

  def ageList(persons: List[Person], nowYear: Int): List[Int] = {
    persons.map {
      nowYear - _.bornIn
    }
  }

  def youngPersonNames(persons: List[Person], nowYear: Int): String = {
    val stringList = persons.filter(nowYear - _.bornIn < 20)
    var resultString = ""
    stringList.foreach(x => resultString += x.name + ";")
    resultString
  }

  def averageAge(persons: List[Person], nowYear: Int): Double = {
    if (persons.isEmpty) throw new UnsupportedOperationException
    val ageSum = persons.foldLeft(0) { (sum, value) =>
      sum + (nowYear - value.bornIn)
    }
    ageSum.toDouble / persons.length
  }
}
