case class SparKonto (override val name: String, override val zinssatzPA: Double, override val saldoCt: Long = 0) extends Konto(name, zinssatzPA, saldoCt){

  require(saldoCt >= 0, "beim sparkonto darf das saldo nicht kleiner als 0 sein")

  override def einzahlen(betragCt: Long) : Konto = {
    require(betragCt >= 0, "einzahlung kann nicht negativ oder 0 sein")
    return new SparKonto(name,zinssatzPA,saldoCt + betragCt)
  }

  override def abheben(betragCt: Long): Konto = {
    require(betragCt >= 0, "abhebung kann nicht negativ oder 0 sein")
    return new SparKonto(name,zinssatzPA,saldoCt - betragCt)
  }

}
