package old.fr.renoux.nightbirds.rules.generics

abstract class Activation

object DeclinedActivation extends Activation
case class BlockedActivation(val source: Card, val target: Card, val info: Option[Any] = None) extends Activation
case class SuccessfulActivation(val source: Card, val target: Option[Card], val witnesses: Neighbours, val info: Option[Any] = None)
  extends Activation 