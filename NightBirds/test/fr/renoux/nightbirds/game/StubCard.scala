package fr.renoux.nightbirds.game

import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Callbacks
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.generics.Guts
import fr.renoux.nightbirds.rules.generics.Illegal
import fr.renoux.nightbirds.rules.generics.Legality
import fr.renoux.nightbirds.rules.generics.WithTarget
import fr.renoux.nightbirds.rules.generics.WithTargetAndMore
import fr.renoux.nightbirds.rules.generics.WithoutTarget
import fr.renoux.nightbirds.rules.generics.Neighbours

class StubCardWithoutTarget(c: Callbacks, b: Board, f: Family, g: Guts = Guts(1), illegal: Legality = Illegal)
  extends Card(c, b, f, g, Illegal) with WithoutTarget {

  def doProceed = {}
  
}

class StubCardWithTarget(c: Callbacks, b: Board, f: Family, g: Guts = Guts(1), illegal: Legality = Illegal)
  extends Card(c, b, f, g, Illegal) with WithTarget {

  def doProceed(target: Card) = {}
  
}

class StubCardWithTargetAndMore(c: Callbacks, b: Board, f: Family, g: Guts = Guts(1), illegal: Legality = Illegal)
  extends Card(c, b, f, g, Illegal) with WithTargetAndMore[Any] {

  def doProceed(target : Card, more: Any) = {}
  
}