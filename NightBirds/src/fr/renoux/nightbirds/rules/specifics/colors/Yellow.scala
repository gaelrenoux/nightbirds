package fr.renoux.nightbirds.rules.specifics.colors

import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.Color
import fr.renoux.nightbirds.rules.specifics.cards.Whore
import fr.renoux.nightbirds.rules.specifics.cards.Cop
import fr.renoux.nightbirds.rules.specifics.cards.Dealer
import fr.renoux.nightbirds.rules.specifics.cards.DJ
import fr.renoux.nightbirds.rules.generics.Callbacks

/** Jaune : Flic, Prostituée, Dealer, Ivrogne*/
object Yellow extends Color {
  override def getCards(c: Callbacks, b: Board, f: Family) = Set[Card](
    new Cop(c, b, f),
    new Whore(c, b, f),
    new Dealer(c, b, f),
    new DJ(c, b, f))
}