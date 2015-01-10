package old.fr.renoux.nightbirds.rules.generics

trait CardState {

  def family: Family

  /** This card's personal cash */
  private var _cash: Cash = Cash(0)
  final def cash = _cash

  /** Store an amount of cash on this card */
  final def store(amount: Cash) = {
    _cash += amount
  }

  /** Take an amount from this card. If not enough cash, takes from the family. Returns how much could be taken. */
  def take(amount: Cash): Cash = {
    val transaction = cash - amount
    _cash = transaction.remaining
    
    if (!transaction.successful) {
      transaction.subtracted + family.take(transaction.notSubtracted)
    } else {
      amount
    }
  }

  /** All money is removed from this card, but no one gets it. */
  def confiscate() = {
    _cash = Cash(0)
  }

  /** What the family gains from this card when asking nicely at the end of the night. */
  final def profit(): Cash = {
    val available = _cash
    _cash = Cash(0)
    available;
  }

  /** How much is this character hurt ? 0 is not hurt, decreases one rank during the day. */
  private var _hurtLevel = 0
  final def hurt = _hurtLevel > 0

  /** Hit this card. Ouch. */
  final def hit() = {
    confiscate()
    /* he's going to miss next night */
    _hurtLevel = 2
  }

  /** Recover after a good day's sleep. */
  final def recover() = {
    if (hurt) _hurtLevel = _hurtLevel - 1
  }

  /** Who holds this character prisoner ?*/
  private var _captor: Option[Family] = None
  final def captor = _captor
  final def held = (_captor != None)

  /** Put this card in prison */
  def hold(captor: Family) = {
    _captor = Some(captor)
  }

  /** Get this card out of prison */
  def free() = {
    _captor = None
  }

  final def available = !hurt && !held

}