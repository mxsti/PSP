object TestMethFunc {

    def main(args: Array[String]) {
      //Methode
      val testObjekt = new MethFuncService
      println(testObjekt.squareMultiplyMethod(-2,30))
      //Funktion
      val f = testObjekt.squareMultiplyFunction
      println(f(2,5))
    }
}
