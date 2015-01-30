package fr.renoux.nightbirds.rules.state;

import org.junit.Assert
import org.junit.Before
import org.junit.Test

import fr.renoux.nightbirds.rules.cardtypes.Pink

class CardTest {

  var family: Family = null
  var card: Card = null

  @Before
  def prepare = {
    family = new Family(Pink)
    card = new BlankCard(family)

    Assert.assertEquals(Cash(10), family.cash)
    Assert.assertEquals(Cash.Zero, card.cash)
  }

  @Test
  def testStore = {
    card.store(Cash(3))
    Assert.assertEquals(Cash(3), card.cash)
    card.store(Cash(2))
    Assert.assertEquals(Cash(5), card.cash)
    card.store(Cash.Zero)
    Assert.assertEquals(Cash(5), card.cash)
  }

  @Test
  def testTake = {
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
  def testHit = {
    card.store(Cash(5))
    card.hit()
    Assert.assertEquals(Cash(0), card.cash)
    Assert.assertEquals(Cash(9), family.cash)
    Assert.assertEquals(false, card.canAct)
  }

  def testSleep = {
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
  def testPublic = {
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
    card.asInstanceOf[WithoutTarget].activate()
    assertPublic(card)
  }

  private def assertPublic(card: Card) = {
    Assert.assertEquals(card.cash, card.public.cash)
    Assert.assertEquals(card.family.cash, card.public.family.cash)
    Assert.assertEquals(card.canAct, card.public.canAct)
    Assert.assertEquals(card.tapped, card.public.tapped)
  }

}

object BlankCardType extends CardType(Legal)
class BlankCard(f: Family) extends Card(f)(BlankCardType) with WithoutTarget {
  override def activate() = {
    store(Cash(2))
  }
}