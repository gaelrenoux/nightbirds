package fr.renoux.nightbirds.rules.state

/** Card in the game. */
class Card(val family: Family)(val legality: Legality) extends PublicRevealedCard {
  
  val clazz = this.getClass()

  private var _cash = Cash.Zero
  def cash = _cash

  var _revealed = false
  def revealed = _revealed

  def take(amount: Cash) = {
    val t = _cash - amount
    if (t.fullySuccessful) {
      t
    } else {
      val t2 = family.take(t.notSubtracted)
      t.backedBy(t2)
    }
  }

  /** Store an amount of cash in this family */
  def store(amount: Cash) = { _cash += amount }

  /** At the end of the night */
  def sleep() = {
    family.store(_cash)
    _cash = Cash.Zero
    _revealed = false
  }
  
  def public = if (revealed) {
    this.asInstanceOf[PublicRevealedCard]
  } else {
    this.asInstanceOf[PublicCard]
  }

}

trait WithTarget extends Card {
  def activate(target: Card)
}

trait WithoutTarget extends Card {
  def activate()
}

/** only public informations */
trait PublicCard {
  val family: PublicFamily
  def cash: Cash
  def revealed : Boolean
}

/** only public informations for a card that is visible by everyone */
trait PublicRevealedCard extends PublicCard {
  val clazz: Class[_ <: Card]
}