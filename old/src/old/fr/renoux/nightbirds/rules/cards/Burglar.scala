package old.fr.renoux.nightbirds.rules.cards

import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.WithTarget
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.generics.WithoutTarget
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.Cash
import fr.renoux.nightbirds.rules.generics.WithTarget
import fr.renoux.nightbirds.rules.generics.Callbacks

/** Burglar : earns four, minus one per action he is the target of */
class Burglar(c: Callbacks, b: Board, f: Family) extends Card(c, b, f) with WithoutTarget {

  private var annoyed = 0

  override def doProceed() = {
    store(Cash(4 - annoyed))
    annoyed = 0
  }

  override def doMandatoryTargeted(source: Card) = {
    if (cash > Cash(0)) {
      take(Cash(1))
    } else {
      annoyed = annoyed + 1
    }
    
    true
  }

  override def doReset() = {
    annoyed = 0
  }
}