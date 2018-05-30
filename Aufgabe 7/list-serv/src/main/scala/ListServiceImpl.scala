class ListServiceImpl {

  def mergeSorted[T](a: List[T], b: List[T], before: (T,T)=>Boolean): List[T] = {
    ???
  }

  case class Person (name: String, bornIn: Int)

    def ageList(persons: List[Person], nowYear: Int): List[Int] = {
      persons.map { nowYear - _.bornIn }
    }

    def youngPersonNames(persons: List[Person], nowYear: Int): String = {
      val stringList = persons.filter(nowYear - _.bornIn < 20)
      var resultString = ""
      stringList.foreach(x => resultString += x.name + ";")
      resultString
    }

    def averageAge(persons: List[Person], nowYear: Int): Double = {
      if (persons.isEmpty) throw new UnsupportedOperationException
      val sum = persons.foldLeft(0) { (x, value) =>
        x + (nowYear - value.bornIn)
      }
      sum.toDouble / persons.length
    }
}
