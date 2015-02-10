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
import fr.renoux.nightbirds.rules.state.WithTarget

object CopType extends CardType(Legal)
class Cop(f: Family) extends Card(f)(CopType) with WithTarget {

  override def specificActivate(target: Card) = {
    if (target.legality.illegal) {
      val t = target.take(Cash(Rules.CopEarnings))
      store(t.subtracted)
    }
    /* nothing happens if the target is legal */
  }

  override val hasTargetedReaction = true

  override def specificReactToTargeted(origin: Card) = {
    if (origin.legality.illegal) {
      hold(origin)
    }
  }

  override val hasWitnessReaction = true

  override def specificReactToWitness(origin: Card) = {
    if (origin.legality.illegal) {
      hold(origin)
    }
  }

  /** Hold this card in an owner's jail. TODO Simplified version for now : always pay two */
  private def hold(delinquant: Card) = {
    delinquant.takeIfAvailable(Cash.Infinity)
    val t = delinquant.family.take(Cash(Rules.CopBail))
    this.family.store(t.subtracted)
    delinquant.takeOut()
  }

}