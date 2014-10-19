package fr.renoux.nightbirds.playercontract

sealed abstract class Activation

/* do not activate */ 
object Declined extends Activation

/* activate */
sealed abstract class Accepted extends Activation
object AcceptedWithoutTarget extends Accepted
case class AcceptedWithTarget(target : Target) extends Accepted
case class AcceptedTaxi(target : Target, district : Int, taxiFirst : Boolean) extends Accepted
case class AcceptedPrivateEye(target1 : Target, target2 : Target) extends Accepted
 