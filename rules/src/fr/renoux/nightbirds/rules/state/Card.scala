package fr.renoux.nightbirds.rules.state

import fr.renoux.nightbirds.rules.Rules
import fr.renoux.nightbirds.rules.cardtypes.Color

/** Card in the game. */
class Card(val family: Family)(val cardType: CardType) {

  val legality: Legality = cardType.legality

  private var _cash = Cash.Zero
  def cash = _cash

  private var _revealed = false
  def revealed = _revealed

  private var _position: Option[Position] = None
  def position = _position

  /** true when a card has been used (either by an activation or by a reaction)  */
  private var _tapped = false
  def tapped = _tapped
  protected def tap() = { _tapped = true; resetPublicState() }

  /** True if the card declined to activate */
  private var _passed = false
  def passed = _passed
  def pass() = { _passed = true }

  /** true when a card have been sent out (jail or hospital) */
  private var _out = false
  def out = _out

  def canAct = !tapped && !out

  def canBeActivated = canAct && !passed

  /** Place this card at the end of a district. Remove it from its current position, if any, and leaves a empty space there. */
  def place(district: District) = {
    _position foreach { p =>
      p.district.remove(this)
    }
    district.append(this)
    _position = Some(Position(district, district.size - 1))
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

  def takeOut() = {
    _out = true
    resetPublicState()
  }

  /** Is targeted and stuff may happens. Player can't refuse to. */
  final def isTargeted(origin: Card) = specificIsTargeted(origin)
  protected def specificIsTargeted(origin: Card) = {}

  /** Does this card do something when targeted ? */
  val hasTargetedReaction = false

  /** The player chose to react to being targeted */
  final def reactToTargeted(origin: Card) = {
    reveal()
    specificReactToTargeted(origin)
    tap()
  }
  protected def specificReactToTargeted(origin: Card) = {}

  /** Is witness and stuff may happens. Player can't refuse to. */
  final def witness(origin: Card) = specificWitness(origin)
  protected def specificWitness(origin: Card) = {}

  /** Does this card do something when witness ? */
  val hasWitnessReaction = false

  /** The player chose to react to being a witness. */
  final def reactToWitness(origin: Card) = {
    reveal()
    specificReactToWitness(origin)
    tap()
  }
  protected def specificReactToWitness(origin: Card) = {}

  /** At the end of the night */
  def sleep() = {
    family.store(_cash)
    _cash = Cash.Zero
    _revealed = false
    _position = None
    _out = false
    _tapped = false
    _passed = false
    resetPublicState()
  }

  private var _publicState = new CardPublicState(None, family.public, Cash.Zero, true, false)
  private def resetPublicState() = { _publicState = new CardPublicState(if (revealed) Some(cardType) else None, family.public, cash, _out, _tapped) }
  def public = _publicState

  override def toString = {
    val builder = new StringBuilder(cardType.toString)
    builder.append("(").append(family.color).append(", ").append(_cash).append(")")
    builder.toString()
  }

  def toFixedString = {
    val builder = new StringBuilder(cardType.toFixedString)
    builder.append("(").append(family.color.toFixedString)
    builder.append(" ").append(_cash.toFixedString)
    builder.append(" ")
    builder.append(if (_tapped) "T" else " ")
    builder.append(if (_passed) "P" else " ")
    builder.append(if (_out) "O" else " ")
    builder.append(")")
    builder.toString()
  }

}

object Card {
  val FixedStringLength = CardType.FixedStringLength + Color.FixedStringLength + Cash.FixedStringLength + 7
}

/** only public informations */
class CardPublicState(val cardType: Option[CardType], val family: FamilyPublicState, val cash: Cash, val out: Boolean, val tapped: Boolean)