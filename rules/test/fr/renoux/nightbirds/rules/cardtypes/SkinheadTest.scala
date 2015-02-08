package fr.renoux.nightbirds.rules.cardtypes;

import org.junit.Assert
import org.junit.Test

import fr.renoux.nightbirds.rules.Rules
import fr.renoux.nightbirds.rules.state.AbstractCardTest
import fr.renoux.nightbirds.rules.state.BlankCard
import fr.renoux.nightbirds.rules.state.Cash
import fr.renoux.nightbirds.rules.state.Family

class SkinheadTest extends AbstractCardTest[Skinhead] {

  override def prepare = {
    card = new Skinhead(family)
  }

  @Test
  def testActivate = {
    card.store(Cash(3))
    otherCard.store(Cash(5))
    card.activate(otherCard)
    card.tap()
    Assert.assertEquals(Cash(3), card.cash)
    Assert.assertEquals(Cash.Zero, otherCard.cash)
    Assert.assertEquals(Cash(10), family.cash)
    Assert.assertEquals(Cash(9), otherFamily.cash)
    Assert.assertFalse(otherCard.canAct)
  }

  @Test
  def testReaction = {
    card.store(Cash(3))
    otherCard.store(Cash(5))
    card.react(otherCard)
    Assert.assertEquals(Cash(3), card.cash)
    Assert.assertEquals(Cash.Zero, otherCard.cash)
    Assert.assertEquals(Cash(10), family.cash)
    Assert.assertEquals(Cash(9), otherFamily.cash)
    Assert.assertFalse(otherCard.canAct)
  }

  /** Does nothing when only targeted */
  @Test
  def testTargeted = {
    card.store(Cash(3))
    otherCard.store(Cash(5))
    card.isTargeted(otherCard)
    Assert.assertEquals(Cash(3), card.cash)
    Assert.assertEquals(Cash(5), otherCard.cash)
    Assert.assertEquals(Cash(10), family.cash)
    Assert.assertEquals(Cash(10), otherFamily.cash)
    Assert.assertTrue(otherCard.canAct)
  }

}