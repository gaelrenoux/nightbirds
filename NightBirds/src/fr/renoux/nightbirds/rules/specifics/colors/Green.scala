package fr.renoux.nightbirds.rules.specifics.colors

import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.Color
import fr.renoux.nightbirds.rules.specifics.cards.Dealer
import fr.renoux.nightbirds.rules.specifics.cards.PrivateEye
import fr.renoux.nightbirds.rules.specifics.cards.Taxi
import fr.renoux.nightbirds.rules.specifics.cards.Burglar
import fr.renoux.nightbirds.rules.generics.Callbacks

/** Vert : Taxi, Dealeuse, Privé, Cambrioleur*/
object Green extends Color {
  override def getCards(c : Callbacks, b: Board, f: Family) = Set[Card](
    new Taxi(c, b, f),
    new Dealer(c, b, f),
    new PrivateEye(c, b, f),
    new Burglar(c, b, f))
}