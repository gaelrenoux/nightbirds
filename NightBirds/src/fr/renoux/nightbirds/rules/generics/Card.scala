package fr.renoux.nightbirds.rules.generics

abstract class Card(val callbacks: Callbacks, val board: Board, val family: Family, val guts: Guts = Guts(0), val legality: Legality = Legal) extends CardState {

  /* Card definition */

  /** true if this card can be arrested by the cop */
  final def isReprehensible = (legality == Illegal)

  /**  How much does it cost to get him out ?  */
  final def bail = Cash(guts.value + 1);

  /** How much will he pay for drug ? */
  final def addiction = Cash(guts.value + 1);

  /* Card status in the current turn */

  /** has the card been seen yet ? */
  private var _revealed = false
  final def revealed = _revealed

  /** Has he played his turn yet ? */
  var _hasPlayed = false
  final def hasPlayed = _hasPlayed

  /* Actions on or with this card */

  def reveal(): Unit = {
    _revealed = true

    _delayedWitnesses.reverse.foreach { witness(_) }
    _delayedWitnesses = Nil

    /* if the card is not available or the player doesn't activate it - do nothing */
    if (!available || !callbacks.activate(this)) return

    val witnesses = activate()

    witnesses.early.foreach { _.witness(this) }
    witnesses.late.foreach { _.witness(this) }

    _hasPlayed = true
  }

  /** Activation of the card. Most important action. Different for different types of cards */
  def activate(): Neighbours

  private var _delayedWitnesses: List[Card] = Nil
  /** Witness something happening. Don't do anything right now if the card is not revealed. */
  final def witness(source: Card) = {
    if (_revealed) {
      if (callbacks.reactToTarget(this, source)) {
        doWitness(source)
      }
    } else {
       _delayedWitnesses ::= source
    }
  }

  /** Heal during the day */
  final def sleep() = {
    doReset()
    recover()
    _hasPlayed = false
  }

  final def targeted(source: Card) = {
    doTargeted(source)
  }

  /* Things specific cards wish to implement */

  /** Witness something happening. Don't do anything in the general case. You're not a witness if you're the target. */
  protected def doWitness(source: Card): Unit = {}

  /** Be targeted by something. Don't do anything in the general case. Returns true if the source may go on. */
  protected def doTargeted(source: Card) = true

  /** Specific reset for a card. Nothing in the general case. */
  protected def doReset() = {}

}