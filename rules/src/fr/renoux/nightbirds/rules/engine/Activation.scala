package fr.renoux.nightbirds.rules.engine

import fr.renoux.nightbirds.rules.state.DistrictPublicState
import fr.renoux.nightbirds.rules.state.Neighbour

sealed abstract class Activation

trait WorksWithoutTarget extends Activation
trait WorksWithTarget extends Activation 
trait WorksWithTaxi extends Activation

object Pass extends Activation with WorksWithoutTarget with WorksWithTarget with WorksWithTaxi
object ActivateWithoutTarget extends Activation with WorksWithoutTarget
case class ActivateWithTarget(val target : Neighbour) extends Activation with WorksWithTarget
case class ActivateTaxi(val target : Neighbour, val district : DistrictPublicState, val targetSide : Neighbour) extends Activation with WorksWithTaxi
