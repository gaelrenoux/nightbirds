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
  
  @Test
  def testCook() = {
    val game = new StubGame
    
    Assert.assertEquals(Cash(0), game.cook.cash)    
    game.cook.activate()
    Assert.assertEquals(Cash(2), game.cook.cash)    
  }
}
