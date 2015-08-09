package fr.renoux.nightbirds.rules.cardtypes;

import org.junit.Assert
import org.junit.Test
import fr.renoux.nightbirds.rules.Rules
import fr.renoux.nightbirds.rules.state.AbstractCardTest
import fr.renoux.nightbirds.rules.state.Cash
import fr.renoux.nightbirds.rules.state.RightNeighbour
import fr.renoux.nightbirds.rules.state.LeftNeighbour

class PrivateEyeTest extends AbstractCardTest[PrivateEye] {

  override def prepare = {
    card = new PrivateEye(family)
  }

  @Test
  def testActivate = {
    card.store(Cash(2))
    card.activate(gs)
    card.store(Cash(2))
  }

}