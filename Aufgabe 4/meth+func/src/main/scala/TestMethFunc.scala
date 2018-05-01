object TestMethFunc {

    def main(args: Array[String]) {
      //Methode
      val testObjekt = new MethFuncService
      println(testObjekt.squareMultiplyMethod(3,3))
      //Funktion
      val f = testObjekt.squareMultiplyFunction
      println(f(2,16))
    }
}
