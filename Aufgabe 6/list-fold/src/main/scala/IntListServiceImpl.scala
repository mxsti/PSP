class IntListServiceImpl extends IntListService {

  def containedIn(value: Int, list: List[Int]): Boolean = {
    list.foldLeft(false) { (notContaining,i) =>
      if (i == value){
        return true
      }
    notContaining
    }
  }

  def removeFrom(value: Int, list: List[Int]): List[Int] = {
    list.foldLeft(List[Int]()) { (resultList,i) =>
      if (i != value){
        resultList :+ i
      }else {
        resultList
      }
    }
  }

  def concatenate(head: List[Int], tail: List[Int]): List[Int] = {
    tail.foldLeft(head) { (resultList,i) =>
      resultList :+ i
    }
  }

}


