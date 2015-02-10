package fr.renoux.nightbirds.rules.cardtypes

import fr.renoux.nightbirds.rules.Rules
import fr.renoux.nightbirds.rules.state.Card
import fr.renoux.nightbirds.rules.state.Cash
import fr.renoux.nightbirds.rules.state.Family
import fr.renoux.nightbirds.rules.state.GameState
import fr.renoux.nightbirds.rules.state.Legal
import fr.renoux.nightbirds.rules.state.WithTarget
import fr.renoux.nightbirds.rules.state.WithoutTarget
import fr.renoux.nightbirds.rules.state.Illegal
import fr.renoux.nightbirds.rules.state.CardType

object DealerType extends CardType(Illegal)
class Dealer(f: Family) extends Card(f)(DealerType) with WithTarget {

  override def specificActivate(target: Card) = {
    val taking = if (target.legality.legal) Rules.DealerLegalEarnings else Rules.DealerIllegalEarnings
    val transaction = target.take(Cash(taking))
    store(transaction.subtracted)
  }
  

}