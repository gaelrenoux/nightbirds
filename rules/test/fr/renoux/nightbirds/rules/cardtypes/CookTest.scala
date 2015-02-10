package fr.renoux.nightbirds.rules.cardtypes;

import org.junit.Assert
import org.junit.Test

import fr.renoux.nightbirds.rules.Rules
import fr.renoux.nightbirds.rules.state.AbstractCardTest
import fr.renoux.nightbirds.rules.state.Cash
import fr.renoux.nightbirds.rules.state.Family

class CookTest extends AbstractCardTest[Cook] {

  override def prepare = {
    card = new Cook(family)
  }

  @Test
  def testActivate = {
    card.store(Cash(3))
    card.activate(gs)
    Assert.assertEquals(Cash(3 + Rules.CookEarnings), card.cash)
  }

}