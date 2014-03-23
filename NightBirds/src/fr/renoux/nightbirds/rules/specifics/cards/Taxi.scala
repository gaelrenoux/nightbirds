package fr.renoux.nightbirds.rules.specifics.cards

import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Callbacks
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.District
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.generics.WithTargetWithInfo

/** Move himself and another at the end of another district */
class Taxi(c: Callbacks, b: Board, f: Family) extends Card(c, b, f) with WithTargetWithInfo[(District, Boolean)] {

  def doProceed(target: Card, more: (District, Boolean)) = {
    val (district, taxiLast) = more
    if (taxiLast) {
      board.moveCardTo(target, district)
      board.moveCardTo(this, district)
    } else {
      board.moveCardTo(this, district)     
      board.moveCardTo(target, district) 
    }
  }
}