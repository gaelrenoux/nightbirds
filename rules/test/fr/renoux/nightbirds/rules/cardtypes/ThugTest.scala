package fr.renoux.nightbirds.rules.cardtypes;

import org.junit.Assert
import org.junit.Test
import fr.renoux.nightbirds.rules.Rules
import fr.renoux.nightbirds.rules.state.AbstractCardTest
import fr.renoux.nightbirds.rules.state.BlankCard
import fr.renoux.nightbirds.rules.state.Cash
import fr.renoux.nightbirds.rules.state.Family
import fr.renoux.nightbirds.rules.cardtypes.Thug

class ThugTest extends AbstractCardTest[Thug] {
  
  override def prepare = {
    card = new Thug(family)
  }

  @Test
  def testActivateOnLegalEnough = {
    card.store(Cash(3))
    otherLegalCard.store(Cash(5))
    card.activate(otherLegalCard, gs)
    Assert.assertEquals(Cash(3 + Rules.ThugLegalEarnings), card.cash)
    Assert.assertEquals(Cash(5 - Rules.ThugLegalEarnings), otherLegalCard.cash)
  }

  @Test
  def testActivateOnLegalEnoughWithFamily = {
    card.store(Cash(3))
    otherLegalCard.store(Cash.Zero)
    card.activate(otherLegalCard, gs)
    Assert.assertEquals(Cash(3 + Rules.ThugLegalEarnings), card.cash)
    Assert.assertEquals(Cash.Zero, otherLegalCard.cash)
    Assert.assertEquals(Cash(10 - Rules.ThugLegalEarnings), otherFamily.cash)
  }

  @Test
  def testActivateOnLegalNotEnough = {
    card.store(Cash(3))
    otherLegalCard.store(Cash.Zero)
    otherFamily.take(Cash(100))
    card.activate(otherLegalCard, gs)
    Assert.assertEquals(Cash(3), card.cash)
    Assert.assertEquals(Cash.Zero, otherLegalCard.cash)
  }

  @Test
  def testActivateOnIllegalEnough = {
    card.store(Cash(3))
    otherIllegalCard.store(Cash(5))
    card.activate(otherIllegalCard, gs)
    Assert.assertEquals(Cash(3 + Rules.ThugIllegalEarnings), card.cash)
    Assert.assertEquals(Cash(5 - Rules.ThugIllegalEarnings), otherIllegalCard.cash)
  }

  @Test
  def testActivateOnIllegalEnoughWithFamily = {
    card.store(Cash(3))
    otherIllegalCard.store(Cash.Zero)
    card.activate(otherIllegalCard, gs)
    Assert.assertEquals(Cash(3 + Rules.ThugIllegalEarnings), card.cash)
    Assert.assertEquals(Cash.Zero, otherIllegalCard.cash)
    Assert.assertEquals(Cash(10 - Rules.ThugIllegalEarnings), otherFamily.cash)
  }

  @Test
  def testActivateOnIllegalNotEnough = {
    card.store(Cash(3))
    otherIllegalCard.store(Cash.Zero)
    otherFamily.take(Cash(100))
    card.activate(otherIllegalCard, gs)
    Assert.assertEquals(Cash(3), card.cash)
    Assert.assertEquals(Cash.Zero, otherIllegalCard.cash)
  }



}