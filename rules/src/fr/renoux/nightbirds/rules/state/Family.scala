package fr.renoux.nightbirds.rules.state

import fr.renoux.nightbirds.rules.cardtypes.Color

/**
 * A family, which is associated to a player. A family has cash, cards and stuff.
 *
 *  Immutable.
 */
class Family(val color: Color, val cash: Cash = Cash(0), val prison: Set[Card] = Set[Card]()) {

  def take(amount: Cash) = if (amount.isZero || cash.isZero) { this } else {
    val t = cash - amount
    new Family(color, t.remaining, prison)
  }

  /** Store an amount of cash in this famHavingCashily */
  def store(amount: Cash) = if (amount.isZero) this else new Family(color, cash + amount, prison)

  def hold(target: Card) = new Family(color, cash, prison + target)

  /** release a card and get its bail money */
  def release(target: Card) = new Family(color, cash + target.bail, prison - target)
}