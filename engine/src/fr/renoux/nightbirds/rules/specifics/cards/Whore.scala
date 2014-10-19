package fr.renoux.nightbirds.rules.specifics.cards

import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Callbacks
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.Cash
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.generics.Guts
import fr.renoux.nightbirds.rules.generics.Illegal
import fr.renoux.nightbirds.rules.generics.WithTarget

/** Takes two from the target */
class Whore(c: Callbacks, b: Board, f: Family) extends Card(c, b, f, Guts(0), Illegal) with WithTarget {
  override def doProceed(target: Card) = {
    store(target.take(Cash(2)))
  }
}