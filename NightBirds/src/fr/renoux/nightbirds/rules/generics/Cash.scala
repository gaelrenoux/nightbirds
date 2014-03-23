package fr.renoux.nightbirds.rules.generics

case class Cash(val amount: Int) extends Ordered[Cash] {

  override def compare(that: Cash) = this.amount.compare(that.amount)

  def +(that: Cash) = Cash(this.amount + that.amount)

  /** Returns a couple : what's left (almost like a real substraction), and what you got when substracting */
  def -(that: Cash) = {
    val difference = this.amount - that.amount
    if (difference < 0) Transaction(Cash(0), this, Cash(-difference), false)
    else Transaction(Cash(difference), that, Cash(0), true)
  }

}

/** Result of taking cash from cash. Returns what is remaining (as a normal subtraction), what was really taken, and what could not be taken */
case class Transaction(val remaining: Cash, val subtracted: Cash, val notSubtracted: Cash, val successful : Boolean)
