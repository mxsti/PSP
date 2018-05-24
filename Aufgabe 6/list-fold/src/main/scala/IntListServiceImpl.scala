class IntListServiceImpl extends IntListService {

  def containedIn(value: Int, list: List[Int]): Boolean = {
    list.foldLeft(false) { (notContaining,i) =>
      if (i == value) return true
      notContaining
    }
  }

  def removeFrom(value: Int, list: List[Int]): List[Int] = {
    list.foldRight(List[Int]()) { (i, resultList) =>
      if(i != value) i :: resultList
      else resultList
    }
  }

  def concatenate(head: List[Int], tail: List[Int]): List[Int] = {
    head.foldRight(tail) { (i, resultList) =>
      i :: resultList
    }
  }

}


