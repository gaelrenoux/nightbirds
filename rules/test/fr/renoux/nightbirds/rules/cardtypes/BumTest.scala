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
  override def testTakeIfAvailable = {
    card.store(Cash(3))
    card.takeIfAvailable(Cash(2))
    Assert.assertEquals(Cash(3), card.cash)
    card.takeIfAvailable(Cash.Zero)
    Assert.assertEquals(Cash(3), card.cash)
    card.takeIfAvailable(Cash(5))
    Assert.assertEquals(Cash(3), card.cash)
    Assert.assertEquals(Cash(10), family.cash)
  }

  @Test
  override def testSleep() = {
    activate(card)
    card.pass()
    card.takeOut()
    card.takeIfAvailable(Cash.Infinity)
    card.store(Cash(5))
    Assert.assertEquals(Cash(5 + Rules.BumEarnings), card.cash)
    Assert.assertEquals(Cash(10), family.cash)
    Assert.assertEquals(true, card.out)
    Assert.assertEquals(true, card.tapped)
    Assert.assertEquals(true, card.revealed)
    Assert.assertEquals(false, card.canAct)
    card.sleep()
    Assert.assertEquals(Cash(0), card.cash)
    Assert.assertEquals(Cash(15 + Rules.BumEarnings), family.cash)
    Assert.assertEquals(false, card.out)
    Assert.assertEquals(false, card.tapped)
    Assert.assertEquals(true, card.canAct)
    Assert.assertEquals(false, card.tapped)
    Assert.assertEquals(false, card.revealed)
  }

  @Test
  def testActivate = {
    card.store(Cash(3))
    card.activate(gs)
    Assert.assertEquals(Cash(3 + Rules.BumEarnings), card.cash)
  }

}