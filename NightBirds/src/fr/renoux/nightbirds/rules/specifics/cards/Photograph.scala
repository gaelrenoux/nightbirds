package fr.renoux.nightbirds.rules.specifics.cards

import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.WithTarget
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.generics.WithoutTarget
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.Cash
import fr.renoux.nightbirds.rules.generics.Callbacks

/** Photograph : earns two for each neighbour that does (or have done) something without targeting him */
class Photograph(c: Callbacks, b: Board, f: Family) extends Card(c, b, f) with WithoutTarget {

  var jerk: Option[Card] = None

  override def doProceed() = {
    //does everything as a witness, so nothing here
  }

  override def doTargeted(source: Card) = {
    jerk = Some(source)
    true
  }

  override def doMandatoryWitness(source: Card) = {
    jerk match {
      case None => store(Cash(2))
      case Some(guy) if guy != source => store(Cash(2))
      case _ => //do nothing
    }
    true
  }
}
