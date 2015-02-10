package fr.renoux.nightbirds.rules.cardtypes;

import org.junit.Assert
import org.junit.Test

import fr.renoux.nightbirds.rules.Rules
import fr.renoux.nightbirds.rules.state.AbstractCardTest
import fr.renoux.nightbirds.rules.state.BlankCard
import fr.renoux.nightbirds.rules.state.Cash
import fr.renoux.nightbirds.rules.state.Family

class WhoreTest extends AbstractCardTest[Whore] {

  override def prepare = {
    card = new Whore(family)
  }

  @Test
  def testActivateOnEnough = {
    card.store(Cash(3))
    otherCard.store(Cash(5))
    card.activate(otherCard, gs)
    Assert.assertEquals(Cash(3 + Rules.WhoreEarnings), card.cash)
    Assert.assertEquals(Cash(5 - Rules.WhoreEarnings), otherCard.cash)
  }

  @Test
  def testActivateOnEnoughWithFamily = {
    card.store(Cash(3))
    otherCard.store(Cash.Zero)
    card.activate(otherCard, gs)
    Assert.assertEquals(Cash(3 + Rules.WhoreEarnings), card.cash)
    Assert.assertEquals(Cash.Zero, otherCard.cash)
    Assert.assertEquals(Cash(10 - Rules.WhoreEarnings), otherFamily.cash)
  }

  @Test
  def testActivateOnNotEnough = {
    card.store(Cash(3))
    otherCard.store(Cash.Zero)
    otherFamily.take(Cash(100))
    card.activate(otherCard, gs)
    Assert.assertEquals(Cash(3), card.cash)
    Assert.assertEquals(Cash.Zero, otherCard.cash)
  }

}