package fr.renoux.nightbirds.game

import org.junit.Assert
import org.junit.Test
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Cash
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.specifics.cards.Cook
import fr.renoux.nightbirds.rules.specifics.colors.Yellow
import fr.renoux.nightbirds.rules.specifics.colors.Blue


class CookTest {

  var player : StubPlayer = new StubPlayer
  
  @Test
  def testCook() = {
    val blue = Family(Blue)
    val yellow = Family(Yellow)
    val b = new Board(blue, yellow)
    
    val cook = new Cook(player, b, blue)
    Assert.assertEquals(Cash(0), cook.cash)    
    cook.activate()
    Assert.assertEquals(Cash(2), cook.cash)    
  }
}
