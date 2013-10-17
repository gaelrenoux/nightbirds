package fr.renoux.nightbirds.rules.specifics.cards

import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.WithTarget
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.generics.Cash
import fr.renoux.nightbirds.rules.generics.WithoutTarget
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.Guts
import fr.renoux.nightbirds.rules.generics.Illegal
import fr.renoux.nightbirds.rules.generics.Callbacks
import fr.renoux.nightbirds.rules.generics.WithTargetAndMore

/**  */
class PrivateEye(c: Callbacks, b: Board, f: Family) extends Card(c, b, f) with WithTargetAndMore[Card] {

  def doProceed(target1: Option[Card], target1Watcher: { def see: Card => Unit }, target2: Option[Card], target2Watcher: { def see: Card => Unit }) = {
    target1.foreach { target1Watcher.see(_) }
    target2.foreach { target2Watcher.see(_) }
  }

  override def doProceed(target1: Card, target2: Card) {

  }
}


