package fr.renoux.nightbirds.rules.cardtypes

import fr.renoux.nightbirds.rules.state.Card
import fr.renoux.nightbirds.rules.state.CardType
import fr.renoux.nightbirds.rules.state.District
import fr.renoux.nightbirds.rules.state.Family
import fr.renoux.nightbirds.rules.state.GameState
import fr.renoux.nightbirds.rules.state.Illegal
import fr.renoux.nightbirds.rules.state.Neighbour
import fr.renoux.nightbirds.rules.state.WithTarget
import fr.renoux.nightbirds.rules.state.WithoutTarget
import fr.renoux.nightbirds.rules.state.LeftNeighbour

object TaxiType extends CardType(Illegal)
class Taxi(f: Family) extends Card(f)(TaxiType) {

  def activate(target: Card, district: District, targetSide: Neighbour, gs: GameState): Unit = {
    reveal()
    specificActivate(target, district, targetSide, gs)
    tap()
  }

  protected def specificActivate(target: Card, district: District, targetSide: Neighbour, gs: GameState): Unit = specificActivate(target, district, targetSide)

  protected def specificActivate(target: Card, district: District, targetSide: Neighbour): Unit = {
    if (targetSide == LeftNeighbour) {
      target.place(district)
      this.place(district)
    } else {
      this.place(district)
      target.place(district)
    }
  }

}