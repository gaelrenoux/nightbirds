package fr.renoux.nightbirds.rules.state

/** Card in the game. */
class Card(val family: Family)(val cardType: CardType) {

  val legality: Legality = cardType.legality

  private var _cash = Cash.Zero
  def cash = _cash

  private var _revealed = false
  def revealed = _revealed

  private var _position: Option[(District, Int)] = None
  def position = _position

  private var _canAct = true
  def canAct = _canAct

  /** true when a card has been activated */
  private var _tapped = false
  def tapped = _tapped
  def tap() = { _tapped = true }

  /** Place this card at the end of a district */
  def place(district: District) = {
    _position = Some(district, district.size)
    district.append(this)
  }

  /** This card is shown to everybody */
  def reveal() = {
    _revealed = true
    resetPublicState()
  }

  /** Take money from this card. If not enough, take from the family. */
  def take(amount: Cash) = {
    val t = _cash - amount
    _cash = t.remaining
    if (t.fullySuccessful) {
      t
    } else {
      val t2 = family.take(t.notSubtracted)
      t.backedBy(t2)
    }
  }

  /** Take money from this card. Do not take from the family if there isn't enough. */
  def takeIfAvailable(amount: Cash) = {
    val t = _cash - amount
    _cash = t.remaining
  }

  /** Store an amount of cash in this card */
  def store(amount: Cash) = { _cash += amount }

  /** Hit this card ans send it to the hospital */
  def hit() = {
    _cash = Cash.Zero
    family.take(Cash.One)
    _canAct = false
  }
  
  /** Is targeted and stuff may happens. Player can't refuse to. */
  def isTargeted(origin: Card) = {}

  /** Does this card do something when targeted ? */
  val hasTargetedReaction = false

  /** Is targeted and does something (the player chose to) */
  def react(origin: Card) = {}

  /** Does this card do something when witness ? */
  val hasWitnessEffect = false
  
  def witness(origin: Card) = {}

  /** At the end of the night */
  def sleep() = {
    family.store(_cash)
    _cash = Cash.Zero
    _revealed = false
    _position = None
    _canAct = true
    _tapped = false
    resetPublicState()
  }

  private var _publicState = new CardPublicState(None, family.public, Cash.Zero, true, false)
  private def resetPublicState() = { _publicState = new CardPublicState(if (revealed) Some(cardType) else None, family.public, cash, _canAct, _tapped) }
  def public = _publicState

  override def toString = {
    val builder = new StringBuilder(this.getClass().getSimpleName())
    builder.append("(").append(_cash).append(", ").append(family.color).append(")")
    builder.toString()
  }

}

trait WithTarget extends Card {
  def activate(target: Card, gs: GameState): Unit = activate(target)
  def activate(target: Card): Unit = Unit
}

trait WithoutTarget extends Card {
  def activate(gs: GameState): Unit = activate()
  def activate(): Unit = Unit
}

/** only public informations */
class CardPublicState(val cardType: Option[CardType], val family: FamilyPublicState, val cash: Cash, val canAct: Boolean, val tapped: Boolean)