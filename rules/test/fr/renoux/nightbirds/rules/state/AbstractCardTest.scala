package fr.renoux.nightbirds.rules.state;

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import fr.renoux.nightbirds.rules.cardtypes.Pink
import fr.renoux.nightbirds.rules.cardtypes.Kaki

/** Weird, but specifying card as an argument with default value null works, however defining it as an attribute with initial value null doesn't compile */
abstract class AbstractCardTest[C <: Card](
  var card: C = null,
  var otherCard: BlankCard = null,
  var otherLegalCard: LegalBlankCard = null,
  var otherIllegalCard: IllegalBlankCard = null) {

  var family: Family = null
  var otherFamily: Family = null

  @Before
  final def genericPrepare() = {
    family = new Family(Pink)
    otherFamily = new Family(Kaki)
    otherCard = new LegalBlankCard(otherFamily)
    otherLegalCard = new LegalBlankCard(otherFamily)
    otherIllegalCard = new IllegalBlankCard(otherFamily)
    prepare()

    Assert.assertEquals(Cash(10), family.cash)
    Assert.assertEquals(Cash(10), otherFamily.cash)
    Assert.assertEquals(Cash.Zero, card.cash)
    Assert.assertEquals(Cash.Zero, otherCard.cash)
    Assert.assertEquals(Cash.Zero, otherLegalCard.cash)
    Assert.assertEquals(Cash.Zero, otherIllegalCard.cash)
  }

  def prepare()

  @Test
  def testStore() = {
    card.store(Cash(3))
    Assert.assertEquals(Cash(3), card.cash)
    card.store(Cash(2))
    Assert.assertEquals(Cash(5), card.cash)
    card.store(Cash.Zero)
    Assert.assertEquals(Cash(5), card.cash)
  }

  @Test
  def testTake() = {
    card.store(Cash(5))
    card.take(Cash(2))
    Assert.assertEquals(Cash(3), card.cash)
    card.take(Cash.Zero)
    Assert.assertEquals(Cash(3), card.cash)
    card.take(Cash(5))
    Assert.assertEquals(Cash(0), card.cash)
    Assert.assertEquals(Cash(8), family.cash)
  }

  @Test
  def testHit() = {
    card.store(Cash(5))
    card.hit()
    Assert.assertEquals(Cash(0), card.cash)
    Assert.assertEquals(Cash(9), family.cash)
    Assert.assertEquals(false, card.canAct)
  }

  @Test
  def testSleep() = {
    card.hit()
    card.tap()
    card.reveal()
    card.store(Cash(5))
    Assert.assertEquals(Cash(5), card.cash)
    Assert.assertEquals(Cash(9), family.cash)
    card.sleep()
    Assert.assertEquals(Cash(0), card.cash)
    Assert.assertEquals(Cash(14), family.cash)
    Assert.assertEquals(true, card.canAct)
    Assert.assertEquals(false, card.tapped)
    Assert.assertEquals(false, card.revealed)
  }

  @Test
  def testPublic() = {
    card.store(Cash(5))
    assertPublic(card)
    card.take(Cash(2))
    assertPublic(card)
    card.takeIfAvailable(Cash(1))
    assertPublic(card)
    card.hit()
    assertPublic(card)
    card.tap()
    assertPublic(card)
    card match {
      case wot: WithoutTarget => wot.activate()
      case wt: WithTarget => wt.activate(otherCard)
    }
    assertPublic(card)
  }

  protected def assertPublic(card: Card) = {
    Assert.assertEquals(card.cash, card.public.cash)
    Assert.assertEquals(card.family.cash, card.public.family.cash)
    Assert.assertEquals(card.canAct, card.public.canAct)
    Assert.assertEquals(card.tapped, card.public.tapped)
  }

}
