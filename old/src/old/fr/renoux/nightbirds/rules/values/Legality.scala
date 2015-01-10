package old.fr.renoux.nightbirds.rules.values

sealed abstract class Legality
object Legal extends Legality
object Illegal extends Legality