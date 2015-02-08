package fr.renoux.nightbirds.rules.cardtypes;

import org.junit.Assert
import org.junit.Test

import fr.renoux.nightbirds.rules.Rules
import fr.renoux.nightbirds.rules.state.AbstractCardTest
import fr.renoux.nightbirds.rules.state.Cash
import fr.renoux.nightbirds.rules.state.Family

class BurglarTest extends AbstractCardTest[Burglar] {

  override def prepare = {
    card = new Burglar(family)
  }

  @Test
  def testActivateUndisturbed = {
    card.store(Cash(3))
    Assert.assertEquals(Cash(3), card.cash)
    card.activate()
    card.tap()
    Assert.assertEquals(Cash(3 + Rules.BurglarEarnings), card.cash)
  }

  @Test
  def testActivateDisturbedBefore = {
    card.store(Cash(3))
    Assert.assertEquals(Cash(3), card.cash)
    card.isTargeted(otherCard)
    Assert.assertEquals(Cash(3), card.cash)
    card.activate()
    card.tap()
    Assert.assertEquals(Cash(3 + Rules.BurglarEarnings - Rules.BurglarLossOnDisturbance), card.cash)
  }

  @Test
  def testActivateDisturbedAfter = {
    card.store(Cash(3))
    Assert.assertEquals(Cash(3), card.cash)
    card.activate()
    card.tap()
    Assert.assertEquals(Cash(3 + Rules.BurglarEarnings), card.cash)
    card.isTargeted(otherCard)
    Assert.assertEquals(Cash(3 + Rules.BurglarEarnings - Rules.BurglarLossOnDisturbance), card.cash)
  }

  @Test
  def testActivateDisturbedBoth = {
    card.store(Cash(3))
    Assert.assertEquals(Cash(3), card.cash)
    card.isTargeted(otherCard)
    Assert.assertEquals(Cash(3), card.cash)
    card.activate()
    card.tap()
    Assert.assertEquals(Cash(3 + Rules.BurglarEarnings - Rules.BurglarLossOnDisturbance), card.cash)
    card.isTargeted(otherCard)
    val left = math.max(0, Rules.BurglarEarnings - 2 * Rules.BurglarLossOnDisturbance)
    Assert.assertEquals(Cash(3 + left), card.cash)
    Assert.assertEquals(Cash(10), family.cash)
  }

}