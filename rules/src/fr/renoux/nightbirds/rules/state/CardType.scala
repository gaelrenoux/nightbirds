package fr.renoux.nightbirds.rules.state

class CardType (val legality: Legality) {
  override def toString = {
    /* get the Type$ off */
    this.getClass().getSimpleName().dropRight(5)
  }
}