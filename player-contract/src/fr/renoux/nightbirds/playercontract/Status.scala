package fr.renoux.nightbirds.playercontract

/* Status of the game */
class Status

object Playing extends Status
case class Revealing(
  val currentDistrict: Int,
  val currentCard: Int) extends Status
object TurnEnd extends Status