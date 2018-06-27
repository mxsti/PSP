package cappucino

/** A scenario where we sequentially prepare a cappuccino.
 * Follows Tutorial of Daniel Westheide "The Neophyte's Guide to Scala Part 8: Welcome to the Future"
 * http://danielwestheide.com/blog/2013/01/09/the-neophytes-guide-to-scala-part-8-welcome-to-the-future.html
 * Modifications: Also in sequential scenario the single steps are simulated as time-consuming.
 * @author Christoph Knabe
 * @since 2014-12-18*/
object SequentialCappuccinoMain extends /*A Scala application */App {
  
  //Wir wollen einen Cappuccino zubereiten: 
  //1. Kaffe mahlen (grind the beans)
  //2. Wasser erhitzen
  //3. Brühe einen Espresso auf aus dem gemahlenen Kaffee (ground coffee) und dem kochenden Wasser.
  //4. Milch aufschäumen (froth some milk up)
  //5. Espresso und aufgeschäumte Milch zu Cappuccino kombinieren.

  // dummy implementations of the individual steps with their durations:
  def grind(beans: CoffeeBeans) = slowly(4000, "grinding"){s"ground coffee of $beans"}
  def heatWater(water: Water): Water = slowly(6000, "heating"){water.copy(temperature = 85)}
  def frothMilk(milk: Milk) = slowly(10000, "frothing"){s"frothed $milk"}
  def brew(coffee: GroundCoffee, heatedWater: Water) = slowly(5000, "brewing"){"espresso"}
  def combine(espresso: Espresso, frothedMilk: FrothedMilk) = slowly(2000, "combine"){"cappuccino"}


  // going through these steps sequentially:
	def prepareCappuccino(): Cappuccino = {
	  val ground = grind("arabica")
	  val water = heatWater(Water(25))
	  val espresso = brew(ground, water)
	  val foam = frothMilk("milk")
    combine(espresso, foam)
	}
	
	val cappuccino = prepareCappuccino
	println(s"Preparing resulted in $cappuccino.")
	
	//Vorteil: Sequentielle, einfache Verarbeitung
	//Nachteil: Viel Warten 
	//Vergleichbar zu Webserver mit Threads.
	
	//Ausweg: Asynchron programmieren mit Scala Futures.
	//Weiter mit FutureCappuccinoMain.

}
