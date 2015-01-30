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

object CookType extends CardType(Legal)
class Cook(f: Family) extends Card(f)(CookType) with WithoutTarget {

  override def activate() = {
    store(Cash(Rules.CookEarnings))
  }

}