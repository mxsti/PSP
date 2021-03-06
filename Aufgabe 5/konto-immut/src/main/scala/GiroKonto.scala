case class GiroKonto (override val name: String, override val zinssatzPA: Double, override val saldoCt: Long = 0) extends Konto (name, zinssatzPA, saldoCt){

  override def einzahlen(betragCt: Long) : Konto = {
    require(betragCt > 0, "einzahlung kann nicht negativ oder 0 sein")
    GiroKonto(name,zinssatzPA,saldoCt + betragCt)
  }

  override def abheben(betragCt: Long): Konto = {
    require(betragCt > 0, "abhebung kann nicht negativ oder 0 sein")
    GiroKonto(name,zinssatzPA,saldoCt - betragCt)
  }

  override def verzinsen(anzahlTage: Int): Konto = {
    require(anzahlTage > 0)
    if (saldoCt >= 0){
      return GiroKonto(name,zinssatzPA,saldoCt)
    }
    val zinsSaldo = Math.round((saldoCt + (((zinssatzPA * saldoCt) / 360) * anzahlTage)))
    GiroKonto(name,zinssatzPA,zinsSaldo)
  }

  def ueberweisen(zielKonto: Konto, betragCt: Long): (GiroKonto,Konto) = {
    require(betragCt > 0)
    val neuesGirokonto = GiroKonto(name, zinssatzPA, saldoCt - betragCt)
    if (zielKonto.isInstanceOf[GiroKonto]){
      (neuesGirokonto, GiroKonto(zielKonto.name,zielKonto.zinssatzPA,zielKonto.saldoCt + betragCt))
    }else
      (neuesGirokonto, SparKonto(zielKonto.name,zielKonto.zinssatzPA,zielKonto.saldoCt + betragCt))
  }
}
