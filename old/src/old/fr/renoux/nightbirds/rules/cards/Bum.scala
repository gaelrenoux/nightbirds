package old.fr.renoux.nightbirds.rules.cards

import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.elements.Family
import fr.renoux.nightbirds.rules.generics.WithoutTarget
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.Cash
import fr.renoux.nightbirds.rules.generics.Callbacks

/** Bum : earns one, can't give anything */
class Bum(c: Callbacks, b: Board, f: Family) extends Card(c, b, f) with WithoutTarget {
  
  override def doProceed() = {
    store(Cash(1))
  }

  override def take(amount: Cash) = Cash(0)
}