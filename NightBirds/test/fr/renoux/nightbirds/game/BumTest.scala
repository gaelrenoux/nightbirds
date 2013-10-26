package fr.renoux.nightbirds.game

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import fr.renoux.nightbirds.rules.specifics.cards.Bum
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Cash
import fr.renoux.nightbirds.rules.specifics.colors.Blue

class BumTest {

  @Test
  def gainMoney() = {
    val game = new StubGame
    Assert.assertEquals(Cash(0), game.bum.cash)
    game.bum.activate
    Assert.assertEquals(Cash(1), game.bum.cash)
  }

  @Test
  def notGiving() = {
    val game = new StubGame
    game.bum.store(Cash(5))
    Assert.assertEquals(Cash(5), game.bum.cash)
    Assert.assertEquals(Cash(0), game.bum.take(Cash(3)))
    Assert.assertEquals(Cash(5), game.bum.cash)
    Assert.assertEquals(Cash(0), game.bum.take(Cash(8)))
    Assert.assertEquals(Cash(5), game.bum.cash)
  }

  @Test
  def notGivingFromFamily() = {
    val game = new StubGame
    game.bum.family.store(Cash(5))    
    Assert.assertEquals(Cash(0), game.bum.cash)
    Assert.assertEquals(Cash(5), game.bum.family.cash)
    Assert.assertEquals(Cash(0), game.bum.take(Cash(3)))
    Assert.assertEquals(Cash(0), game.bum.cash)
    Assert.assertEquals(Cash(5), game.bum.family.cash)
    game.bum.store(Cash(3))
    Assert.assertEquals(Cash(3), game.bum.cash)
    Assert.assertEquals(Cash(0), game.bum.take(Cash(8)))
    Assert.assertEquals(Cash(3), game.bum.cash)
    Assert.assertEquals(Cash(5), game.bum.family.cash)
  }
}