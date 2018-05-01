import java.io.File

object Dir {

  def main(args: Array[String]){
    val dir = new File(args(0))
    val files = dir.list
    files.sorted.foreach(println)
  }

}
