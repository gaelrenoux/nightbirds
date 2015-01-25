package fr.renoux.nightbirds.rules.state

import fr.renoux.nightbirds.rules.cardtypes.Color

/**
 * A family, which is associated to a player. A family has cash, cards and stuff.
 */
class Family(val color: Color) {

  val cards = color.makeCards(this)

  private var _cash: Cash = Cash(10)
  def cash = _cash

  private var _hand: Set[Card] = cards
  def handSize = _hand.size
  def hand = _hand

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

  /** Returns true if the card was able to be discarded */
  def discard(card: Card): Boolean = {
    if (_hand.contains(card)) {
      _hand = _hand - card
      resetPublicState()
      true
    } else false
  }

  /** Returns an option with the discarded card if it was there, None if it wasn't */
  def discard(cardType: CardType): Option[Card] = {
    val card = _hand.filter(_.cardType == cardType).headOption
    if (card.isDefined) {
      _hand = _hand - card.get
      resetPublicState()
      true
    }
    card
  }

  def resetHand() = {
    _hand = cards
    resetPublicState()
  }

  private var _publicState = new FamilyPublicState(color, cash, _hand.size)
  private def resetPublicState() = { _publicState = new FamilyPublicState(color, cash, _hand.size) }
  def public = _publicState

  override def toString = {
    val builder = new StringBuilder(color.toString)
    builder.append("(").append(_cash).append(")").append("(")
    _hand map { _.cardType } mkString (builder.toString(), ",", ")")
  }
}

/** only public informations */
class FamilyPublicState(val color: Color, val cash: Cash, val handSize: Int)