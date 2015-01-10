package fr.renoux.nightbirds.rules.state

import fr.renoux.nightbirds.rules.cardtypes.Color

/**
 * A family, which is associated to a player. A family has cash, cards and stuff.
 */
class Family(val color: Color) {

  val cards = color.makeCards(this)

  private var _cash: Cash = Cash(0)
  def cash = _cash

  private var _hand: Set[Card] = cards
  def handSize = _hand.size

  def take(amount: Cash) = {
    val t = cash - amount
    _cash = t.remaining
    resetPublicState()
    t
  }

  /** Store an amount of cash in this famHavingCashily */
  def store(amount: Cash) = {
    _cash += amount
    resetPublicState()
  }

  def discard(card: Card) = {
    _hand = _hand - card
    resetPublicState()
  }

  def resetHand = {
    _hand = cards
    resetPublicState()
  }

  private var _publicState = new FamilyPublicState(color, cash, _hand.size)
  private def resetPublicState() = { _publicState = new FamilyPublicState(color, cash, _hand.size) }
  def public = _publicState
}

/** only public informations */
class FamilyPublicState(val color: Color, val cash: Cash, val handSize: Int)