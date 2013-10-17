package fr.renoux.nightbirds.rules.specifics.colors

import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Callbacks
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.Color
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.specifics.cards.Bum
import fr.renoux.nightbirds.rules.specifics.cards.PrivateEye
import fr.renoux.nightbirds.rules.specifics.cards.Thug
import fr.renoux.nightbirds.rules.specifics.cards.Whore


/** Noir : prostitué, privé, petite frappe, clochard */
object Black extends Color {
  override def getCards(c : Callbacks, b: Board, f: Family) = Set[Card](
    new Whore(c, b, f),
    new PrivateEye(c, b, f),
    new Thug(c, b, f),
    new Bum(c, b, f))
}