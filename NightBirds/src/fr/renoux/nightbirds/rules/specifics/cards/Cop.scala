package fr.renoux.nightbirds.rules.specifics.cards

import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.generics.WithTarget
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.Callbacks


class Cop(c: Callbacks, b: Board, f: Family) extends Card(c, b, f) with WithTarget {

  override def doTargeted(source: Card) = {
    if (source.isReprehensible) {
      source.confiscate()
      f.hold(source)
      false
    }
    true
  }

  override def doWitness(source: Card) = {
    if (source.isReprehensible) {
      source.confiscate()
      f.hold(source)
    }
  }

  override def doProceed(target: Card) = {
    if (target.isReprehensible && target.hasPlayed) {
      target.confiscate()
      f.hold(target)
    }
  }
}