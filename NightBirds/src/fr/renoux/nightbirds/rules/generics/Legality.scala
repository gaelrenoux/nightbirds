package fr.renoux.nightbirds.rules.generics

sealed abstract class Legality
object Legal extends Legality
object Illegal extends Legality