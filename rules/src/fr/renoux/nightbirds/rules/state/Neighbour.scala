package fr.renoux.nightbirds.rules.state

sealed abstract class Neighbour {
  def apply(p: Position): Option[Position]
  def apply(p: PublicPosition): Option[PublicPosition]
  def opposite: Neighbour
}
object LeftNeighbour extends Neighbour {
  def apply(p: Position) = p.left
  def apply(p: PublicPosition) = p.left
  def opposite = RightNeighbour
  override def toString = "Left"
}
object RightNeighbour extends Neighbour {
  def apply(p: Position) = p.right
  def apply(p: PublicPosition) = p.right
  def opposite = LeftNeighbour
  override def toString = "Right"
}