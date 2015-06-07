package fr.renoux.nightbirds.simulation

import fr.renoux.nightbirds.rules.engine.Game
import fr.renoux.nightbirds.simulation.players.BasePlayer
import fr.renoux.nightbirds.rules.cardtypes.Color
import fr.renoux.nightbirds.rules.engine.Random

object NightbirdsSimulation extends App {

  implicit val random = new Random(8073608544803983463L)
  println("Starting with seed " + random.seed)

  for (i <- 0 until 100) {
    println("Play game #" + i)
    val game = new Game(new BasePlayer, new BasePlayer, new BasePlayer)
    val result = game.play()
  }
}