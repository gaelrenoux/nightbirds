package fr.renoux.nightbirds.simulation.players

import fr.renoux.nightbirds.rules.cardtypes.Color
import fr.renoux.nightbirds.rules.engine.ActivateTaxi
import fr.renoux.nightbirds.rules.engine.ActivateWithTarget
import fr.renoux.nightbirds.rules.engine.ActivateWithoutTarget
import fr.renoux.nightbirds.rules.engine.Activation
import fr.renoux.nightbirds.rules.engine.Pass
import fr.renoux.nightbirds.rules.engine.Random
import fr.renoux.nightbirds.rules.engine.WorksWithTaxi
import fr.renoux.nightbirds.rules.state.CardPublicState
import fr.renoux.nightbirds.rules.state.CardType
import fr.renoux.nightbirds.rules.state.DistrictPublicState
import fr.renoux.nightbirds.rules.state.GamePublicState
import fr.renoux.nightbirds.rules.state.LeftNeighbour
import fr.renoux.nightbirds.rules.state.PublicPosition
import fr.renoux.nightbirds.rules.state.RightNeighbour
import fr.renoux.nightbirds.rules.engine.WorksWithTarget
import fr.renoux.nightbirds.rules.state.Neighbour
import fr.renoux.nightbirds.rules.engine.WorksWithPrivateEye
import fr.renoux.nightbirds.rules.engine.ActivatePrivateEye

class BasePlayer(implicit val random: Random) extends AbstractPlayer {

  var myColor: Color = null

  override def initGame(gs: GamePublicState, myColor: Color) = {
    /* The base player does nothing specific */
  }

  /** Returns a card and a district to put it in */
  override def place(gs: GamePublicState, myHand: Set[CardType]): (CardType, DistrictPublicState) = {
    val card = random.pick(myHand)
    val smallestDistrictSize = gs.districts map { _.size } min
    val district = random.pick(gs.districts filter { _.size == smallestDistrictSize })
    (card, district)

    (random.pick(myHand), random.pick(gs.districts))
  }

  /** Returns a choice to activate a card, and optionally a target to go wih it */
  override def activate(gs: GamePublicState, card: CardPublicState, cardPosition: PublicPosition): Activation = {
    if (random.nextBoolean(0.2)) return Pass
    else super.activate(gs, card, cardPosition)
  }

  /** All cards without a target are activated */
  override def activateDefaultWithoutTarget(gs: GamePublicState, card: CardPublicState, cardPosition: PublicPosition) = ActivateWithoutTarget

  /** All cards with a target chose one at random, if any is available */
  override def activateDefaultWithTarget(gs: GamePublicState, card: CardPublicState, cardPosition: PublicPosition): WorksWithTarget = findRandomNeighbour(cardPosition) match {
    case None => Pass
    case Some(n) => ActivateWithTarget(n)
  }

  /** The taxi targets a random neighbour and goes to a random district */
  override def activateTaxi(gs: GamePublicState, card: CardPublicState, cardPosition: PublicPosition): WorksWithTaxi = {
    val district = cardPosition.district
    val districtSize = district.size
    val column = cardPosition.column

    val target = findRandomNeighbour(cardPosition)

    if (target.isEmpty) {
      Pass
    } else {
      val targetDistrict = random.pick(gs.districts filter { _.position != district.position })
      val targetSide = if (random.nextBoolean) LeftNeighbour else RightNeighbour
      ActivateTaxi(target.get, targetDistrict, targetSide)
    }
  }

  /** The private eye activates on two random unrevealed cards, if available */
  override def activatePrivateEye(gs: GamePublicState, card: CardPublicState, cardPosition: PublicPosition): WorksWithPrivateEye = {

    val districtAndIndexAndCardList = gs.districts flatMap { district =>
      val existing = district.cards.zipWithIndex flatMap { optionAndIndex => optionAndIndex._1 map { optionAndIndex._2 -> _ } }
      val notRevealed = existing filter { _._2.cardType.isEmpty }
      notRevealed map { indexAndCard => (district, indexAndCard._1, indexAndCard._2) }
    } filter { districtAndIndexAndCard =>
      /* not the current card, thank you */
     districtAndIndexAndCard._1 != cardPosition.district || districtAndIndexAndCard._2 != cardPosition.column
    }
    
    val targets = random.pick(districtAndIndexAndCardList, 2) map {districtAndIndexAndCard =>
      PublicPosition(districtAndIndexAndCard._1, districtAndIndexAndCard._2)
    }
    val targetsCount = targets.size
    
    if (targetsCount == 0) {
      Pass
    } else if (targetsCount==1) {
      ActivatePrivateEye(targets(0), None)
    } else {
      ActivatePrivateEye(targets(0), Some(targets(1)))
    }
    
  }

  /** Helper method : finds a random available neighbour */
  private def findRandomNeighbour(cardPosition: PublicPosition): Option[Neighbour] = {
    val neighbourFirstTry = if (random.nextBoolean) LeftNeighbour else RightNeighbour
    val cardAtFirstPosition = neighbourFirstTry(cardPosition) flatMap { _.get }
    if (cardAtFirstPosition isDefined) {
      return Some(neighbourFirstTry)
    }

    val neighbourSecondTry = neighbourFirstTry.opposite
    val cardAtSecondPosition = neighbourSecondTry(cardPosition) flatMap { _.get }
    if (cardAtSecondPosition isDefined) {
      return Some(neighbourSecondTry)
    }
    return None
  }

}