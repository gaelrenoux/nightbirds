package fr.renoux.nightbirds.rules.specifics.cards

import fr.renoux.nightbirds.rules.generics.WithoutTarget
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Cash
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.Callbacks

/** Cook : earns two */
class Cook(c: Callbacks, b: Board, f: Family) extends Card(c, b, f) with WithoutTarget {
  
  override def doProceed() = {
    store(Cash(2))
  }
  
}