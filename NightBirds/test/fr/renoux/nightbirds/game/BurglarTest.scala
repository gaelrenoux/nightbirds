package fr.renoux.nightbirds.game

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.generics.Cash
import fr.renoux.nightbirds.rules.specifics.colors.Yellow
import fr.renoux.nightbirds.rules.specifics.colors.Blue

class BurglarTest {

  @Test
  def undisturbed() = {
    val game = new StubGame
    Assert.assertEquals(Cash(0), game.burglar.cash)
    game.burglar.activate
    Assert.assertEquals(Cash(4), game.burglar.cash)
  }

  @Test
  def disturbedBefore() = {
    val game = new StubGame
    Assert.assertEquals(Cash(0), game.burglar.cash)
    game.otherPlayer.target = game.burglar
    game.otherCard.activate()
    Assert.assertEquals(Cash(0), game.burglar.cash)
    game.burglar.activate()
    Assert.assertEquals(Cash(3), game.burglar.cash)
  }

  @Test
  def disturbedAfter() = {
    val game = new StubGame
    Assert.assertEquals(Cash(0), game.burglar.cash)
    game.burglar.activate
    Assert.assertEquals(Cash(4), game.burglar.cash)
    game.otherPlayer.target = game.burglar
    game.otherCard.activate()
    Assert.assertEquals(Cash(3), game.burglar.cash)
  }

  @Test
  def disturbedBoth() = {
    val game = new StubGame
    game.otherPlayer.target = game.burglar
    Assert.assertEquals(Cash(0), game.burglar.cash)
    game.otherCard.activate()
    Assert.assertEquals(Cash(0), game.burglar.cash)
    game.burglar.activate
    Assert.assertEquals(Cash(3), game.burglar.cash)
    game.otherCard.activate()
    Assert.assertEquals(Cash(2), game.burglar.cash)
  }

  @Test
  def onlyWitness() = {
    val game = new StubGame
    game.player.target = game.otherCard
    game.otherPlayer.target=game.otherCard
    game.board.getNeighbours(game.burglar).foreach{ _.activate }
    Assert.assertEquals(Cash(0), game.burglar.cash)
    game.burglar.activate
    Assert.assertEquals(Cash(4), game.burglar.cash)
  }
}