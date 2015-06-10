package fr.renoux.nightbirds.rules.state;

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import fr.renoux.nightbirds.rules.cardtypes.Pink
import fr.renoux.nightbirds.rules.cardtypes.Kaki

/** Weird, but specifying card as an argument with default value null works, however defining it as an attribute with initial value null doesn't compile */
abstract class AbstractCardTest[C <: Card](var card: C = null) {
  var district: District = null
  var otherDistrict: District = null

  var otherCard: BlankCard = null
  var otherLegalCard: LegalBlankCard = null
  var otherIllegalCard: IllegalBlankCard = null

  var family: Family = null
  var otherFamily: Family = null

  var gs: GameState = new GameState(null, null)

  @Before
  final def genericPrepare() = {
    district = new District(0)
    otherDistrict = new District(0)
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

  def prepare(): Unit

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
  def testTakeIfAvailable() = {
    card.store(Cash(5))
    card.takeIfAvailable(Cash(2))
    Assert.assertEquals(Cash(3), card.cash)
    card.takeIfAvailable(Cash.Zero)
    Assert.assertEquals(Cash(3), card.cash)
    card.takeIfAvailable(Cash(5))
    Assert.assertEquals(Cash(0), card.cash)
    Assert.assertEquals(Cash(10), family.cash)
  }

  @Test
  def testTakeOut() = {
    card.store(Cash(5))
    card.takeOut()
    Assert.assertEquals(Cash(5), card.cash)
    Assert.assertEquals(Cash(10), family.cash)
    Assert.assertEquals(true, card.out)
    Assert.assertEquals(false, card.canAct)
  }

  @Test
  def testSleep() = {
    activate(card)
    card.pass()
    card.takeOut()
    card.takeIfAvailable(Cash.Infinity)
    card.store(Cash(5))
    Assert.assertEquals(Cash(5), card.cash)
    Assert.assertEquals(Cash(10), family.cash)
    Assert.assertEquals(true, card.out)
    Assert.assertEquals(true, card.tapped)
    Assert.assertEquals(true, card.revealed)
    Assert.assertEquals(false, card.canAct)
    card.sleep()
    Assert.assertEquals(Cash(0), card.cash)
    Assert.assertEquals(Cash(15), family.cash)
    Assert.assertEquals(false, card.out)
    Assert.assertEquals(false, card.tapped)
    Assert.assertEquals(true, card.canAct)
    Assert.assertEquals(false, card.tapped)
    Assert.assertEquals(false, card.revealed)
  }

  @Test
  def testPlace() = {
    district.clear()
    otherDistrict.clear()
    card.sleep()
    otherCard.sleep()
    otherLegalCard.sleep()
    
    card.place(district)
    otherCard.place(district)
    otherLegalCard.place(otherDistrict)
    Assert.assertEquals(Some(Position(district, 0)), card.position)
    Assert.assertEquals(Some(Position(district, 1)), otherCard.position)
    Assert.assertEquals(Some(Position(otherDistrict, 0)), otherLegalCard.position)
    card.place(otherDistrict)
    Assert.assertEquals(Some(Position(otherDistrict, 1)), card.position)
    Assert.assertEquals(Some(Position(district, 1)), otherCard.position)
    Assert.assertEquals(Some(Position(otherDistrict, 0)), otherLegalCard.position)
    Assert.assertEquals(None, district(0))
  }

  @Test
  def testToFixedString() = {
    Assert.assertEquals(Card.FixedStringLength, card.toFixedString.length())
  }

  @Test
  def testPublic() = {
    card.store(Cash(5))
    assertPublic(card)
    card.take(Cash(2))
    assertPublic(card)
    card.takeIfAvailable(Cash(1))
    assertPublic(card)
    card match {
      case wot: WithoutTarget => wot.activate(gs)
      case wt: WithTarget => wt.activate(otherCard, gs)
    }
    assertPublic(card)
  }

  protected def activate(card: Card) = card match {
    case wt: WithTarget => wt.activate(otherCard, gs)
    case wot: WithoutTarget => wot.activate(gs)
  }

  protected def assertPublic(card: Card) = {
    Assert.assertEquals(card.cash, card.public.cash)
    Assert.assertEquals(card.family.cash, card.public.family.cash)
    Assert.assertEquals(card.out, card.public.out)
    Assert.assertEquals(card.tapped, card.public.tapped)
  }

}
