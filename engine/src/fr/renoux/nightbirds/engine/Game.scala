package fr.renoux.nightbirds.engine

import fr.renoux.nightbirds.rules.Rules
import fr.renoux.nightbirds.rules.cardtypes.Color
import fr.renoux.nightbirds.rules.state.Family
import fr.renoux.nightbirds.rules.state.GameState
import scala.annotation.tailrec
import fr.renoux.nightbirds.rules.state.District
import fr.renoux.nightbirds.rules.state.WithoutTarget
import fr.renoux.nightbirds.rules.state.WithTarget
import fr.renoux.nightbirds.rules.state.RightNeighbour
import fr.renoux.nightbirds.rules.state.LeftNeighbour
import fr.renoux.nightbirds.rules.state.Card
import fr.renoux.nightbirds.rules.state.Position

class Game(playersInput: Player*) {

  val random = new Random

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

  println(gameState)
  println(players)
  println(affectations)

  /** Play a game, returns the score for each color from the winner to the loser */
  def play() = {

    players.foreach { p =>
      val hisAffectation = affectations.filter(_._2 == p)
      val hisColor = hisAffectation.head._1
      p.initGame(gameState.public, hisColor)
    }

    for (roundNum <- 0 until playersCount) {
      /* Play the round number roundNum. Round number is zero-based*/
      for (turnNum <- 0 until turnCount) {
        doPlacement(roundNum, turnNum)
      }

      val columnMax = gameState.districts.view.map { _.size }.max
      for (column <- 0 until columnMax) {
        gameState.districts.foreach { d =>
          if (column < d.size) {
            doActivation(roundNum, d, column)
          }
        }
      }

      gameState.endRound()
    }

    gameState.families.map { f => f.color -> f.cash.amount }.sortBy(_._2).reverse.toMap
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
      case Some(card) => card.place(gameState.districts(districtPublicState.position))
    }
  }

  /** Second stage of the round : activating cards */
  private def doActivation(roundNum: Int, district: District, column: Int): Unit = {
    val position = Position(district, column)
    val card = position.get
    val player = affectations(card.family.color)

    card.reveal()
    if (!card.canAct) return

    val (activation, neighbour) = player.activate(gameState.public, position.public)
    if (!activation) return

    val targetPosition = neighbour match {
      case None => None
      case Some(LeftNeighbour) => position.left
      case Some(RightNeighbour) => position.right
      case _ => throw new CheaterException(neighbour.toString)
    }

    val target = targetPosition map { p => district(p.column) }

    card match {
      case wot: WithoutTarget => activateWithoutTarget(wot, position)
      case wt: WithTarget if target.isEmpty => throw new CheaterException("Player " + player + " : card " + card + " needed a target")
      case wt: WithTarget => activateOnTarget(wt, position, target.get, targetPosition.get)
    }
  }

  private def activateWithoutTarget(card: WithoutTarget, cardPosition: Position) = {
    if (card.canAct) {
      card.activate(gameState)
      card.tap()

      cardPosition.left foreach { p => witness(p.get, p, card, cardPosition) }
      cardPosition.right foreach { p => witness(p.get, p, card, cardPosition) }
    }
  }

  private def activateOnTarget(card: WithTarget, cardPosition: Position, target: Card, targetPosition: Position) = {
    val targetedPlayer = affectations(target.family.color)
    target.isTargeted(card)
    
    if (target.hasTargetedReaction && targetedPlayer.react(gameState.public, targetPosition.public, cardPosition.public)) {
      target.react(card)
    }
    
    /* check if the card can still act */
    if (card.canAct) {
      card.activate(target, gameState)
      card.tap()

      cardPosition.left foreach { p => witness(p.get, p, card, cardPosition) }
      cardPosition.right foreach { p => witness(p.get, p, card, cardPosition) }
    }
  }

  private def witness(witness: Card, witnessPosition: Position, origin: Card, originPosition: Position) = {
    val witnessPlayer = affectations(witness.family.color)
    if (witness.hasWitnessEffect && witnessPlayer.witness(gameState.public, witnessPosition.public, originPosition.public)) {
      witness.witness(origin)
    }
  }

}