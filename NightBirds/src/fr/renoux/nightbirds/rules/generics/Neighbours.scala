package fr.renoux.nightbirds.rules.generics

import scala.collection.Iterator

case class Neighbours(val early: Option[Card], val late: Option[Card]) extends Iterable[Card] {
  if (early != None && late != None && early == late) throw new IllegalArgumentException

  def remove(card: Card) = Some(card) match {
    case `early` => Neighbours(None, late)
    case `late` => Neighbours(early, None)
    case _ => Neighbours(early, None)
  }

  override def iterator = Array(early, late).flatten.iterator

}