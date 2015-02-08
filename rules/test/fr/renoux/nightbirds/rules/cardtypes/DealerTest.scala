package fr.renoux.nightbirds.rules.cardtypes;

import org.junit.Assert
import org.junit.Test

import fr.renoux.nightbirds.rules.Rules
import fr.renoux.nightbirds.rules.state.AbstractCardTest
import fr.renoux.nightbirds.rules.state.Cash

class DealerTest extends AbstractCardTest[Dealer] {
  
  override def prepare = {
    card = new Dealer(family)
  }

  @Test
  def testActivateOnLegalEnough = {
    card.store(Cash(3))
    otherLegalCard.store(Cash(5))
    card.activate(otherLegalCard)
    Assert.assertEquals(Cash(3 + Rules.DealerLegalEarnings), card.cash)
    Assert.assertEquals(Cash(5 - Rules.DealerLegalEarnings), otherLegalCard.cash)
  }

  @Test
  def testActivateOnLegalEnoughWithFamily = {
    card.store(Cash(3))
    otherLegalCard.store(Cash.Zero)
    card.activate(otherLegalCard)
    Assert.assertEquals(Cash(3 + Rules.DealerLegalEarnings), card.cash)
    Assert.assertEquals(Cash.Zero, otherLegalCard.cash)
    Assert.assertEquals(Cash(10 - Rules.DealerLegalEarnings), otherFamily.cash)
  }

  @Test
  def testActivateOnLegalNotEnough = {
    card.store(Cash(3))
    otherLegalCard.store(Cash.Zero)
    otherFamily.take(Cash(100))
    card.activate(otherLegalCard)
    Assert.assertEquals(Cash(3), card.cash)
    Assert.assertEquals(Cash.Zero, otherLegalCard.cash)
  }

  @Test
  def testActivateOnIllegalEnough = {
    card.store(Cash(3))
    otherIllegalCard.store(Cash(5))
    card.activate(otherIllegalCard)
    Assert.assertEquals(Cash(3 + Rules.DealerIllegalEarnings), card.cash)
    Assert.assertEquals(Cash(5 - Rules.DealerIllegalEarnings), otherIllegalCard.cash)
  }

  @Test
  def testActivateOnIllegalEnoughWithFamily = {
    card.store(Cash(3))
    otherIllegalCard.store(Cash.Zero)
    card.activate(otherIllegalCard)
    Assert.assertEquals(Cash(3 + Rules.DealerIllegalEarnings), card.cash)
    Assert.assertEquals(Cash.Zero, otherIllegalCard.cash)
    Assert.assertEquals(Cash(10 - Rules.DealerIllegalEarnings), otherFamily.cash)
  }

  @Test
  def testActivateOnIllegalNotEnough = {
    card.store(Cash(3))
    otherIllegalCard.store(Cash.Zero)
    otherFamily.take(Cash(100))
    card.activate(otherIllegalCard)
    Assert.assertEquals(Cash(3), card.cash)
    Assert.assertEquals(Cash.Zero, otherIllegalCard.cash)
  }



}