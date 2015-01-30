package fr.renoux.nightbirds.rules.cardtypes

import fr.renoux.nightbirds.rules.state.Legal
import fr.renoux.nightbirds.rules.state.WithoutTarget
import fr.renoux.nightbirds.rules.state.Card
import fr.renoux.nightbirds.rules.state.GameState
import fr.renoux.nightbirds.rules.state.Cash
import fr.renoux.nightbirds.rules.Rules
import fr.renoux.nightbirds.rules.state.Family
import fr.renoux.nightbirds.rules.state.Transaction
import fr.renoux.nightbirds.rules.state.CardType

object BurglarType extends CardType(Legal)
class Burglar(f: Family) extends Card(f)(BurglarType) with WithoutTarget {

  private var _disturbanceCount = 0

  override def activate() = {
    val loot = Cash(Rules.BurglarEarnings) - Cash(_disturbanceCount * Rules.BurglarLossOnDisturbance)
    store(loot.remaining)
  }

  override val hasTargetedEffect = true

  override def react(origin: Card) = {
    if (cash.isZero) {
      _disturbanceCount = _disturbanceCount + 1
    } else {
      takeIfAvailable(Cash(Rules.BurglarLossOnDisturbance))
    }
  }

  override def sleep() {
    super.sleep()
    _disturbanceCount = 0
  }

}