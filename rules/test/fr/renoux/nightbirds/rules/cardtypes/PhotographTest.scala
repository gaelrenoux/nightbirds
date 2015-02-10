package fr.renoux.nightbirds.rules.cardtypes;

import org.junit.Assert
import org.junit.Test

import fr.renoux.nightbirds.rules.Rules
import fr.renoux.nightbirds.rules.state.AbstractCardTest
import fr.renoux.nightbirds.rules.state.Cash
import fr.renoux.nightbirds.rules.state.Family

class PhotographTest extends AbstractCardTest[Photograph] {

  override def prepare = {
    card = new Photograph(family)
  }

  @Test
  def testActivate = {
    card.store(Cash(3))
    card.activate(gs)
    Assert.assertEquals(Cash(3), card.cash)
  }

  @Test
  def testWitnessWhileActive = {
    card.store(Cash(3))
    card.activate(gs)
    Assert.assertEquals(Cash(3), card.cash)
    card.witness(otherCard)
    Assert.assertEquals(Cash(3 + Rules.PhotographEarnings), card.cash)
  }

  @Test
  def testWitnessWhileInactive = {
    card.store(Cash(3))
    card.witness(otherCard)
    Assert.assertEquals(Cash(3), card.cash)
    card.activate(gs)
    Assert.assertEquals(Cash(3 + Rules.PhotographEarnings), card.cash)
  }

  @Test
  def testWitnessTwo = {
    card.store(Cash(3))
    card.witness(otherCard)
    Assert.assertEquals(Cash(3), card.cash)
    card.activate(gs)
    Assert.assertEquals(Cash(3 + Rules.PhotographEarnings), card.cash)
    card.witness(otherCard)
    Assert.assertEquals(Cash(3 + 2*Rules.PhotographEarnings), card.cash)
  }

  @Test
  def testTargeted = {
    card.store(Cash(3))
    card.isTargeted(otherCard)
    Assert.assertEquals(Cash(3), card.cash)
    card.activate(gs)
    Assert.assertEquals(Cash(3), card.cash)
    card.isTargeted(otherCard)
    Assert.assertEquals(Cash(3), card.cash)
  }

}