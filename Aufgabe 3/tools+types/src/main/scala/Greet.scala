object Greet {

  def main(args: Array[String]){
    println(("Hello" :: args.toList).mkString(", "))
  }

}