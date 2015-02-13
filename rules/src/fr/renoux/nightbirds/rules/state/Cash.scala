package fr.renoux.nightbirds.rules.state

/** An amount of cash. Immutable. The amount can't be negative. */
sealed case class Cash(val amount: Int) extends Ordered[Cash] {
  if (amount < 0) {
    throw new IllegalArgumentException
  }

  override def compare(that: Cash) = this.amount.compare(that.amount)

  def +(that: Cash) = {
    if (that.isZero) this
    else if (this.isZero) that
    else if (that.isInfinite) Cash.Infinity
    else if (this.isInfinite) Cash.Infinity
    else Cash(this.amount + that.amount)
  }

  /** Returns a couple : what's left (almost like a real substraction), and what you got when substracting */
  def -(that: Cash) = {
    if (this == Cash.Infinity && that == Cash.Infinity) {
      Transaction(Cash.Zero, Cash.Infinity, Cash(0), true)
    } else if (this == Cash.Infinity) {
      Transaction(Cash.Infinity, that, Cash(0), true)
    } else if (that == Cash.Infinity) {
      Transaction(Cash.Zero, this, Cash.Infinity, false)
    } else {
      val difference = this.amount - that.amount
      if (difference < 0) Transaction(Cash(0), this, Cash(-difference), false)
      else Transaction(Cash(difference), that, Cash(0), true)
    }
  }

  lazy val isZero = (amount == 0)

  lazy val isInfinite = (amount == Integer.MAX_VALUE)

  override lazy val toString = this match {
    case Cash.Infinity => "$$$"
    case _ => amount + "$"
  }

  lazy val toFixedString = toString.padTo(Cash.FixedStringLength, ' ')

}

object Cash {
  val Zero = Cash(0)
  val One = Cash(1)
  val Infinity = Cash(Integer.MAX_VALUE)
  val FixedStringLength = 3
}
