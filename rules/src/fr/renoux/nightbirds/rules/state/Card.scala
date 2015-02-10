package fr.renoux.nightbirds.rules.state

/** Card in the game. */
class Card(val family: Family)(val cardType: CardType) {

  val legality: Legality = cardType.legality

  private var _cash = Cash.Zero
  def cash = _cash

  private var _revealed = false
  def revealed = _revealed

  private var _position: Option[Position] = None
  def position = _position

  private var _canAct = true
  def canAct = _canAct

  /** true when a card has been activated */
  private var _tapped = false
  def tapped = _tapped
  protected def tap() = { _tapped = true; resetPublicState() }

  /** Place this card at the end of a district */
  def place(district: District) = {
    _position = Some(Position(district, district.size))
    district.append(this)
    resetPublicState()
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
      resetPublicState()
      t
    } else {
      val t2 = family.take(t.notSubtracted)
      resetPublicState()
      t.backedBy(t2)
    }
  }

  /** Take money from this card. Do not take from the family if there isn't enough. */
  def takeIfAvailable(amount: Cash) = {
    val t = _cash - amount
    _cash = t.remaining
    resetPublicState()
    t
  }

  /** Store an amount of cash in this card */
  def store(amount: Cash) = {
    _cash += amount
    resetPublicState()
  }

  /** Hit this card ans send it to the hospital */
  def hit() = {
    _cash = Cash.Zero
    family.take(Cash.One)
    _canAct = false
    resetPublicState()
  }

  /** Is targeted and stuff may happens. Player can't refuse to. */
  final def isTargeted(origin: Card) = specificIsTargeted(origin)
  def specificIsTargeted(origin: Card) = {}

  /** Does this card do something when targeted ? */
  val hasTargetedReaction = false

  /** The player chose to react to being targeted */
  final def reactToTargeted(origin: Card) = {
    reveal()
    specificReactToTargeted(origin)
    tap()
  }
  def specificReactToTargeted(origin: Card) = {}

  /** Is witness and stuff may happens. Player can't refuse to. */
  final def witness(origin: Card) = specificWitness(origin)
  def specificWitness(origin: Card) = {}

  /** Does this card do something when witness ? */
  val hasWitnessReaction = false

  /** The player chose to react to being a witness. */
  final def reactToWitness(origin: Card) = {
    reveal()
    specificReactToWitness(origin)
    tap()
  }
  def specificReactToWitness(origin: Card) = {}

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
  def activate(target: Card, gs: GameState): Unit = {
    reveal()
    specificActivate(target, gs)
    tap()
  }
  def specificActivate(target: Card, gs: GameState): Unit = specificActivate(target)
  def specificActivate(target: Card): Unit = Unit
}

trait WithoutTarget extends Card {
  def activate(gs: GameState): Unit = {
    reveal()
    specificActivate(gs)
    tap()
  }
  def specificActivate(gs: GameState): Unit = specificActivate()
  def specificActivate(): Unit = Unit
}

/** only public informations */
class CardPublicState(val cardType: Option[CardType], val family: FamilyPublicState, val cash: Cash, val canAct: Boolean, val tapped: Boolean)