package fr.renoux.nightbirds.rules.engine

import fr.renoux.nightbirds.rules.state.DistrictPublicState
import fr.renoux.nightbirds.rules.state.Neighbour
import fr.renoux.nightbirds.rules.state.PublicPosition
import fr.renoux.nightbirds.rules.state.PublicPosition

sealed abstract class Activation

trait WorksWithoutTarget extends Activation
trait WorksWithTarget extends Activation 
trait WorksWithTaxi extends Activation
trait WorksWithPrivateEye extends Activation

object Pass extends Activation with WorksWithoutTarget with WorksWithTarget with WorksWithTaxi with WorksWithPrivateEye
object ActivateWithoutTarget extends Activation with WorksWithoutTarget
case class ActivateWithTarget(val target : Neighbour) extends Activation with WorksWithTarget
case class ActivateTaxi(val target : Neighbour, val district : DistrictPublicState, val targetSide : Neighbour) extends Activation with WorksWithTaxi
case class ActivatePrivateEye(val target1 : PublicPosition, val target2: Option[PublicPosition]) extends Activation with WorksWithPrivateEye
