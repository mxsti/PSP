package cappucino

import combine.FutureCombine

import scala.concurrent.Future

class FutureCombineImpl extends FutureCombine {
  /** Liefert eine Future mit dem Ergebnis bzw. Versagen der als erste terminierenden Future aus {futures}.
    * Die Ergebnisse aller anderen Futures aus {futures} werden ignoriert. */
  override def firstOf[T](futures: Seq[Future[T]]): Future[T] = ???

  /** Liefert eine Future mit einer Sequenz der erfolgreichen Ergebnisse aller Futures aus {futures}.
    * Wenn eine Future aus {futures} versagt oder nicht innerhalb des Timeout-Zeitraums terminiert,
    * versagt auch die gelieferte Future mit einer Exception. */
  override def allOf[T](futures: Seq[Future[T]]): Future[Seq[T]] = ???
}
