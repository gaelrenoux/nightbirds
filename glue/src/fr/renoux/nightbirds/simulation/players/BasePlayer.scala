package fr.renoux.nightbirds.simulation.players

import fr.renoux.nightbirds.engine.Player
import fr.renoux.nightbirds.engine.Random
import fr.renoux.nightbirds.rules.cardtypes.Color
import fr.renoux.nightbirds.rules.state.Card
import fr.renoux.nightbirds.rules.state.District
import fr.renoux.nightbirds.rules.state.DistrictPublicState
import fr.renoux.nightbirds.rules.state.GamePublicState
import fr.renoux.nightbirds.rules.state.Neighbour
import fr.renoux.nightbirds.rules.state.LeftNeighbour
import fr.renoux.nightbirds.rules.state.RightNeighbour
import fr.renoux.nightbirds.rules.state.CardType

class BasePlayer extends Player {

  var myColor: Color = null

  val random = new Random

  override def initGame(gs: GamePublicState, myColor: Color) = {
    /* The base player does nothing specific */
  }

  /** Returns a card and a district to put it in */
  override def place(gs: GamePublicState, myHand: Set[CardType]): (CardType, DistrictPublicState) = {
    val card = random.pick(myHand)
    val smallestDistrictSize = gs.districts map { _.size } min
    val district = random.pick(gs.districts filter { _.size == smallestDistrictSize })

    (card, district)
  }

  /** Returns a choice to activate a card, and optionally a target to go wih it */
  override def activate(gs: GamePublicState, district: DistrictPublicState, column: Int): (Boolean, Option[Neighbour]) = {
    val districtSize = district.size
    if (districtSize == 1) (false, None)
    else if (column == 0) (true, Some(RightNeighbour))
    else if (column == districtSize - 1) (true, Some(LeftNeighbour))
    else if (random.nextBoolean) (true, Some(LeftNeighbour))
    else (true, Some(RightNeighbour))
  }

}