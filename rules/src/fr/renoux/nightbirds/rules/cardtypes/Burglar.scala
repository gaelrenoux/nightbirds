package fr.renoux.nightbirds.rules.cardtypes

import fr.renoux.nightbirds.rules.Rules
import fr.renoux.nightbirds.rules.state.Card
import fr.renoux.nightbirds.rules.state.CardType
import fr.renoux.nightbirds.rules.state.Cash
import fr.renoux.nightbirds.rules.state.Family
import fr.renoux.nightbirds.rules.state.Illegal
import fr.renoux.nightbirds.rules.state.WithoutTarget

object BurglarType extends CardType(Illegal)
class Burglar(f: Family) extends Card(f)(BurglarType) with WithoutTarget {

  private var _disturbanceCount = 0

  override def activate() = {
    val loot = Cash(Rules.BurglarEarnings) - Cash(_disturbanceCount * Rules.BurglarLossOnDisturbance)
    store(loot.remaining)
  }

  override def isTargeted(origin: Card) = {
    if (tapped) {
      takeIfAvailable(Cash(Rules.BurglarLossOnDisturbance))
    } else {
      _disturbanceCount = _disturbanceCount + 1
    }
  }

  override def sleep() {
    super.sleep()
    _disturbanceCount = 0
  }

}