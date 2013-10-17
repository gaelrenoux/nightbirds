package fr.renoux.nightbirds.rules.specifics.cards

import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Callbacks
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.generics.Guts
import fr.renoux.nightbirds.rules.generics.Illegal
import fr.renoux.nightbirds.rules.generics.WithTarget

/** Hits the target. Get hurt instead if the target is stronger or equal. */
class Skinhead(c: Callbacks, b: Board, f: Family) extends Card(c, b, f, Guts(4), Illegal) with WithTarget {
  override def doProceed(target: Card) = {
    if (target.guts < this.guts) {
      target.hit()
    } else {
      this.hit()
    }
  }
}
