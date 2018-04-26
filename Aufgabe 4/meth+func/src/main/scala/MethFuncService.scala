class MethFuncService {

  /**Liefert die p-te Potenz von b.
    * @throws IllegalArgumentException b**p not representable in type Int*/
  def squareMultiplyMethod(b: Int, p: Int): Int = ???

  /**Liefert die p-te Potenz von b.*/
  val squareMultiplyFunction: (Int,Int)=>Int = {
    (b, p) => ???
  }

  /** Computes the area between the X-axis and the graph of the function f from 0 to endX
    * by summing up the rectangles beneath the graph with a width of incrementX. */
  def integrate(f: Double=>Double, endX: Double, incrementX: Double): Double = ???

}

