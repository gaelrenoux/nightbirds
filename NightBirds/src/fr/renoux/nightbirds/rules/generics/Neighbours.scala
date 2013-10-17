package fr.renoux.nightbirds.rules.generics

case class Neighbours(val early: Option[Card], val late: Option[Card]) {
  if (early==late) throw new IllegalArgumentException
  
  def remove(card : Card) = Some(card) match {
    case early => Neighbours(None, late)
    case late => Neighbours(early, None)
    case _ => Neighbours(early, None)
  }
}