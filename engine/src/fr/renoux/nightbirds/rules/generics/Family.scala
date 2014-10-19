package fr.renoux.nightbirds.rules.generics

import fr.renoux.nightbirds.rules.Color

case class Family(val color: Color) {

  var internalCash: Cash = Cash(0)
  
  def cash = internalCash

  val prison: collection.mutable.Set[Card] = collection.mutable.Set()

  def take(amount: Cash) = {
    val t = internalCash - amount
    internalCash = t.remaining
    t.subtracted
  }

  /** Store an amount of cash on this card */
  def store(amount: Cash) = {
    internalCash += amount
  }

  def hold(target: Card) {
    prison.add(target)
    target.hold(this)
  }

  def release(target: Card) {
    val bail = target.bail
    if (target.family.internalCash < bail) {
      throw new IllegalArgumentException
    }
    if (prison.remove(target)) {
      internalCash = internalCash + target.bail
      target.family.store((target.family.internalCash - target.bail).remaining)
      target.free()
    } else {
      throw new IllegalArgumentException
    }
  }
}