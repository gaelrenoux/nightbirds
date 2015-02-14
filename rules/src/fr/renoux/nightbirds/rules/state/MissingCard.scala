package fr.renoux.nightbirds.rules.state

import fr.renoux.nightbirds.rules.engine.CheaterException


/** A card that is not here because it have been removed from the board (by the taxi, for instance) */
object MissingCardType extends CardType(null)
class MissingCard(d : District, ix : Int) extends Card(null)(MissingCardType) {

  override val legality = null

  override val position = Some(new Position(d, ix))
  
  override def reveal() = Unit
  override def canAct = false
  override def revealed = true

  override def toString = cardType.toString

  override def toFixedString = new String(Array.fill(Card.FixedStringLength)(' '))

  /** You don't place a missing card. It appears when another is removed. */
  override def place(district: District) = throw new CheaterException("This is a missing card !")
  
  override def cash = throw new CheaterException("This is a missing card !")
  override def tapped = throw new CheaterException("This is a missing card !")
  override protected def tap() = throw new CheaterException("This is a missing card !")
  override def out = throw new CheaterException("This is a missing card !")
  override def take(amount: Cash) = throw new CheaterException("This is a missing card !")
  override def takeIfAvailable(amount: Cash) = throw new CheaterException("This is a missing card !")
  override def store(amount: Cash) = throw new CheaterException("This is a missing card !")
  override def takeOut() = throw new CheaterException("This is a missing card !")
  override def specificIsTargeted(origin: Card) = throw new CheaterException("This is a missing card !")
  override def specificReactToTargeted(origin: Card) = throw new CheaterException("This is a missing card !")
  override def specificReactToWitness(origin: Card) = throw new CheaterException("This is a missing card !")
  override def sleep() = throw new CheaterException("This is a missing card !")
  override def public = throw new CheaterException("This is a missing card !")

}