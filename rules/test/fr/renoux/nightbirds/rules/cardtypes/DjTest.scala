package fr.renoux.nightbirds.rules.cardtypes;

import org.junit.Assert
import org.junit.Test
import fr.renoux.nightbirds.rules.Rules
import fr.renoux.nightbirds.rules.state.AbstractCardTest
import fr.renoux.nightbirds.rules.state.Cash
import fr.renoux.nightbirds.rules.state.Family
import fr.renoux.nightbirds.rules.state.District
import fr.renoux.nightbirds.rules.state.BlankCard
import fr.renoux.nightbirds.rules.state.Card
import fr.renoux.nightbirds.rules.state.LegalBlankCard

class DjTest extends AbstractCardTest[Dj] {

  var district: District = null
  var myOtherCard: Card = null
  var myAnotherCard: Card = null
  var againAnotherFamily: Family = null
  var againAnotherCard: Card = null

  override def prepare = {
    card = new Dj(family)
    myOtherCard = new LegalBlankCard(family)
    myAnotherCard = new LegalBlankCard(family)

    againAnotherFamily = new Family(Taupe)
    againAnotherCard = new LegalBlankCard(againAnotherFamily)

    district = new District(0)
    card.place(district)
    otherCard.place(district)
    otherLegalCard.place(district)
    otherIllegalCard.place(district)
    againAnotherCard.place(district)
  }

  @Test
  def testActivateGainsMoney = {
    card.store(Cash(3))
    Assert.assertEquals(Cash(3), card.cash)
    card.activate(gs)
    Assert.assertEquals(Cash(3 + Rules.DjEarnings), card.cash)
  }

  @Test
  def testActivateMakesThemPay = {
    card.store(Cash(3))
    otherCard.store(Cash(3))
    card.activate(gs)
    Assert.assertEquals(Cash(3 - Rules.DjPrice), otherCard.cash)
    Assert.assertEquals(Cash(10 - 2 * Rules.DjPrice), otherFamily.cash)
    Assert.assertEquals(Cash(10 - Rules.DjPrice), againAnotherFamily.cash)
  }

  @Test
  def testActivateDoesNotMakeMePay = {
    myAnotherCard.store(Cash(3))
    Assert.assertEquals(Cash.Zero, myOtherCard.cash)
    Assert.assertEquals(Cash(3), myAnotherCard.cash)
    Assert.assertEquals(Cash(10), family.cash)
    card.activate(gs)
    Assert.assertEquals(Cash.Zero, myOtherCard.cash)
    Assert.assertEquals(Cash(3), myAnotherCard.cash)
    Assert.assertEquals(Cash(10), family.cash)
  }

}