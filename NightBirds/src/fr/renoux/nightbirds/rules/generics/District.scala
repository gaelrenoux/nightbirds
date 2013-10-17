package fr.renoux.nightbirds.rules.generics

import scala.collection.mutable

class District(val board: Board) {
  private val cardsMutableArray = mutable.ArrayBuffer[Card]()

  def cards = cardsMutableArray.toList: Seq[Card]

  /** Add a new card in a district */
  def add(c: Card) = {
    cardsMutableArray += c
    board.index(c, this, cardsMutableArray.length - 1)
  }

  /** Remove a card from a district */
  def remove(c: Card) = {
    cardsMutableArray -= c
    board.unindex(c)
  }

  /** Remove a card at an index from a district */
  def remove(ix: Int) = {
    val card = cardsMutableArray.remove(ix)
    board.unindex(card)
  }

  def reset = cardsMutableArray.clear
}