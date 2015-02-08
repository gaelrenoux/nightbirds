package fr.renoux.nightbirds.rules.cardtypes;

import org.junit.Assert
import org.junit.Test

import fr.renoux.nightbirds.rules.Rules
import fr.renoux.nightbirds.rules.state.AbstractCardTest
import fr.renoux.nightbirds.rules.state.Cash
import fr.renoux.nightbirds.rules.state.Family

class BumTest extends AbstractCardTest[Bum] {

  override def prepare = {
    card = new Bum(family)
  }

  @Test
  override def testTake = {
    card.store(Cash(3))
    card.take(Cash(2))
    Assert.assertEquals(Cash(3), card.cash)
    card.take(Cash.Zero)
    Assert.assertEquals(Cash(3), card.cash)
    card.take(Cash(5))
    Assert.assertEquals(Cash(3), card.cash)
    Assert.assertEquals(Cash(10), family.cash)
  }

  @Test
  def testActivate = {
    card.store(Cash(3))
    card.activate()
    Assert.assertEquals(Cash(3 + Rules.BumEarnings), card.cash)
  }

}