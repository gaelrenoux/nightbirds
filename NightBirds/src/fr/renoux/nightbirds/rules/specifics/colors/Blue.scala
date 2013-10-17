package fr.renoux.nightbirds.rules.specifics.colors

import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.Color
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.specifics.cards.Cop
import fr.renoux.nightbirds.rules.specifics.cards.DJ
import fr.renoux.nightbirds.rules.specifics.cards.Photograph
import fr.renoux.nightbirds.rules.specifics.cards.Skinhead
import fr.renoux.nightbirds.rules.generics.Callbacks



/** Bleu : photographe, flic, skinhead, ivrogne */
object Blue extends Color {
  override def getCards(c : Callbacks, b: Board, f: Family) = Set[Card](
    new Photograph(c, b, f),
    new Cop(c, b, f),
    new Skinhead(c, b, f),
    new DJ(c, b, f))
}

