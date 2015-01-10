package fr.renoux.nightbirds.rules.state

/** Card in the game. */
class Card(val family: Family)(val cardType: CardType) {

  val legality: Legality = cardType.legality

  private var _cash = Cash.Zero
  def cash = _cash

  var _revealed = false
  def revealed = _revealed
  
  def reveal() = {
    _revealed = true
    resetPublicState()
  }

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

  private var _publicState = new CardPublicState(family.public, cash, None)
  private def resetPublicState() = { _publicState = new CardPublicState(family.public, cash, if (revealed) Some(cardType) else None) }
  def public = _publicState

}

trait WithTarget extends Card {
  def activate(target: Card)
}

trait WithoutTarget extends Card {
  def activate()
}

/** only public informations */
class CardPublicState(val family: FamilyPublicState, val cash: Cash, val cardType: Option[CardType])