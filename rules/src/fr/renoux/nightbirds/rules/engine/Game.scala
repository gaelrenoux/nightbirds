package fr.renoux.nightbirds.rules.engine

import fr.renoux.nightbirds.rules.Rules
import fr.renoux.nightbirds.rules.cardtypes.Color
import fr.renoux.nightbirds.rules.state.Card
import fr.renoux.nightbirds.rules.state.District
import fr.renoux.nightbirds.rules.state.Family
import fr.renoux.nightbirds.rules.state.GameState
import fr.renoux.nightbirds.rules.state.LeftNeighbour
import fr.renoux.nightbirds.rules.state.Position
import fr.renoux.nightbirds.rules.state.RightNeighbour
import fr.renoux.nightbirds.rules.state.WithTarget
import fr.renoux.nightbirds.rules.state.WithoutTarget
import fr.renoux.nightbirds.utils.Logger
import fr.renoux.nightbirds.rules.state.Neighbour
import fr.renoux.nightbirds.rules.cardtypes.Taxi
import fr.renoux.nightbirds.rules.cardtypes.PrivateEye
import fr.renoux.nightbirds.rules.state.PublicPosition

class Game(playersInput: Player*)(implicit val random: Random) {

  /** Number of players */
  val playersCount = playersInput.size

  /** Players, ordered in a random fashion */
  val players = random.shuffle(playersInput)

  /** Colors, in the same order as players */
  val colors = random.pick(Color.all.toSeq, playersCount)

  if (colors.size < playersCount) {
    throw new IllegalArgumentException("Too many players !")
  }

  /** Number of turns per step of a round (two steps per round : placement and activation) */
  val turnCount = playersCount * Rules.CardsCountPerPlayer

  val affectations = colors.zip(players).toMap

  val families = colors.map { new Family(_) }.toIndexedSeq

  val gameState = new GameState(families)

  Logger.debug(gameState.toString)
  Logger.debug(affectations.toString)

  /** Play a game, returns the score for each color from the winner to the loser */
  def play() = {

    GameLogger.gameStarts(colors)

    players.foreach { p =>
      val hisAffectation = affectations.filter(_._2 == p)
      val hisColor = hisAffectation.head._1
      p.initGame(gameState.public, hisColor)
    }

    for (roundNum <- 0 until playersCount) {
      /* Play the round number roundNum. Round number is zero-based*/
      GameLogger.roundStarts(families(roundNum).color)

      /* Card placement ! */
      for (turnNum <- 0 until turnCount) {
        doPlacement(roundNum, turnNum)
      }

      GameLogger.roundPlacementDone(gameState)

      /* Activation ! */
      var maxActivations = gameState.districts map { _.cards.length } sum

      var next = findNextActivation()
      while (next.isDefined) {
        doActivation(next.get)
        next = findNextActivation()

        if (maxActivations < 0) {
          throw new IllegalStateException
        } else {
          maxActivations = maxActivations - 1
        }
      }

      GameLogger.roundActivationDone(gameState)

      gameState.endRound()
      GameLogger.roundEnds(gameState)
    }

    GameLogger.gameEnds(gameState)
    gameState.toScore
  }

  /** First stage of the round : placing cards */
  private def doPlacement(roundNum: Int, turnNum: Int) = {
    val playerIndex = (turnNum + roundNum) % playersCount
    val player = players(playerIndex)
    val family = families(playerIndex)

    val (cardType, districtPublicState) = player.place(gameState.public, family.hand map { _.cardType })

    val cardOption = family.discard(cardType)
    cardOption match {
      case None => throw new CheaterException("Player " + player + " : card " + cardType + " is not available")
      case Some(card) =>
        card.place(gameState.districts(districtPublicState.position))
        GameLogger.placed(card)
    }

  }

  private def findNextActivation() = {
    val candidates = gameState.districts map { _.nextActivationCandidate } flatten

    if (candidates.size == 0) None
    else Some(candidates minBy { _._2 } _1)
  }

