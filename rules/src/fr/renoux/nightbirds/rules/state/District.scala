package fr.renoux.nightbirds.rules.state

/** A district on the board. Immutable, all methods returns a new district. Position is zero-based. */
class District(val position : Int, val cards: List[Card] = Nil) {

  /** Add a new card in a district */
  def +(c: Card) = new District(position, c :: cards)

  /** Remove a card from a district */
  def -(c: Card): District = new District(position, _remove(cards, c))

  /** Replace a card from a district */
  def updated(i : Int, c: Card): District = new District(position, cards.updated(i, c))

  private def _remove(cards: List[Card], c: Card): List[Card] = cards match {
    case Nil => Nil
    case head :: tail => if (head == c) tail else head :: _remove(tail, c)
  }

  /** Remove a card at an index from a district. First element is index 0. */
  def remove(ix: Int): District = new District(position, _remove(cards, ix))

  private def _remove(cards: List[Card], ix: Int): List[Card] = ix match {
    case 0 => cards.tail
    case _ => cards.head :: _remove(cards.tail, ix - 1)
  }

}