package old.fr.renoux.nightbirds.rules.generics

abstract class Card(
  val callbacks: Callbacks,
  val board: Board,
  val family: Family,
  val guts: Guts = Guts(0),
  val legality: Legality = Legal) extends CardState {

  /* Card definition */

  /** true if this card can be arrested by the cop */
  final def isReprehensible = (legality == Illegal)

  /**  How much does it cost to get him out ?  */
  final def bail = Cash(guts.value + 1);

  /** How much will he pay for drug ? */
  final def addiction = Cash(guts.value + 1);

  /** Has it been revealed yet ? */
  var _isRevealed = false
  final def isRevealed = _isRevealed
  
  /** Has it dome something this turn ? */
  var _hasPlayed = false
  final def hasPlayed = _hasPlayed

  /** Heal during the day */
  final def sleep() = {
    doReset()
    recover()
    _hasPlayed = false
    _isRevealed = false
  }

  /* Actions on or with this card */

  /** Main stuff : revealing the card and doing whatever we need */
  def reveal(): Unit = {
    _isRevealed = true
    
    /* if he has been a witness, do the corresponding actions */
    _delayedWitnesses.reverse.foreach { witness(_) }
    _delayedWitnesses = Nil

    /* In case the source changed status during witness action */
    if (!available) return

    activate() match {
      case success: SuccessfulActivation =>
        val firstWitnessAllowsSecond = success.witnesses.early.map { _.witness(success) }
        if (firstWitnessAllowsSecond.getOrElse(true)) {
          success.witnesses.late.foreach { _.witness(success) }
        }
      case _ => ; //do nothing
    }

    _hasPlayed = true
  }

  /** Activation of the card. Most important action. Different for different types of cards. */
  def activate(): Activation

  /** Has been a witness but was not revealed then, will do it on its turn */
  private var _delayedWitnesses: List[SuccessfulActivation] = Nil
  /** Witness something happening. Don't do anything right now if the card is not revealed. */
  final def witness(activation: SuccessfulActivation): Boolean = {

    /* first case : card is already revealed */
    if (_isRevealed) {
      val mw = doMandatoryWitness(activation.source)

      if (callbacks.reactToWitness(activation, this))
        doWitness(activation.source) && mw
      else
        mw
    } /* second case : card has not been revealed yet */ else {
      _delayedWitnesses ::= activation
      true
    }
  }

  /** This card is targeted. Returns true if the source mat go on. */
  final def targeted(source: Card): Boolean = {
    val mt = doMandatoryTargeted(source)

    if (callbacks.reactToTarget(this, source))
      doTargeted(source) && mt
    else
      mt
  }

  /* Things specific cards wish to implement */

  /** Witness something happening. Don't do anything in the general case. You're not a witness if you're the target. Returns true if the next witness may do something. */
  protected def doWitness(source: Card): Boolean = true

  /** Same as doWitness, except the player doesn't have a choice. */
  protected def doMandatoryWitness(source: Card): Boolean = true

  /** Be targeted by something. Don't do anything in the general case. Returns true if the source may go on. */
  protected def doTargeted(source: Card) = true

  /** Same as doTargeted, except the player doesn't have a choice. */
  protected def doMandatoryTargeted(source: Card) = true

  /** Specific reset for a card. Nothing in the general case. */
  protected def doReset() = {}
  
  override def toString() = {
    this.getClass().getSimpleName() + " (" + this.cash  + ")" 
  }

}