  /** Second stage of the round : activating card at the given position */
  private def doActivation(card: Card): Unit = {
    val position = card.position.get
    val player = affectations(card.family.color)

    card.reveal()
    if (!card.canAct) return //shouldn't happen, but who knows

    val activation = player.activate(gameState.public, card.public, position.public)

    (card, activation) match {
      case (_, Pass) => pass(card)
      case (wot: WithoutTarget, ActivateWithoutTarget) => activateWithoutTarget(wot)
      case (wt: WithTarget, ActivateWithTarget(neighbour)) => activateWithTarget(wt, neighbour)
      case (taxi: Taxi, ActivateTaxi(neighbour, district, targetSide)) => activateTaxi(taxi, neighbour, gameState.districts(district.position), targetSide)
      case (eye: PrivateEye, ActivatePrivateEye(target1, target2)) => activatePrivateEye(eye, target1, target2)
      case _ => throw new CheaterException("Incompatible activation " + activation + " for card " + card)
    }
  }

  /** Do not activate the card */
  private def pass(card: Card) = {
    card.pass()
    GameLogger.declinedActivation(card)
  }

  /** Activation of a card at a certain position. No target. */
  private def activateWithoutTarget(card: WithoutTarget) = {
    card.activate(gameState)
    doWitnesses(card, None)

    GameLogger.activated(card)
  }

  /**
   * Activation of a card at a certain position, on a target at a certain position.
   *  @param card The activating card
   * @param cardPosition The activating card position on the board
   * @param neighbour The targeted neighbour (left or right)
   */
  private def activateWithTarget(card: WithTarget, neighbour: Neighbour) = {
    val cardPosition = card.position.get
    val targetPosition = neighbour(cardPosition).getOrElse {
      throw new CheaterException("Position " + neighbour + " doesn't exist for " + card)
    }
    val target = targetPosition.get getOrElse {
      throw new CheaterException("No target at position " + neighbour + " for " + card)
    }

    val targetedPlayer = affectations(target.family.color)
    target.isTargeted(card)

    if (target.hasTargetedReaction && target.canAct && targetedPlayer.reactToTargeted(gameState.public, targetPosition.public, cardPosition.public)) {
      target.reactToTargeted(card)
    }

    /* check if the card can still act */
    if (card.canAct) {
      card.activate(target, gameState)
      doWitnesses(card, Some(target))
    }

    GameLogger.activated(card, target)
  }

  /** Activation of a taxi, with special parameters. */
  private def activateTaxi(taxi: Taxi, neighbour: Neighbour, targetDistrict: District, targetSide: Neighbour) = {
    val taxiPosition = taxi.position.get
    if (targetDistrict.position == taxiPosition.district.position) {
      throw new CheaterException("Taxi " + taxi + " wants to go the the same district " + targetDistrict)
    }

    val targetPosition = neighbour(taxiPosition).getOrElse {
      throw new CheaterException("Position " + neighbour + " doesn't exist for taxi " + taxi)
    }
    val targetOption = targetPosition.get
    val target = targetOption.getOrElse {
      throw new CheaterException("No target at position " + neighbour + " for taxi " + taxi)
    }

    val targetedPlayer = affectations(target.family.color)
    target.isTargeted(taxi)

    if (target.hasTargetedReaction && target.canAct && targetedPlayer.reactToTargeted(gameState.public, targetPosition.public, taxiPosition.public)) {
      target.reactToTargeted(taxi)
    }

    /* check if the card can still act */
    if (taxi.canAct) {
      taxi.activate(target, targetDistrict, targetSide, gameState)
      doWitnesses(taxi, Some(target))
    }

    GameLogger.activated(taxi, target)
  }

  /** Activation of a private eye, not the usual stuff. */
  private def activatePrivateEye(eye: PrivateEye, target1: PublicPosition, target2: PublicPosition) = {
    val player = affectations(eye.family.color)

    List(target1, target2) foreach { target =>
      val card = gameState.districts(target.district.position)(target.column) getOrElse {
        throw new CheaterException("Position " + target + " doesn't exist for private eye " + eye)
      }
      player.see(gameState.public, target, card.cardType)
    }
  }

  /** Work out the witness stuff */
  private def doWitnesses(card: Card, target: Option[Card]) = if (!card.out) {
    val cardPosition = card.position.get
    val neighbours = cardPosition.neighbours map { np => np.get } flatten
    val witnesses = neighbours filter { !target.contains(_) }

    witnesses foreach { _.witness(card) }

    witnesses foreach { neighbour =>
      /* the first neighbour can take out this card, so check each time */
      if (!card.out) {
        val neighbourPlayer = affectations(neighbour.family.color)
        if (neighbour.hasWitnessReaction && neighbour.canAct && neighbourPlayer.reactToWitness(gameState.public, neighbour.position.get.public, cardPosition.public)) {
          neighbour.reactToWitness(card)
        }
      }
    }

  }

}