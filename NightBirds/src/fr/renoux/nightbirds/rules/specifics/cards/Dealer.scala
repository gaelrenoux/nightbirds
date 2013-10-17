package fr.renoux.nightbirds.rules.specifics.cards

import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.generics.WithTarget
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.Guts
import fr.renoux.nightbirds.rules.generics.Illegal
import fr.renoux.nightbirds.rules.generics.Callbacks

/** Stores the drug value of the target */
class Dealer(c: Callbacks, b: Board, f: Family) extends Card(c, b, f, Guts(4), Illegal) with WithTarget {
  override def doProceed(target: Card) = {
    store(target.take(target.addiction))
  }
}