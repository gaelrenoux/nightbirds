package fr.renoux.nightbirds.rules.cardtypes;

import org.junit.Assert
import org.junit.Test
import fr.renoux.nightbirds.rules.Rules
import fr.renoux.nightbirds.rules.state.AbstractCardTest
import fr.renoux.nightbirds.rules.state.Cash
import fr.renoux.nightbirds.rules.state.RightNeighbour
import fr.renoux.nightbirds.rules.state.LeftNeighbour

class TaxiTest extends AbstractCardTest[Taxi] {

  override def prepare = {
    card = new Taxi(family)
  }

  @Test
  def testActivateRight = {
    card.place(district)
    otherCard.place(district)
    otherLegalCard.place(district)
    otherIllegalCard.place(otherDistrict)
    Assert.assertEquals(Some(card), district(0))
    Assert.assertEquals(Some(otherCard), district(1))
    Assert.assertEquals(Some(otherLegalCard), district(2))
    Assert.assertEquals(Some(otherIllegalCard), otherDistrict(0))
    card.activate(otherCard, otherDistrict, RightNeighbour, gs)
    Assert.assertEquals(None, district(0))
    Assert.assertEquals(None, district(1))
    Assert.assertEquals(Some(otherLegalCard), district(2))
    Assert.assertEquals(Some(otherIllegalCard), otherDistrict(0))
    Assert.assertEquals(Some(card), otherDistrict(1))
    Assert.assertEquals(Some(otherCard), otherDistrict(2))
  }

  @Test
  def testActivateLeft = {
    card.place(district)
    otherCard.place(district)
    otherLegalCard.place(district)
    otherIllegalCard.place(otherDistrict)
    Assert.assertEquals(Some(card), district(0))
    Assert.assertEquals(Some(otherCard), district(1))
    Assert.assertEquals(Some(otherLegalCard), district(2))
    Assert.assertEquals(Some(otherIllegalCard), otherDistrict(0))
    card.activate(otherCard, otherDistrict, LeftNeighbour, gs)
    Assert.assertEquals(None, district(0))
    Assert.assertEquals(None, district(1))
    Assert.assertEquals(Some(otherLegalCard), district(2))
    Assert.assertEquals(Some(otherIllegalCard), otherDistrict(0))
    Assert.assertEquals(Some(otherCard), otherDistrict(1))
    Assert.assertEquals(Some(card), otherDistrict(2))
  }

  @Test
  def testMoneyMoved = {
    card.place(district)
    otherCard.place(district)
    card.store(Cash(3))
    otherCard.store(Cash(5))
    card.activate(otherCard, otherDistrict, RightNeighbour, gs)
    Assert.assertEquals(Cash(3), card.cash)
    Assert.assertEquals(Cash(5), otherCard.cash)
  }

}