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

  private var _earned = Cash.Zero

  protected override def specificActivate() = {
    val loot = Cash(Rules.BurglarEarnings) - Cash(_disturbanceCount * Rules.BurglarLossOnDisturbance)
    _earned = loot.remaining
    store(_earned)
  }

  protected override def specificIsTargeted(origin: Card) = {
    if (tapped) {
      val t = _earned - Cash(Rules.BurglarLossOnDisturbance)
      _earned = t.remaining
      takeIfAvailable(t.subtracted)
    } else {
      _disturbanceCount = _disturbanceCount + 1
    }
  }

  override def sleep() {
    super.sleep()
    _disturbanceCount = 0
  }

}