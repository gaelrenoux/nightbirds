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

object BumType extends CardType(Legal)
class Bum(f: Family) extends Card(f)(BumType) with WithoutTarget {

  override def activate() = {
    _revealed = true
    store(Cash(Rules.BumEarnings))
  }

  override def take(amount: Cash) = Transaction(cash, Cash.Zero, amount, false)

}