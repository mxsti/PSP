class MethFuncService {

  /** Liefert die p-te Potenz von b.
    *
    * @throws IllegalArgumentException b**p not representable in type Int */
  def squareMultiplyMethod(b: Int, p: Int): Int = {
    require(b >= 0, "b muss größer gleich 0 sein")
    require(p >= 0, "p muss größer gleich 0 sein")
    if (p == 1) b
    else if (p % 2 != 0) b * squareMultiplyMethod(b, (p - 1))
    else (squareMultiplyMethod(b, p / 2) * squareMultiplyMethod(b, p / 2))
  }

  /**Liefert die p-te Potenz von b.*/
  val squareMultiplyFunction: (Int,Int)=>Int = {
    (b, p) => squareMultiplyMethod(b,p)
  }

  /** Computes the area between the X-axis and the graph of the function f from 0 to endX
    * by summing up the rectangles beneath the graph with a width of incrementX. */
  def integrate(f: Double=>Double, endX: Double, incrementX: Double): Double = {
    var x = incrementX
    var result = 0.0
    while (x <= endX){
      result = result + (f(x) * incrementX)
      x = x + incrementX
    }
    return result
  }


}

