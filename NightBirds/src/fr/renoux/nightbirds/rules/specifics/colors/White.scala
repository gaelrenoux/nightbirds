package fr.renoux.nightbirds.rules.specifics.colors

import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.Color
import fr.renoux.nightbirds.rules.specifics.cards.Photograph
import fr.renoux.nightbirds.rules.specifics.cards.Bum
import fr.renoux.nightbirds.rules.specifics.cards.Skinhead
import fr.renoux.nightbirds.rules.specifics.cards.Cook
import fr.renoux.nightbirds.rules.generics.Callbacks

/** Blanc : cuistot, skinhead, clochard, photographe */
object White extends Color {
  override def getCards(c : Callbacks, b: Board, f: Family) = Set[Card](
    new Cook(c, b, f),
    new Skinhead(c, b, f),
    new Bum(c, b, f),
    new Photograph(c, b, f))
}