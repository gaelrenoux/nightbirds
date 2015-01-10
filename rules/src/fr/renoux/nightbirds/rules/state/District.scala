package fr.renoux.nightbirds.rules.state

import scala.collection.mutable

/** A district on the board. Position is zero-based. */
class District(val position: Int) {

  var _cards = mutable.ArrayBuffer[Card]()
  /** we do not wish cards to be modified from the outside */
  def cards = _cards.toSeq

  /** Add a new card in a district */
  def append(c: Card) = _cards.append(c)
  
  def clear() = _cards.clear()

}