package fr.renoux.nightbirds.game

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.cards.Burglar
import fr.renoux.nightbirds.rules.generics.Cash
import fr.renoux.nightbirds.rules.generics.SuccessfulActivation
import fr.renoux.nightbirds.rules.generics.Neighbours

class BurglarTest {
  
  var game : StubGame = null
  
  @Before
  def prepare = {
	game = new StubGame
  }

  @Test
  def undisturbed() = {
    val before = game.district(0).addStub()
    val burglar = game.district(0).addBurglar()
    val after = game.district(0).addStub()
    
    Assert.assertEquals(Cash(0), burglar.cash)
    before.reveal
    burglar.reveal
    after.reveal
    Assert.assertEquals(Cash(4), burglar.cash)
  }

  @Test
  def disturbedBefore() = {
    val before = game.district(0).addStub(true)
    val burglar = game.district(0).addBurglar()
    val after = game.district(0).addStub()
    game.player.target = burglar
    
    Assert.assertEquals(Cash(0), burglar.cash)
    before.reveal
    Assert.assertEquals(Cash(0), burglar.cash)
    burglar.reveal()
    Assert.assertEquals(Cash(3), burglar.cash)
    after.reveal
    Assert.assertEquals(Cash(3), burglar.cash)
  }

  @Test
  def disturbedAfter() = {
    val before = game.district(0).addStub()
    val burglar = game.district(0).addBurglar()
    val after = game.district(0).addStub(true)
    game.player.target = burglar
    
    Assert.assertEquals(Cash(0), burglar.cash)
    before.reveal
    Assert.assertEquals(Cash(0), burglar.cash)
    burglar.reveal
    Assert.assertEquals(Cash(4), burglar.cash)
    after.reveal()
    Assert.assertEquals(Cash(3), burglar.cash)
  }

  @Test
  def disturbedBoth() = {
    val before = game.district(0).addStub(true)
    val burglar = game.district(0).addBurglar()
    val after = game.district(0).addStub(true)
    game.player.target = burglar
    
    Assert.assertEquals(Cash(0), burglar.cash)
    before.reveal
    Assert.assertEquals(Cash(0), burglar.cash)
    burglar.reveal
    Assert.assertEquals(Cash(3), burglar.cash)
    after.reveal()
    Assert.assertEquals(Cash(2), burglar.cash)
  }
}