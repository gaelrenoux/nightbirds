package fr.renoux.nightbirds.rules.state

class CardType(val legality: Legality) {
  override lazy val toString = {
    /* get the Type$ off */
    this.getClass().getSimpleName().dropRight(5)
  }

  lazy val toFixedString = toString.padTo(CardType.FixedStringLength, ' ')
}

object CardType {
  val FixedStringLength = 10
}