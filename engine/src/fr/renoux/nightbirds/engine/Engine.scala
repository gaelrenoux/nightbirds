package fr.renoux.nightbirds.engine

import fr.renoux.nightbirds.rules.cardtypes.Color
import fr.renoux.nightbirds.rules.state.Family
import fr.renoux.nightbirds.rules.state.GameState
import scala.annotation.tailrec

object Engine {

  val random = new Random

  /** Play a game, returns the score for each color from the winner to the loser */
  def play(players: Set[Player]) = {
    /* Initialization */
    val families = random.pick(Color.all.toSeq, players.size).map { new Family(_) }.toIndexedSeq
    val initialGs = new GameState(families)

    players.foreach { _.initGame(initialGs, players.zip(families).toMap) }

    val finalGs = playRounds(initialGs, families.zip(players).toMap)
    finalGs.families.map { f => f.color -> f.cash.amount }.sortBy(_._2).reverse.toMap
  }

  /** Round number is zero-based */
  @tailrec
  def playRounds(gs: GameState, familiesToPlayer: Map[Family, Player], startingNum: Int = 0): GameState = {
    if (gameEnd(gs, startingNum)) {
      return gs
    }

    val gsAfterPlacement = doPlacement(gs, familiesToPlayer)
    val gsAfterRound = doActivation(gs, familiesToPlayer)

    playRounds(gs, familiesToPlayer: Map[Family, Player], startingNum + 1)
  }

  def doPlacement(gs: GameState, familiesToPlayer: Map[Family, Player]): GameState = {
    gs
  }

  def doActivation(gs: GameState, familiesToPlayer: Map[Family, Player]): GameState = {
    gs
  }

  def gameEnd(gs: GameState, roundNum: Int): Boolean = roundNum >= gs.families.length

}