package fr.renoux.nightbirds.rules.state

/** An amount of cash. Immutable. */
case class Cash(val amount: Int) extends Ordered[Cash] {
  if (amount < 0) {
    throw new IllegalArgumentException
  }

  override def compare(that: Cash) = this.amount.compare(that.amount)

  def +(that: Cash) = {
    if (that.isZero) this
    else if (this.isZero) that
    else Cash(this.amount + that.amount)
  }

  /** Returns a couple : what's left (almost like a real substraction), and what you got when substracting */
  def -(that: Cash) = {
    val difference = this.amount - that.amount
    if (difference < 0) Transaction(Cash(0), this, Cash(-difference), false)
    else Transaction(Cash(difference), that, Cash(0), true)
  }

  def isZero = (amount == 0)

  override def toString = amount + "$"

}

object Cash {
  val Zero = Cash(0)
}
