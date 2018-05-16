/** Ein allgemeines Konto im funktionalen Stil. Jede Methode liefert eine veränderte Kopie des aktuellen Objekts. */
abstract class Konto(val name: String, val zinssatzPA: Double, val saldoCt: Long) {

  /** Liefert ein Konto gleichen Typs mit einem um den Betrag höheren Saldo, wobei die anderen Daten gleich bleiben.
    *
    * @throws IllegalArgumentException Betrag Null oder negativ */
  def einzahlen(betragCt: Long): Konto

  /** Liefert ein Konto gleichen Typs mit einem um den Betrag geringeren Saldo, wobei die anderen Daten gleich bleiben.
    *
    * @throws IllegalArgumentException Betrag Null oder negativ */
  def abheben(betragCt: Long): Konto

  /** Liefert ein Konto gleichen Typs mit einem gemäß dem Kontotyp und Zinssatz veränderten Saldo, wobei die anderen Daten gleich bleiben.
    *
    * @throws IllegalArgumentException anzahlTage Null oder negativ.*/
  def verzinsen(anzahlTage: Int): Konto

}
