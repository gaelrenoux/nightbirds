package fr.renoux.nightbirds.engine

import fr.renoux.nightbirds.rules.Rules
import fr.renoux.nightbirds.rules.cardtypes.Color
import fr.renoux.nightbirds.rules.state.Family
import fr.renoux.nightbirds.rules.state.GameState
import scala.annotation.tailrec
import fr.renoux.nightbirds.rules.state.District
import fr.renoux.nightbirds.rules.state.WithoutTarget
import fr.renoux.nightbirds.rules.state.WithTarget

class Game(playersInput: Set[Player]) {

  val random = new Random

  /** Number of players */
  val playersCount = playersInput.size

  /** Players, ordered in a random fashion */
  val players = random.shuffle(playersInput)

  /** Colors, in the same order as players */
  val colors = random.pick(Color.all.toSeq, playersCount)

  /** Number of turns per step of a round (two steps per round : placement and activation) */
  val turnCount = playersCount * Rules.CardsCountPerPlayer

  val affectations = colors.zip(players).toMap

  val families = colors.map { new Family(_) }.toIndexedSeq

  val gameState = new GameState(families)

  /** Play a game, returns the score for each color from the winner to the loser */
  def play() = {

    players.foreach { _.initGame(gameState.public, affectations) }

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

    val (card, district) = player.place(gameState.public)

    if (!family.discard(card)) {
      throw new CheaterException("Player " + player + " : card " + card + " is not available")
    }
    district.append(card)
  }

  /** Second stage of the round : activating cards */
  private def doActivation(roundNum: Int, district: District, column: Int) = {
    val card = district(column)
    val player = affectations(card.family.color)

    card.reveal()
    val (activation, neighbour) = player.activate(gameState.public, district, column)
    
    if (activation) {
      val target = neighbour.map {
        _ match {
          case Left if column == 0 => throw new CheaterException("Player " + player + " : card " + card + " has no left neighbour")
          case Left => district(column - 1)
          case Right if column >= district.size => throw new CheaterException("Player " + player + " : card " + card + " has no right neighbour")
          case Right => district(column + 1)
        }
      }

      card match {
        case wot: WithoutTarget => wot.activate()
        case wt: WithTarget if target.isEmpty => throw new CheaterException("Player " + player + " : card " + card + " needed a target")
        case wt: WithTarget => wt.activate(target.get)
      }
    }//end if activation
  }

}