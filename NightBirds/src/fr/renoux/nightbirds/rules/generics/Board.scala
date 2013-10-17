package fr.renoux.nightbirds.rules.generics

import scala.collection.mutable

/** families : Families playing the game, in order. It is a loop, so the first family sits right after the second */
class Board(val families: Family*) {

  /** Ordered list of districts */
  val districts = List.fill(families.length - 1) { new District(this) }

  /** Index to accelerate looking for cards in the district : values are a couple of the district and the position within the district */
  private val boardIndex = mutable.Map[Card, (District, Int)]()

  def index(c: Card, d: District, i : Int) = {
    boardIndex.put(c, (d, i))
  }
  def unindex(c: Card) = {
    boardIndex.remove(c)
  }

  def districtOf(c: Card) = boardIndex.get(c).get._1

  /** Move a card from a district to another */
  def moveCardTo(c: Card, d: District) = {
    val (oldDistrict, oldPosition) = boardIndex.get(c).get
    oldDistrict.remove(oldPosition)
    d.add(c)
  }

  /** Neighbours of a card : right before and right after */
  def getNeighbours(c: Card) = {
    val (d, ix) = boardIndex.get(c).get
    val cards = d.cards

    val early = if (ix == 0) None else Some(cards(ix - 1))
    val late = if (ix == cards.size - 1) None else Some(cards(ix + 1))
    Neighbours(early, late)
  }

}
  



