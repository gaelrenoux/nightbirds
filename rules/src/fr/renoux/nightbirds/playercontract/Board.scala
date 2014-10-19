package fr.renoux.nightbirds.playercontract

/** Current game board */
case class Board(
  val districts: List[Card],
  val money : Map[Color, Int]  
)