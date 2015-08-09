package fr.renoux.nightbirds.simulation.players

import fr.renoux.nightbirds.rules.cardtypes.Color

case class RunResult(
  val victoriesCount: Map[Color, Int],
  val meanScore: Map[Color, Double]) {
  
  def colors = victoriesCount.keySet ++ meanScore.keySet

  def toVictoriesString = {
    victoriesCount.toSeq.sortBy(_._1.toString()) map { colorAndCount =>
      (colorAndCount._1.toFixedString + " -> %3d").format(colorAndCount._2)
    } mkString ("    ")
  }
  
  def toMeanScoreString = {
    meanScore.toSeq.sortBy(_._1.toString()) map { colorAndScore =>
      (colorAndScore._1.toFixedString + " -> %f").format(colorAndScore._2)
    } mkString ("    ")
  }
}