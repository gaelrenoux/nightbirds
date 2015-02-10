package fr.renoux.nightbirds.rules.cardtypes;

import org.junit.Assert
import org.junit.Test
import fr.renoux.nightbirds.rules.state.AbstractCardTest
import fr.renoux.nightbirds.rules.state.Cash
import fr.renoux.nightbirds.rules.Rules

class CopTest extends AbstractCardTest[Cop] {

  override def prepare = {
    card = new Cop(family)
  }

  @Test
  def testActivateIllegal = {
    card.store(Cash(3))
    otherIllegalCard.store(Cash(5))
    card.activate(otherIllegalCard, gs)
    Assert.assertEquals(Cash(3 + Rules.CopEarnings), card.cash)
    Assert.assertEquals(Cash(5 - Rules.CopEarnings), otherIllegalCard.cash)
    Assert.assertEquals(Cash(10), family.cash)
    Assert.assertEquals(Cash(10), otherFamily.cash)
    Assert.assertFalse(otherIllegalCard.out)
    Assert.assertTrue(otherIllegalCard.canAct)
  }

  @Test
  def testActivateLegal = {
    card.store(Cash(3))
    otherLegalCard.store(Cash(5))
    card.activate(otherLegalCard, gs)
    Assert.assertEquals(Cash(3), card.cash)
    Assert.assertEquals(Cash(5), otherLegalCard.cash)
    Assert.assertEquals(Cash(10), family.cash)
    Assert.assertEquals(Cash(10), otherFamily.cash)
    Assert.assertFalse(otherIllegalCard.out)
    Assert.assertTrue(otherIllegalCard.canAct)
  }

  @Test
  def testReactionIllegal = {
    card.store(Cash(3))
    otherIllegalCard.store(Cash(5))
    card.reactToTargeted(otherIllegalCard)
    Assert.assertEquals(Cash(3), card.cash)
    Assert.assertEquals(Cash.Zero, otherIllegalCard.cash)
    Assert.assertEquals(Cash(10 + Rules.CopBail), family.cash)
    Assert.assertEquals(Cash(10 - Rules.CopBail), otherFamily.cash)
    Assert.assertTrue(otherIllegalCard.out)
    Assert.assertFalse(otherIllegalCard.canAct)
  }

  @Test
  def testReactionLegal = {
    card.store(Cash(3))
    otherLegalCard.store(Cash(5))
    card.reactToTargeted(otherLegalCard)
    Assert.assertEquals(Cash(3), card.cash)
    Assert.assertEquals(Cash(5), otherLegalCard.cash)
    Assert.assertEquals(Cash(10), family.cash)
    Assert.assertEquals(Cash(10), otherFamily.cash)
    Assert.assertTrue(otherLegalCard.canAct)
  }

  /** Does nothing when only targeted */
  @Test
  def testTargeted = {
    card.store(Cash(3))
    otherIllegalCard.store(Cash(5))
    card.isTargeted(otherIllegalCard)
    Assert.assertEquals(Cash(3), card.cash)
    Assert.assertEquals(Cash(5), otherIllegalCard.cash)
    Assert.assertEquals(Cash(10), family.cash)
    Assert.assertEquals(Cash(10), otherFamily.cash)
    Assert.assertTrue(otherIllegalCard.canAct)
  }

  @Test
  def testReactionWitnessIllegal = {
    card.store(Cash(3))
    otherIllegalCard.store(Cash(5))
    card.reactToWitness(otherIllegalCard)
    Assert.assertEquals(Cash(3), card.cash)
    Assert.assertEquals(Cash.Zero, otherIllegalCard.cash)
    Assert.assertEquals(Cash(10 + Rules.CopBail), family.cash)
    Assert.assertEquals(Cash(10 - Rules.CopBail), otherFamily.cash)
    Assert.assertTrue(otherIllegalCard.out)
    Assert.assertFalse(otherIllegalCard.canAct)
  }

  @Test
  def testReactionWitnessLegal = {
    card.store(Cash(3))
    otherLegalCard.store(Cash(5))
    card.reactToWitness(otherLegalCard)
    Assert.assertEquals(Cash(3), card.cash)
    Assert.assertEquals(Cash(5), otherLegalCard.cash)
    Assert.assertEquals(Cash(10), family.cash)
    Assert.assertEquals(Cash(10), otherFamily.cash)
    Assert.assertTrue(otherLegalCard.canAct)
  }

  /** Does nothing when only witness */
  @Test
  def testWitness = {
    card.store(Cash(3))
    otherCard.store(Cash(5))
    card.witness(otherCard)
    Assert.assertEquals(Cash(3), card.cash)
    Assert.assertEquals(Cash(5), otherCard.cash)
    Assert.assertEquals(Cash(10), family.cash)
    Assert.assertEquals(Cash(10), otherFamily.cash)
    Assert.assertTrue(otherCard.canAct)
  }

}