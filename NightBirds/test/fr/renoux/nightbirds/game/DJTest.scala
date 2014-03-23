package fr.renoux.nightbirds.game

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import fr.renoux.nightbirds.rules.specifics.cards.DJ
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Cash
import fr.renoux.nightbirds.rules.specifics.colors.Yellow
import fr.renoux.nightbirds.rules.specifics.colors.Blue


class DJTest {

  var game : StubGame = null

  @Before
  def prepare = {
    game = new StubGame
  }

  @Test
  def gainMoney() = {
    val stub1 = game.district(0).addStub()
    val stub2 = game.district(0).addStub()
    val dj = game.district(0).addDj()
    val stub3 = game.district(0).addStub()
    
    Assert.assertEquals(Cash(0), dj.cash)
    dj.reveal()
    Assert.assertEquals(Cash(1), dj.cash)
  }

  @Test
  def gainMoneyAlone() = {
    val dj = game.district(0).addDj()
    
    Assert.assertEquals(Cash(0), dj.cash)
    dj.activate()
    Assert.assertEquals(Cash(1), dj.cash)
  }

  @Test
  def loseMoney() = {
    val stub1 = game.district(0).addStub()
    val stub2 = game.district(0).addStub()
    val dj = game.district(0).addDj()
    val stub3 = game.district(0).addStub()
    
    stub1.store(Cash(0))
    stub2.store(Cash(2))
    stub3.store(Cash(4))
    
    Assert.assertEquals(Cash(0), stub1.cash)
    Assert.assertEquals(Cash(2), stub2.cash)
    Assert.assertEquals(Cash(4), stub3.cash)
    
    dj.reveal()
    
    Assert.assertEquals(Cash(0), stub1.cash)
    Assert.assertEquals(Cash(1), stub2.cash)
    Assert.assertEquals(Cash(3), stub3.cash)

  }
}