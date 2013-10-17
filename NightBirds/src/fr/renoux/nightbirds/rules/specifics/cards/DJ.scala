package fr.renoux.nightbirds.rules.specifics.cards

import fr.renoux.nightbirds.rules.generics.WithoutTarget
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.Cash
import fr.renoux.nightbirds.rules.generics.Callbacks

/** DJ : earns 1, everyone in the district loses two */
class DJ(c: Callbacks, b: Board, f: Family) extends Card(c, b, f) with WithoutTarget {
  override def doProceed() = {
    store(Cash(1))
    b.districtOf(this).cards.filter { _ != this }.foreach { _.take(Cash(1)) }
  }
}