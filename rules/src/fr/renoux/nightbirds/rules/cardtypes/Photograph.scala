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

  protected override def specificActivate() = {
    for (i <- 0 until futureWitnessEffects) {
      store(Cash(Rules.PhotographEarnings))
    }
    futureWitnessEffects = 0
  }
  
  private var futureWitnessEffects = 0
  
  protected override def specificWitness(origin: Card) = {
    if (revealed) {
      store(Cash(Rules.PhotographEarnings))
    } else {
      futureWitnessEffects = futureWitnessEffects + 1
    }
    /* do nothing if not tapped, will happen when activated */
  }
  
  override def sleep() = {
    futureWitnessEffects = 0
    super.sleep()
  }

}