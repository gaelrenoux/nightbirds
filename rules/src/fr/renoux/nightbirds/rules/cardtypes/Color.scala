package fr.renoux.nightbirds.rules.cardtypes

import fr.renoux.nightbirds.rules.state.Card
import fr.renoux.nightbirds.rules.state.Family

sealed abstract class Color(val cardMakers: (Family => Card)*) {
  def makeCards(f: Family): Set[Card] = cardMakers.map { _.apply(f) }.toSet
  override def toString = this.getClass().getSimpleName().dropRight(1)
  lazy val toFixedString = toString.padTo(Color.FixedStringLength, ' ')
}

/* Colors for test */
object Pink extends Color(new Bum(_), new Whore(_), new Cook(_), new Dealer(_))
object Kaki extends Color(new Burglar(_), new Photograph(_), new Taxi(_), new Cop(_))
object Taupe extends Color(new Dj(_), new Skinhead(_), new Thug(_), new PrivateEye(_))

/* Real colors */
object Black extends Color(new Whore(_), new PrivateEye(_), new Thug(_), new Bum(_))
object Blue extends Color(new Photograph(_), new Cop(_), new Skinhead(_), new Bum(_))
object Green extends Color(new Taxi(_), new Dealer(_), new PrivateEye(_), new Burglar(_))
object Orange extends Color(new Thug(_), new Cook(_), new Burglar(_), new Taxi(_))
object White extends Color(new Cook(_), new Skinhead(_), new Bum(_), new Photograph(_))
object Yellow extends Color(new Cop(_), new Whore(_), new Dealer(_), new Bum(_))

object Color {
  def all: Set[Color] = Set(Black, Blue, Green, Orange, White, Yellow)

  val FixedStringLength = 6
}