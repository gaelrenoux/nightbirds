package fr.renoux.nightbirds.rules.state

/** Result of taking cash from cash. Returns what is remaining (as a normal subtraction), what was really taken, and what could not be taken. Immutable */
case class Transaction(val remaining: Cash, val subtracted: Cash, val notSubtracted: Cash, val fullySuccessful: Boolean) {
  if (!fullySuccessful) {
    assert(remaining == Cash.Zero)
    assert(notSubtracted != Cash.Zero)
  } else {
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