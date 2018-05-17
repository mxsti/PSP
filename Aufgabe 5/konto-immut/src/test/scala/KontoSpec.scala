//Aufbau analog zu http://www.scalatest.org/quick_start

import org.scalatest.{FlatSpec, Matchers}

/**ScalaTest-Spezifikation für die zu erstellende Konto-Klassenhierarchie.
  *
  * @author Christoph Knabe
  * @since 2018-04-27*/
class KontoSpec extends FlatSpec with Matchers {

  "SparKonto" should "name, saldo und zinssatzPA haben" in {
    val kontoV1 = SparKonto("Knabe", 0.02) //SparKonto mit Guthabenzinssatz 2% per annum

    kontoV1.name shouldBe "Knabe"
    kontoV1.saldoCt shouldBe 0
    kontoV1.zinssatzPA shouldBe 0.02 +- 1E-6
  }

  it should "unnatürliche Beträge abweisen" in {
    val kontoV1 = SparKonto("Knabe", 0.02)
    assertThrows[IllegalArgumentException] {
      kontoV1.einzahlen(0)
    }
    assertThrows[IllegalArgumentException] {
      kontoV1.abheben(0)
    }
  }

  it should "Kontoüberziehung abweisen" in {
    val kontoV1 = SparKonto("Knabe", 0.02)
    assertThrows[IllegalArgumentException] {
      kontoV1.abheben(1)
    }
    //Dieser Abhebungsversuch darf nichts am Saldo geändert haben:
    kontoV1.saldoCt shouldBe 0
  }

  it should "Guthaben tagesgenau verzinsen" in {
    val kontoV1 = SparKonto("Knabe", 0.03) //SparKonto mit Guthabenzinssatz 3%
    val kontoV2 = kontoV1.einzahlen(100000)
    kontoV2.saldoCt shouldBe 100000

    {
      val result = kontoV2.verzinsen(anzahlTage = 360) //kaufmännisches ganzes Jahr
      result.saldoCt shouldBe 103000
    }
    {
      val result = kontoV2.verzinsen(10) //Kaufmännisch gelten 360 Tage als ein Jahr
      result.saldoCt shouldBe 100083
    }
  }

  "SparKonto.einzahlen" should "Betrag zum Saldo addieren" in {
    val kontoV1 = SparKonto("Knabe", 0.02) //SparKonto mit Guthabenzinssatz 2% per annum
    val kontoV2 = kontoV1.einzahlen(10000)
    kontoV2.saldoCt shouldBe 10000
    kontoV2.isInstanceOf[SparKonto] shouldBe true
  }

  "SparKonto.verzinsen" should "unnatürliche Tagesanzahl abweisen" in {
    val kontoV1 = SparKonto("Knabe", 0.02) //SparKonto mit Guthabenzinssatz 2% per annum
    assertThrows[IllegalArgumentException] {
      kontoV1.verzinsen(0)
    }
  }

  "SparKonto.abheben" should "Betrag vom Saldo abziehen" in {
    val kontoV1 = SparKonto("Knabe", 0.02) //SparKonto mit Guthabenzinssatz 2% per annum
    val kontoV2 = kontoV1.einzahlen(10000)
    val kontoV3 = kontoV2.abheben(3333)
    kontoV3.saldoCt shouldBe 6667
    kontoV3.isInstanceOf[SparKonto] shouldBe true
  }

  "GiroKonto" should "name, saldo und zinssatzPA haben" in {
    val kontoV1 = GiroKonto("Schmidt", 0.10) //GiroKonto mit Überziehungszinssatz 10% per annum

    kontoV1.name shouldBe "Schmidt"
    kontoV1.saldoCt shouldBe 0
    kontoV1.zinssatzPA shouldBe 0.10 +- 1E-6
  }

  it should "unnatürliche Beträge abweisen" in {
    val kontoV1 = GiroKonto("Schmidt", 0.10)
    assertThrows[IllegalArgumentException] {
      kontoV1.einzahlen(0)
    }
    assertThrows[IllegalArgumentException] {
      kontoV1.abheben(0)
    }
  }

  "GiroKonto.einzahlen" should "Betrag zum Saldo addieren" in {
    val kontoV1 = GiroKonto("Schmidt", 0.10) //GiroKonto mit Überziehungszinssatz 10% per annum
    val kontoV2 = kontoV1.einzahlen(10000)
    kontoV2.saldoCt shouldBe 10000
    kontoV2.isInstanceOf[GiroKonto] shouldBe true
  }

  "GiroKonto.abheben" should "Betrag vom Saldo abziehen" in {
    val kontoV1 = GiroKonto("Schmidt", 0.10) //GiroKonto mit Überziehungszinssatz 10% per annum
    val kontoV2 = kontoV1.einzahlen(10000)
    val kontoV3 = kontoV2.abheben(3333)
    kontoV3.saldoCt shouldBe 6667
    kontoV3.isInstanceOf[GiroKonto] shouldBe true
  }

  it should "Überziehung erlauben" in {
    val kontoV1 = GiroKonto("Schmidt", 0.10) //GiroKonto mit Überziehungszinssatz 10% per annum
    val kontoV2 = kontoV1.einzahlen(10000)
    val kontoV3 = kontoV1.abheben(999999)
    kontoV3.saldoCt shouldBe -999999
  }

  "GiroKonto.ueberweisen" should "zwei korrekt geänderte Konten des richtigen Typs liefern" in {
    val quellKontoV1 = GiroKonto("Schmidt", 0.10)
    val zielKontoV1 = SparKonto("Knabe", 0.02)
    val (quellKontoNeu, zielKontoNeu) = quellKontoV1.ueberweisen(zielKontoV1, 12345)
    quellKontoNeu.saldoCt shouldBe -12345
    zielKontoNeu.saldoCt shouldBe 12345
    quellKontoNeu.isInstanceOf[GiroKonto] shouldBe true
    zielKontoNeu.isInstanceOf[SparKonto] shouldBe true
  }

  it should "unnatürliche Beträge abweisen" in {
    val quellKontoV1 = GiroKonto("Schmidt", 0.10)
    val zielKontoV1 = SparKonto("Knabe", 0.02)
    assertThrows[IllegalArgumentException] {
      quellKontoV1.ueberweisen(zielKontoV1, 0)
    }
  }

  "GiroKonto.verzinsen" should "Guthaben ignorieren" in {
    val kontoV1 = GiroKonto("Schmidt", 0.15) //GiroKonto mit Überziehungszinssatz 15% per annum
    val kontoV2 = kontoV1.einzahlen(100000)
    val result = kontoV2.verzinsen(360)
    result.saldoCt shouldBe 100000
  }

  it should "Negativsaldo bestrafen" in {
    val kontoV1 = GiroKonto("Schmidt", 0.15) //GiroKonto mit Überziehungszinssatz 15% per annum
    val kontoV2 = kontoV1.abheben(10000);
    {
      val result = kontoV2.verzinsen(360) //Kaufmännisch gelten 360 Tage als ein Jahr
      result.saldoCt shouldBe -11500
    }
    {
      val result = kontoV2.verzinsen(10) //Kaufmännisch gelten 360 Tage als ein Jahr
      result.saldoCt shouldBe -10042
    }
  }

  it should "unnatürliche Tagesanzahl abweisen" in {
    val kontoV1 = GiroKonto("Schmidt", 0.15) //GiroKonto mit Überziehungszinssatz 15% per annum
    assertThrows[IllegalArgumentException] {
      kontoV1.verzinsen(0)
    }
  }

}
