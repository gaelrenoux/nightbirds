package fr.renoux.nightbirds.rules.state

/** Result of taking cash from cash. Returns what is remaining (as a normal subtraction), what was really taken, and what could not be taken. Immutable */
case class Transaction(val remaining: Cash, val subtracted: Cash, val notSubtracted: Cash, val fullySuccessful: Boolean) {
  /* there may be money remaining if the transaction was unsuccessful for another reason - see the bum */
  /* Zero can be "not subtracted" on an unsuccessful transaction if zero was the amount we attempted to subtract in the first place from a bum */
  if (fullySuccessful) {
    assert(notSubtracted == Cash.Zero)
  }

  /** The current transaction has been followed by another one, directed to a backup source, to get the money that could not be subtracted in the first place. */
  def backedBy(that: Transaction) = {
    /* checks that the two transactions are indeed correctly related */
    assert(!this.fullySuccessful)
    assert(this.notSubtracted == that.subtracted + that.notSubtracted)

    Transaction(that.remaining, this.subtracted + that.subtracted, that.notSubtracted, that.fullySuccessful)
  }
}