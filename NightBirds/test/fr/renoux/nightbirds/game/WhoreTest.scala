package fr.renoux.nightbirds.game

import org.junit.Assert
import org.junit.Test
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.specifics.cards.Whore
import fr.renoux.nightbirds.rules.specifics.colors.Yellow
import fr.renoux.nightbirds.rules.specifics.colors.Blue
import fr.renoux.nightbirds.rules.specifics.cards.Cook
import fr.renoux.nightbirds.rules.generics.Cash

class WhoreTest {

  var player: StubPlayer = new StubPlayer

  @Test
  def testWhore() = {
    val blue = Family(Blue)
    val yellow = Family(Yellow)
    val b = new Board(blue, yellow)
    val firstDistrict = b.districts(0)

    val cook = new Cook(player, b, yellow)
    val whore = new Whore(player, b, blue)
    firstDistrict.add(cook)
    firstDistrict.add(whore)

    cook.store(Cash(3))
    Assert.assertEquals(Cash(3), cook.cash)
    whore.doProceed(cook)
    Assert.assertEquals(Cash(1), cook.cash)
    Assert.assertEquals(Cash(2), whore.cash)
  }

}
