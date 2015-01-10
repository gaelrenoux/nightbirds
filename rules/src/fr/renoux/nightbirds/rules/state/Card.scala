package fr.renoux.nightbirds.rules.state

/** Card in the game. Immutable */
class Card(
  val cardType: CardType,
  val family: Family,
  val cash: Cash) {

  val bail: Cash = Cash(0)
  //val color : family.color

  implicit def toCardType = cardType

  def is(cardType: CardType) = (this.cardType == cardType)

  def take(amount: Cash) = {
    val t = cash - amount
    val newF = family.take(t.notSubtracted)
    new Card(cardType, newF, t.remaining)
  }

  def store(amount: Cash) = new Card(cardType, family, cash + amount)

}