package fr.renoux.nightbirds.rules.state

sealed abstract class Legality(val legal : Boolean, val illegal : Boolean)
object Legal extends Legality(true, false)
object Illegal extends Legality(false, true)