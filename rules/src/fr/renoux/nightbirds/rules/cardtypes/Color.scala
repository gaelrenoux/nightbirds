package fr.renoux.nightbirds.rules.cardtypes

import fr.renoux.nightbirds.rules.state.Card
import fr.renoux.nightbirds.rules.state.Family

sealed abstract class Color(val cardMakers: (Family => Card)*) {
  def makeCards(f: Family): Set[Card] = cardMakers.map { _.apply(f) }.toSet
}

object Pink extends Color(new Bum(_), new Whore(_))
object Kaki extends Color(new Bum(_), new Bum(_))
object Taupe extends Color(new Whore(_), new Whore(_))
/* object Black extends Color(Whore, PrivateEye, Thug, Bum)
object Blue extends Color(Photograph, Cop, Skinhead, Bum)
object Green extends Color(Taxi, Dealer, PrivateEye, Burglar)
object Orange extends Color(Thug, Cook, Burglar, Taxi)
object White extends Color(Cook, Skinhead, Bum, Photograph)
object Yellow extends Color(Cop, Whore, Dealer, Bum) */

object Color {
  def all: Set[Color] = Set(Pink, Kaki, Taupe)
}