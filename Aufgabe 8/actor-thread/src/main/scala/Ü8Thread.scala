class Ü8Thread extends Thread {
  override def run(): Unit = {
      if(this.getId%100 == 0) println("thread " + this.getId + " started")
      Thread.sleep(1000 * 1000)
  }
}
