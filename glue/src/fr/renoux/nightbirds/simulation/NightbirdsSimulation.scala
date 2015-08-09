package fr.renoux.nightbirds.simulation

import scala.collection.mutable
import fr.renoux.nightbirds.rules.engine.Game
import fr.renoux.nightbirds.simulation.players.BasePlayer
import fr.renoux.nightbirds.rules.cardtypes.Color
import fr.renoux.nightbirds.rules.engine.Random
import fr.renoux.nightbirds.utils.Logger
import fr.renoux.nightbirds.simulation.players.RunResult
import fr.renoux.nightbirds.simulation.players.GlobalResult

object NightbirdsSimulation extends App {
  implicit val random = new Random(8073608544803983463L)
  Logger.debug("Starting with seed " + random.seed)
  
  val results = mutable.ListBuffer[RunResult]()

  for (run <- 0 until 10) {
    val victories = mutable.Map[Color, Int]()
    val cumulatedScore = mutable.Map[Color, Int]()

    val iterations = 1000
    for (i <- 0 until iterations) {
      Logger.info("Play game #" + i)
      val game = new Game(new BasePlayer, new BasePlayer, new BasePlayer)
      val result = game.play()

      val (victor, victorScore) = result(0)

      val existing = victories.get(victor).getOrElse(0)
      victories.put(victor, existing + 1)

      result foreach { colorAndScore =>
        val (color, score) = colorAndScore
        val existing = cumulatedScore.get(color).getOrElse(0)
        cumulatedScore.put(color, existing + score)
      }
    }

    val runResult = RunResult(victories.toMap, cumulatedScore.mapValues(1.0*_/iterations).toMap)
    results.append(runResult)
    Logger.warn(runResult.toString())
  }
  
  /* Interpret results */
  val global = GlobalResult(results.toList)
  /* results foreach { r =>
    Logger.warn("Victory repartition is : " + r.toVictoriesString);
  }
  results foreach { r =>
    Logger.warn("Mean score at the end of the games is : " + r.toMeanScoreString);
  }*/
  Logger.warn(global.toFixedWidthTable)
  
  

}