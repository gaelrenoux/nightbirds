package old.fr.renoux.nightbirds.rules.cards

import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.generics.WithTarget
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.Guts
import fr.renoux.nightbirds.rules.generics.Illegal
import fr.renoux.nightbirds.rules.generics.Cash
import fr.renoux.nightbirds.rules.generics.Callbacks

/** Stores the difference between his guts and the target's guts. Get hurt instead if the target is stronger or equal. */
class Thug(c: Callbacks, b: Board, f: Family) extends Card(c, b, f, Guts(3), Illegal) with WithTarget {
  override def doProceed(target: Card) = {
    if (target.guts < this.guts) {
      store(target.take(Cash(this.guts.value - target.guts.value)))
    } else {
      hit()
    }
  }
}