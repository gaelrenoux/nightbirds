package fr.renoux.nightbirds.game

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Cash
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.specifics.cards.Photograph
import fr.renoux.nightbirds.rules.specifics.colors.Blue
import fr.renoux.nightbirds.rules.specifics.colors.Yellow
import fr.renoux.nightbirds.rules.generics.SuccessfulActivation
import fr.renoux.nightbirds.rules.generics.Neighbours
import fr.renoux.nightbirds.rules.specifics.cards.Photograph
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.specifics.cards.Skinhead
import fr.renoux.nightbirds.rules.generics.Guts
import fr.renoux.nightbirds.rules.specifics.cards.Thug

class ThugTest {

  var game: StubGame = null

  var thug: Thug = null

  @Before
  def prepare = {
    game = new StubGame
    thug = game.district(0).addThug()
  }

  @Test
  def robsWeaker() = {
    game.player.target = game.district(0).addStub(guts=Guts(1))
    game.player.target.store(Cash(15))
    Assert.assertEquals(Cash(0), thug.cash)
    
    thug.reveal()
    Assert.assertEquals(Cash(13), game.player.target.cash)
    Assert.assertEquals(Cash(2), thug.cash)
    Assert.assertFalse(thug.hurt)
    Assert.assertFalse(game.player.target.hurt)
  }

  @Test
  def robsStronger() = {
    game.player.target = game.district(0).addStub(guts=Guts(4)) 
    game.player.target.store(Cash(15))
    Assert.assertEquals(Cash(0), thug.cash)
    
    thug.reveal()
    Assert.assertEquals(Cash(15), game.player.target.cash)
    Assert.assertEquals(Cash(0), thug.cash)
    Assert.assertTrue(thug.hurt)
    Assert.assertFalse(game.player.target.hurt)
  }

  @Test
  def robsEqual() = {
    game.player.target = game.district(0).addStub(guts=Guts(3)) 
    game.player.target.store(Cash(15))
    Assert.assertEquals(Cash(0), thug.cash)
    
    thug.reveal()
    Assert.assertEquals(Cash(15), game.player.target.cash)
    Assert.assertEquals(Cash(0), thug.cash)
    Assert.assertTrue(thug.hurt)
    Assert.assertFalse(game.player.target.hurt)
  }

  @Test
  def robsNoMoney() = {
    game.player.target = game.district(0).addStub(guts=Guts(1)) 
    Assert.assertEquals(Cash(0), thug.cash)
    Assert.assertEquals(Cash(0), game.player.target.cash)
    Assert.assertEquals(Cash(0), game.player.target.family.cash)
    
    thug.reveal()
    Assert.assertEquals(Cash(0), thug.cash)
    Assert.assertFalse(thug.hurt)
    Assert.assertFalse(game.player.target.hurt)
  }
}