package fr.renoux.nightbirds.rules.cardtypes

import fr.renoux.nightbirds.rules.state.Card
import fr.renoux.nightbirds.rules.state.CardType
import fr.renoux.nightbirds.rules.state.District
import fr.renoux.nightbirds.rules.state.Family
import fr.renoux.nightbirds.rules.state.GameState
import fr.renoux.nightbirds.rules.state.Illegal
import fr.renoux.nightbirds.rules.state.Neighbour
import fr.renoux.nightbirds.rules.state.WithTarget
import fr.renoux.nightbirds.rules.state.WithoutTarget
import fr.renoux.nightbirds.rules.state.LeftNeighbour
import fr.renoux.nightbirds.rules.state.Legal

object PrivateEyeType extends CardType(Legal)

/** Very very weird card. Doesn not have a classic activation. Everything it does is done within the game engine. */
class PrivateEye(f: Family) extends Card(f)(PrivateEyeType) {

  def activate(gs: GameState): Unit = {
    reveal()
    tap()
  }

}