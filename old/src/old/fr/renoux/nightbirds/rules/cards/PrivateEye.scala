package old.fr.renoux.nightbirds.rules.cards

import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Callbacks
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.District
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.generics.Guts
import fr.renoux.nightbirds.rules.generics.WithoutTargetWithInfo

/**  */
class PrivateEye(c: Callbacks, b: Board, f: Family) extends Card(c, b, f) with WithoutTargetWithInfo[(District, Int, District, Int)] {
  
  override def doProceed(info : (District, Int, District, Int)) = {
    val (district1, ix1, district2, ix2) = info
    c.see(district1.cards(ix1))
    c.see(district2.cards(ix2))
  }
}


