package fr.renoux.nightbirds.game

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import fr.renoux.nightbirds.rules.cards.Bum
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Cash
import fr.renoux.nightbirds.rules.generics.Board

class BumTest {

  var game: StubGame = null

  @Before
  def prepare = {
    game = new StubGame
  }

  @Test
  def gainMoney() = {
    val bum = game.district(0).addBum()
    Assert.assertEquals(Cash(0), bum.cash)
    bum.reveal
    Assert.assertEquals(Cash(1), bum.cash)
  }

  @Test
  def notGiving() = {
    val bum = game.district(0).addBum()
    bum.store(Cash(5))
    Assert.assertEquals(Cash(5), bum.cash)
    Assert.assertEquals(Cash(0), bum.take(Cash(3)))
    Assert.assertEquals(Cash(5), bum.cash)
    Assert.assertEquals(Cash(0), bum.take(Cash(8)))
    Assert.assertEquals(Cash(5), bum.cash)
  }

  @Test
  def notGivingFromFamily() = {
    val bum = game.district(0).addBum()
    bum.family.store(Cash(5))
    Assert.assertEquals(Cash(0), bum.cash)
    Assert.assertEquals(Cash(5), bum.family.cash)
    
    Assert.assertEquals(Cash(0), bum.take(Cash(3)))
    Assert.assertEquals(Cash(0), bum.cash)
    Assert.assertEquals(Cash(5), bum.family.cash)
    
    bum.store(Cash(3))
    Assert.assertEquals(Cash(3), bum.cash)
    
    Assert.assertEquals(Cash(0), bum.take(Cash(8)))
    Assert.assertEquals(Cash(3), bum.cash)
    Assert.assertEquals(Cash(5), bum.family.cash)
  }
}