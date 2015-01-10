package fr.renoux.nightbirds.engine

import fr.renoux.nightbirds.rules.Rules
import fr.renoux.nightbirds.rules.cardtypes.Color
import fr.renoux.nightbirds.rules.state.Family
import fr.renoux.nightbirds.rules.state.GameState
import scala.annotation.tailrec

class Game(playersInput: Set[Player]) {

  val random = new Random

  /** Players, ordered in a random fashion */
  val players = random.shuffle(playersInput)

  /** Colors, in the same order as players */
  val colors = random.pick(Color.all.toSeq, playersCount)

  /** Number of players */
  val playersCount = players.size

  /** Number of turns per step of a round (two steps per round : placement and activation) */
  val turnCount = playersCount * Rules.CardsCountPerPlayer

  val affectations = colors.zip(players).toMap

  val families = colors.map { new Family(_) }.toIndexedSeq

  val gameState = new GameState(families)

  /** Play a game, returns the score for each color from the winner to the loser */
  def play() = {

    players.foreach { _.initGame(gameState, affectations) }

    for (roundNum <- 0 until playersCount) {
      /* Play the round number roundNum. Round number is zero-based*/
      for (turnNum <- 0 until turnCount) {
        doPlacement(roundNum, turnNum)
      }
      for (turnNum <- 0 until turnCount) {
        doActivation(roundNum, turnNum)
      }
    }

    gameState.families.map { f => f.color -> f.cash.amount }.sortBy(_._2).reverse.toMap
  }

  /** First stage of the round : placing cards */
  private def doPlacement(roundNum: Int, turnNum: Int) = {
    val playerIndex = (turnNum + roundNum) % playersCount
    val player = players(playerIndex)
    
    player.place(gameState.public)
  }

  /** Secodn stage of the round : activating cards */
  private def doActivation(roundNum: Int, turnNum: Int) = {
    val playerIndex = (turnNum + roundNum) % playersCount
    val player = players(playerIndex)
    
    player.activate(gameState.public)
  }

}