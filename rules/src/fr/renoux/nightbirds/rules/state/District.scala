package fr.renoux.nightbirds.rules.state

import scala.collection.mutable

/** A district on the board. Position is zero-based. */
class District(val position: Int) {

  private var _cards = mutable.ArrayBuffer[Option[Card]]()
  /** we do not wish cards to be modified from the outside */
  def apply(ix: Int) = _cards(ix)
  def cards = _cards.toSeq
  def size = _cards.size

  /** Add a new card in a district. Restricted to this package, the engine should use the Card#place() method. */
  private[state] def append(c: Card) = _cards.append(Some(c))

  /** Remove a card from this a district. Restricted to this package, the engine should use the Card#place() method. Error if not present. Leaves a missing card if the card removed was not at the end. */
  private[state] def remove(c: Card) = {
    val index = _cards.indexOf(Some(c))
    _cards(index) = None
  }

  def clear() = _cards.clear()

  def indexOf(card: Card) = _cards.indexOf(Some(card))

  def public = {
    new DistrictPublicState(position, _cards map { _ map { _.public } } toVector)
  }

  override def toString = _cards.mkString("#" + position + "(", ",", ")")

  /** Returns the next activation candidate with a priority */
  def nextActivationCandidate: Option[(Card, (Int, Int))] = _cards.indexWhere { cardOpt => cardOpt.isDefined && cardOpt.get.canBeActivated } match {
    case -1 => None
    case index => Some(_cards(index).get, (index, this.position))
  }

  def toFixedString = _cards map { _ map { _.toFixedString } getOrElse (" " * Card.FixedStringLength) } mkString ("#" + position + "    ", "    ", "")
}

/** only public informations */
class DistrictPublicState(val position: Int, val cards: Vector[Option[CardPublicState]]) {
  def size = cards.size
  def apply(ix: Int) = cards(ix)
}