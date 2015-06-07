package fr.renoux.nightbirds.rules.engine

import fr.renoux.nightbirds.rules.Rules
import fr.renoux.nightbirds.rules.cardtypes.Color
import fr.renoux.nightbirds.rules.state.Card
import fr.renoux.nightbirds.rules.state.District
import fr.renoux.nightbirds.rules.state.Family
import fr.renoux.nightbirds.rules.state.GameState
import fr.renoux.nightbirds.rules.state.LeftNeighbour
import fr.renoux.nightbirds.rules.state.MissingCard
import fr.renoux.nightbirds.rules.state.Position
import fr.renoux.nightbirds.rules.state.RightNeighbour
import fr.renoux.nightbirds.rules.state.WithTarget
import fr.renoux.nightbirds.rules.state.WithoutTarget
import fr.renoux.nightbirds.utils.Logger
import fr.renoux.nightbirds.rules.state.Neighbour
import fr.renoux.nightbirds.rules.cardtypes.Taxi

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
        doActivation(next.get._1, next.get._2)
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
    val districtsAndIndex = gameState.districts.view map { d => d -> d.cards.indexWhere(_.canBeActivated) } filter { _._2 != -1 }
    if (districtsAndIndex.size == 0) None
    else Some(districtsAndIndex minBy { di => (di._2, di._1.position) })
    //minBy { di => 100 * di._2 + di._1.position }
  }

  /** Second stage of the round : activating card at the given position */
  private def doActivation(district: District, column: Int): Unit = {
    val position = Position(district, column)
    val card = position.get
    val player = affectations(card.family.color)

    card.reveal()
    if (!card.canAct) return //shouldn't happen, but who knows

    val activation = player.activate(gameState.public, card.public, position.public)

    (card, activation) match {
      case (_, Pass) => pass(card)
      case (wot: WithoutTarget, ActivateWithoutTarget) => activateWithoutTarget(wot, position)
      case (wt: WithTarget, ActivateWithTarget(neighbour)) => activateWithTarget(wt, position, neighbour)
      case (taxi: Taxi, ActivateTaxi(neighbour, district, targetSide)) => activateTaxi(taxi, position, neighbour, gameState.districts(district.position), targetSide)
      case _ => throw new CheaterException("Incompatible activation " + activation + " for card " + card)
    }
  }

  /** Do not activate the card */
  private def pass(card: Card) = {
    card.pass()
    GameLogger.declinedActivation(card)
  }

  /** Activation of a card at a certain position. No target. */
  private def activateWithoutTarget(card: WithoutTarget, cardPosition: Position) = {
    card.activate(gameState)
    doWitnesses(card, cardPosition, None)

    GameLogger.activated(card)
  }

  /**
   * Activation of a card at a certain position, on a target at a certain position.
   *  @param card The activating card
   * @param cardPosition The activating card position on the board
   * @param neighbour The targeted neighbour (left or right)
   */
  private def activateWithTarget(card: WithTarget, cardPosition: Position, neighbour: Neighbour) = {
    val (targetPosition, target) = neighbour(cardPosition) map { p => (p, p.get) } filter { !_._2.isInstanceOf[MissingCard] } getOrElse {
      throw new CheaterException("Target chosen is " + neighbour(cardPosition).map(_.get) + " at position " + neighbour + " by card " + card)
    }

    val targetedPlayer = affectations(target.family.color)
    target.isTargeted(card)

    if (target.hasTargetedReaction && target.canAct && targetedPlayer.reactToTargeted(gameState.public, targetPosition.public, cardPosition.public)) {
      target.reactToTargeted(card)
    }

    /* check if the card can still act */
    if (card.canAct) {
      card.activate(target, gameState)
      doWitnesses(card, cardPosition, Some(target))
    }

    GameLogger.activated(card, target)
  }

  /** Activation of a card at a certain position, on a target at a certain position. */
  private def activateTaxi(taxi: Taxi, taxiPosition: Position, neighbour: Neighbour, targetDistrict: District, targetSide: Neighbour) = {
    if (targetDistrict.position == taxiPosition.district.position) {
      throw new CheaterException("Taxi " + taxi + " wants to go the the same district " + targetDistrict)
    }

    val (targetPosition, target) = neighbour(taxiPosition) map { p => (p, p.get) } filter { !_._2.isInstanceOf[MissingCard] } getOrElse {
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
      doWitnesses(taxi, taxiPosition, Some(target))
    }

    GameLogger.activated(taxi, target)
  }

  /** Work out the witness stuff */
  private def doWitnesses(card: Card, cardPosition: Position, target: Option[Card]) = if (!card.out) {
    val witnessesAndPosition = cardPosition.neighbours.view map { np => np.get -> np } filter { c =>
      val (neighbour, neighbourPosition) = c
      !target.contains(neighbour) && !neighbour.isInstanceOf[MissingCard]
    } force

    witnessesAndPosition foreach { _._1.witness(card) }

    witnessesAndPosition foreach { c =>
      val (neighbour, neighbourPosition) = c
      /* the first neighbour can take out this card, so check each time */
      if (!card.out) {
        val neighbourPlayer = affectations(neighbour.family.color)
        if (neighbour.hasWitnessReaction && neighbour.canAct && neighbourPlayer.reactToWitness(gameState.public, neighbourPosition.public, cardPosition.public)) {
          neighbour.reactToWitness(card)
        }
      }
    }

  }

}