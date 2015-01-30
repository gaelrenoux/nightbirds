package fr.renoux.nightbirds.simulation

import fr.renoux.nightbirds.engine.Game
import fr.renoux.nightbirds.simulation.players.BasePlayer
import fr.renoux.nightbirds.rules.cardtypes.Color

object NightbirdsSimulation extends App {

  for (i <- 0 until 10) {
    val game = new Game(new BasePlayer, new BasePlayer, new BasePlayer)
    val result = game.play()
    result.foreach { println }
  }
}