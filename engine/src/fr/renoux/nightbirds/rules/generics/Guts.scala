package fr.renoux.nightbirds.rules.generics

case class Guts(value : Int) extends Ordered[Guts] {
  def compare(that: Guts) = this.value.compare(that.value)
}