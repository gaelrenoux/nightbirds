package fr.renoux.nightbirds.rules.state

import scala.collection.mutable

/** A district on the board. Position is zero-based. */
class District(val position: Int) {

  private var _cards = mutable.ArrayBuffer[Card]()
  /** we do not wish cards to be modified from the outside */
  def apply(ix: Int) = _cards(ix)
  def cards = _cards.toSeq
  def size = _cards.size

  /** Add a new card in a district. Restricted to this package, the engine should use the Card#place() method. */
  private[state] def append(c: Card) = _cards.append(c)

  /** Remove a card from this a district. Restricted to this package, the engine should use the Card#place() method. Error if not present. Leaves a missing card if the card removed was not at the end. */
  private[state] def remove(c: Card) = {
    val index = _cards.indexOf(c)
    _cards(index) = new MissingCard(this, index)
  }

  def clear() = _cards.clear()

  def indexOf(card: Card) = _cards.indexOf(card)

  def public = {
    new DistrictPublicState(position, _cards.map { _.public }.toVector)
  }

  override def toString = _cards.mkString("#" + position + "(", ",", ")")

  def toFixedString = _cards map { _.toFixedString } mkString ("#" + position + "    ", "    ", "")
}

/** only public informations */
class DistrictPublicState(val position: Int, val cards: Vector[CardPublicState]) {
  def size = cards.size
  def apply(ix: Int) = cards(ix)
}