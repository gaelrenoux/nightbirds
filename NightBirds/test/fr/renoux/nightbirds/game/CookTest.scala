package fr.renoux.nightbirds.game

import org.junit.Assert
import org.junit.Test
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Cash
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.specifics.cards.Cook


class CookTest {

  @Test
  def testCook() = {
	val game = new StubGame
    
    val cook = game.district(0).addCook
    Assert.assertEquals(Cash(0), cook.cash)    
    cook.reveal()
    Assert.assertEquals(Cash(2), cook.cash)    
  }
}
