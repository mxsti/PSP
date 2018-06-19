package combine

import scala.concurrent.Future

/**Spezifiziert einige übliche Kombinationen für Futures, die Sie implementieren sollen.*/
trait FutureCombine {
  
  /**Liefert eine Future mit dem Ergebnis bzw. Versagen der als erste terminierenden Future aus {futures}.
   * Die Ergebnisse aller anderen Futures aus {futures} werden ignoriert.*/
  def firstOf[T](futures: Seq[Future[T]]): Future[T]
  
  /**Liefert eine Future mit einer Sequenz der erfolgreichen Ergebnisse aller Futures aus {futures}.
   * Wenn eine Future aus {futures} versagt oder nicht innerhalb des Timeout-Zeitraums terminiert, 
   * versagt auch die gelieferte Future mit einer Exception. */
  def allOf[T](futures: Seq[Future[T]]): Future[Seq[T]]

}
