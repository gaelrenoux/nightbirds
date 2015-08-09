package fr.renoux.nightbirds.simulation.players

import fr.renoux.nightbirds.rules.cardtypes.Color
import scala.collection.mutable

case class GlobalResult(
  val runResults: List[RunResult]) {

  private val colorSet = runResults flatMap { _.colors } toSet  
  private val colors = colorSet.toList.sortBy(_.toString())

  def toFixedWidthTable = {
    /* headers */
    val headersLine1 = colors map {"       " + _.toFixedString + "       "} mkString("    ")
    val headersLine2 = colors map {c => "Victories    Score  "} mkString("    ")

    /* for each run */
    val lines = runResults map { result =>
      colors map { color =>
        val victories = result.victoriesCount(color)
        val meanScore = result.meanScore(color)
        "  %5d      %5.2f  ".format(victories, meanScore)
      } mkString("    ")
    }
    
    val concat = mutable.ListBuffer[String]()
    concat.append(headersLine1, headersLine2)
    concat.appendAll(lines)
    concat mkString("\n");

  }

}