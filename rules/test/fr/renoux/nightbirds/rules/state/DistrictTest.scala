package fr.renoux.nightbirds.rules.state

import org.junit.Assert
import org.junit.Before
import fr.renoux.nightbirds.rules.cardtypes.Pink
import org.junit.Test

class DistrictTest {
  var district: District = null
  var otherDistrict: District = null

  var firstCard: BlankCard = null
  var secondCard: BlankCard = null
  var thirdCard: BlankCard = null
  var fourthCard: BlankCard = null
  var fifthCard: BlankCard = null

  var family: Family = null

  @Before
  final def prepare() = {
    district = new District(0)
    otherDistrict = new District(0)
    family = new Family(Pink)
    firstCard = new LegalBlankCard(family)
    secondCard = new LegalBlankCard(family)
    thirdCard = new IllegalBlankCard(family)
    fourthCard = new LegalBlankCard(family)
    fifthCard = new IllegalBlankCard(family)
  }
  
  @Test
  final def testToFixedString() = {
    firstCard.place(district)
    secondCard.place(district)
    thirdCard.place(district)
    
    fourthCard.place(otherDistrict)
    fifthCard.place(otherDistrict)
    thirdCard.place(otherDistrict)
    
    Assert.assertEquals(district.toFixedString.length(), otherDistrict.toFixedString.length())
  }
}