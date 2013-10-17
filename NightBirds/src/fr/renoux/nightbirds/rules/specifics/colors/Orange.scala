package fr.renoux.nightbirds.rules.specifics.colors

import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.Color
import fr.renoux.nightbirds.rules.specifics.cards.Taxi
import fr.renoux.nightbirds.rules.specifics.cards.Burglar
import fr.renoux.nightbirds.rules.specifics.cards.Thug
import fr.renoux.nightbirds.rules.specifics.cards.Cook
import fr.renoux.nightbirds.rules.generics.Callbacks

/** Orange : Petite frappe, cuistot, cambrioleur, taxi */
object Orange extends Color {
  override def getCards(c : Callbacks, b: Board, f: Family) = Set[Card](
    new Thug(c, b, f),
    new Cook(c, b, f),
    new Burglar(c, b, f),
    new Taxi(c, b, f))
}