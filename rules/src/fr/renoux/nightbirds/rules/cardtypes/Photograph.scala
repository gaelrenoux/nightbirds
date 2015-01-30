package fr.renoux.nightbirds.rules.cardtypes

import fr.renoux.nightbirds.rules.Rules
import fr.renoux.nightbirds.rules.state.Card
import fr.renoux.nightbirds.rules.state.CardType
import fr.renoux.nightbirds.rules.state.Cash
import fr.renoux.nightbirds.rules.state.Family
import fr.renoux.nightbirds.rules.state.Illegal
import fr.renoux.nightbirds.rules.state.WithoutTarget
import fr.renoux.nightbirds.rules.state.Legal
import fr.renoux.nightbirds.rules.state.GameState
import fr.renoux.nightbirds.rules.state.LeftNeighbour

object PhotographType extends CardType(Legal)
class Photograph(f: Family) extends Card(f)(PhotographType) with WithoutTarget {

  override def activate(gs : GameState) = {
    val position = gs.find(this).get
    
    val leftGuyTapped = position.left.map(_.get).map(_.tapped).getOrElse(false)
    if (leftGuyTapped) {
      store(Cash(Rules.PhotographEarnings))
    }
    
    val rightGuyTapped = position.right.map(_.get).map(_.tapped).getOrElse(false)
    if (rightGuyTapped) {
      store(Cash(Rules.PhotographEarnings))
    }
  }

  override val hasWitnessEffect = true
  
  override def witness(origin: Card) = {
    if (tapped) {
      store(Cash(Rules.PhotographEarnings))
    }
    /* do nothing if not tapped, will happen when activated */
  }

}