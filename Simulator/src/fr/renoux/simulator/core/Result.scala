package fr.renoux.simulator.core

sealed abstract class Result {
  def victor : Player
}

case class OneVictorResult(val victor: Player, val others: Set[Player]) extends Result

case class RankingResult(val ranking: List[Player]) extends Result {
  def victor = ranking.head
}

case class PointsResult(val points: Map[Player, Int]) extends Result {
  def victor = points.maxBy { _._2}._1  
}