package fr.renoux.nightbirds.rules.cardtypes

import fr.renoux.nightbirds.rules.state.CardType

sealed abstract class Color(val cards : CardType*)
/* object Black extends Color(Whore, PrivateEye, Thug, Bum)
object Blue extends Color(Photograph, Cop, Skinhead, Bum)
object Green extends Color(Taxi, Dealer, PrivateEye, Burglar)
object Orange extends Color(Thug, Cook, Burglar, Taxi)
object White extends Color(Cook, Skinhead, Bum, Photograph)
object Yellow extends Color(Cop, Whore, Dealer, Bum) */

object Color {
  def all : Set[Color] = Set()
}