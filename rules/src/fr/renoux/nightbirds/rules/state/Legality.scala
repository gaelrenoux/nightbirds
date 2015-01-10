package fr.renoux.nightbirds.rules.state

sealed abstract class Legality
object Legal extends Legality
object Illegal extends Legality