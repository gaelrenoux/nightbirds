package fr.renoux.nightbirds.rules

/** Current game board */
case class Board(
  val districts: List[Card],
  val money : Map[Color, Int]  
)