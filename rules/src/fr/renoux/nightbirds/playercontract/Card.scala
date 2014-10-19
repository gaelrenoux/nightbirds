package fr.renoux.nightbirds.playercontract

sealed abstract class CardType
object Bum extends CardType
object Burglar extends CardType
object Cook extends CardType
object Cop extends CardType
object Dealer extends CardType
object Dj extends CardType
object Photograph extends CardType
object PrivateEye extends CardType
object Skinhead extends CardType
object Taxi extends CardType
object Thug extends CardType
object Whore extends CardType
object FaceDown extends CardType

case class Card(val color : Color, val cardType : CardType) {
  implicit def toCardType = cardType
  
  def is(cardType : CardType) = (this.cardType == cardType)
}