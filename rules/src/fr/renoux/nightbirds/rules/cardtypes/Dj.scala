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

object DjType extends CardType(Legal)
class Dj(f: Family) extends Card(f)(DjType) with WithoutTarget {

  override def specificActivate() = {
    store(Cash(Rules.DjEarnings))
    for (c <- this.position.get.district.cards) {
      if (c.family != family) c.take(Cash(Rules.DjPrice))
    }
  }

}