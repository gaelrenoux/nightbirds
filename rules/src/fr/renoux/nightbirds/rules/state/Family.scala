package fr.renoux.nightbirds.rules.state

import fr.renoux.nightbirds.rules.cardtypes.Color

/**
 * A family, which is associated to a player. A family has cash, cards and stuff.
 */
class Family(val color: Color) extends PublicFamily {

  val cards = color.makeCards(this)

  private var _cash: Cash = Cash(0)
  def cash = _cash

  private var _hand: Set[Card] = cards
  def handSize = _hand.size

  def take(amount: Cash) = {
    val t = cash - amount
    _cash = t.remaining
    t
  }

  /** Store an amount of cash in this famHavingCashily */
  def store(amount: Cash) = { _cash += amount }

  def resetHand = {
    _hand = cards
  }

  /** Returns the family with "fog of war" : only public information */
  def public = this.asInstanceOf[PublicFamily]
}

/** only public informations */
trait PublicFamily {
  val color: Color
  def handSize: Int
  def cash: Cash
}