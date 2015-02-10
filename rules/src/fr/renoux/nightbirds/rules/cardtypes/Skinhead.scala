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

object SkinheadType extends CardType(Illegal)
class Skinhead(f: Family) extends Card(f)(SkinheadType) with WithTarget {

  override def specificActivate(target: Card) = {
    store(target.takeIfAvailable(Cash.Infinity).subtracted)
    target.hit()
  }
  
  override val hasTargetedReaction = true
  
  override def specificReactToTargeted(origin : Card) = {
    this.reveal()
    store(origin.takeIfAvailable(Cash.Infinity).subtracted)
    origin.hit()
  }
}