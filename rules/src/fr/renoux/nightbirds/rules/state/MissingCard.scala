package fr.renoux.nightbirds.rules.state

import fr.renoux.nightbirds.rules.engine.CheaterException
import fr.renoux.nightbirds.rules.cardtypes.Color
import fr.renoux.nightbirds.rules.cardtypes.NonexistingColor


/** A card that is not here because it have been removed from the board (by the taxi, for instance) */
object MissingCardType extends CardType(null)
class MissingCard(d : District, ix : Int) extends Card(NonexistingFamily)(MissingCardType) {

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
  override def take(amount: Cash) = Transaction(Cash.Zero, Cash.Zero, amount, false)
  override def takeIfAvailable(amount: Cash) = throw new CheaterException("This is a missing card !")
  override def store(amount: Cash) = throw new CheaterException("This is a missing card !")
  override def takeOut() = throw new CheaterException("This is a missing card !")
  override def specificIsTargeted(origin: Card) = throw new CheaterException("This is a missing card !")
  override def specificReactToTargeted(origin: Card) = throw new CheaterException("This is a missing card !")
  override def specificReactToWitness(origin: Card) = throw new CheaterException("This is a missing card !")
  override def sleep() = Unit
  override lazy val public = new CardPublicState(Some(MissingCardType), family.public, Cash.Zero, false, false)
  
}

object NonexistingFamily extends Family(NonexistingColor) {
  override lazy val public = new FamilyPublicState(NonexistingColor, Cash(0), 0)
}