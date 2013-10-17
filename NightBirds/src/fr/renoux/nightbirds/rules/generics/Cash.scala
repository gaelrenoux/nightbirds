package fr.renoux.nightbirds.rules.generics

sealed abstract class Cash extends Ordered[Cash] {
  def +(that: Cash) : Cash
  def -(that: Cash) : Transaction
}

object Cash {
  def apply(amount: Int) = RealCash(amount)
}

class Transaction(val remaining : Cash, val subtracted : Cash) extends Tuple2(remaining, subtracted)

case class RealCash(val amount: Int) extends Cash {
  override def compare(that: Cash) = that match {
    case Everything => -1
    case RealCash(thatAmount) => this.amount.compare(thatAmount)
  }

  override def +(that: Cash) = that match {
    case Everything => Everything
    case RealCash(thatAmount) => RealCash(this.amount + thatAmount)
  }

  /** Returns a couple : what's left (almost like a real substraction), and what you got */
  override def -(that: Cash) = that match {
    case Everything => new Transaction(Cash(0), this)
    case RealCash(thatAmount) if this.amount < thatAmount => new Transaction(Cash(0), this)
    case RealCash(thatAmount) if this.amount >= thatAmount => new Transaction(RealCash(this.amount - thatAmount), that)
  }
}

object Everything extends Cash {
  override def compare(that: Cash) = that match {
    case Everything => 0
    case _ => 1
  }

  override def +(that: Cash) = Everything

  /** Returns a couple : what's left (almost like a real substraction), and what you got */
  override def -(that: Cash) = that match {
    case Everything => new Transaction(Everything, Everything)
    case RealCash(thatAmount) => new Transaction(Everything, that)
  }

}