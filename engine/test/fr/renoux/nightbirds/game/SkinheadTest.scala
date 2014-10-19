package fr.renoux.nightbirds.game

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Cash
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.specifics.cards.Photograph
import fr.renoux.nightbirds.rules.generics.SuccessfulActivation
import fr.renoux.nightbirds.rules.generics.Neighbours
import fr.renoux.nightbirds.rules.specifics.cards.Photograph
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.specifics.cards.Skinhead
import fr.renoux.nightbirds.rules.generics.Guts

class SkinheadTest {

  var game: StubGame = null

  var easyTarg: Card = null
  var skin: Skinhead = null

  @Before
  def prepare = {
    game = new StubGame
    skin = game.district(0).addSkinhead()
  }

  @Test
  def hitsWeaker() = {
    game.player.target = game.district(0).addStub(guts=Guts(3)) 
    skin.reveal()
    Assert.assertFalse(skin.hurt)
    Assert.assertTrue(game.player.target.hurt)
  }

  @Test
  def hitsStronger() = {
    game.player.target = game.district(0).addStub(guts=Guts(5))
    skin.reveal()
    Assert.assertTrue(skin.hurt)
    Assert.assertFalse(game.player.target.hurt)
  }

  @Test
  def hitsEqual() = {
    game.player.target = game.district(0).addStub(guts=Guts(4))
    skin.reveal()
    Assert.assertTrue(skin.hurt)
    Assert.assertFalse(game.player.target.hurt)
  }

  @Test
  def targetedWeaker() = {
    val active =  game.district(0).addStub(true, Guts(1))
    game.player.target = skin
    active.reveal()
    Assert.assertFalse(skin.hurt)
    Assert.assertTrue(active.hurt)
  }

  @Test
  def targetedStronger() = {
    val active =  game.district(0).addStub(true, Guts(1))
    game.player.target = skin
    active.reveal()
    Assert.assertFalse(skin.hurt)
    Assert.assertTrue(active.hurt)
  }
}