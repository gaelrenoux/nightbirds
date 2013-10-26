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
import scala.util.Left

class PhotographTest {

  @Test
  def seesNothing() = {
    val game = new StubGame
    Assert.assertEquals(Cash(0), game.photograph.cash)
    game.photograph.activate
    Assert.assertEquals(Cash(0), game.photograph.cash)
  }

  @Test
  def seesBefore() = {
    val game = new StubGame
    game.otherPlayer.target = game.otherCard
    val neighbours = game.board.getNeighbours(game.photograph)
    Assert.assertEquals(Cash(0), game.photograph.cash)
    neighbours.early.get.activate
    Assert.assertEquals(Cash(0), game.photograph.cash)
    game.photograph.activate
    Assert.assertEquals(Cash(2), game.photograph.cash)
  }

  @Test
  def seesAfter() = {
    val game = new StubGame
    game.otherPlayer.target = game.otherCard
    val neighbours = game.board.getNeighbours(game.photograph)
    Assert.assertEquals(Cash(0), game.photograph.cash)
    game.photograph.activate
    Assert.assertEquals(Cash(0), game.photograph.cash)
    neighbours.late.get.activate
    Assert.assertEquals(Cash(2), game.photograph.cash)
  }

  @Test
  def seesBoth() = {
    val game = new StubGame
    game.otherPlayer.target = game.otherCard
    val neighbours = game.board.getNeighbours(game.photograph)
    Assert.assertEquals(Cash(0), game.photograph.cash)
    neighbours.early.get.activate
    Assert.assertEquals(Cash(0), game.photograph.cash)
    game.photograph.activate
    Assert.assertEquals(Cash(2), game.photograph.cash)
    neighbours.late.get.activate
    Assert.assertEquals(Cash(4), game.photograph.cash)
  }

  @Test
  def isTargetedOnce() = {
    val game = new StubGame
    game.otherPlayer.target = game.photograph
    val neighbours = game.board.getNeighbours(game.photograph)
    Assert.assertEquals(Cash(0), game.photograph.cash)
    neighbours.early.get.activate
    Assert.assertEquals(Cash(0), game.photograph.cash)
    game.photograph.activate
    Assert.assertEquals(Cash(0), game.photograph.cash)
    game.otherPlayer.target = game.otherCard
    neighbours.late.get.activate
    Assert.assertEquals(Cash(2), game.photograph.cash)
  }

  @Test
  def isTargetedTwice() = {
    val game = new StubGame
    game.otherPlayer.target = game.photograph
    val neighbours = game.board.getNeighbours(game.photograph)
    Assert.assertEquals(Cash(0), game.photograph.cash)
    neighbours.early.get.activate
    Assert.assertEquals(Cash(0), game.photograph.cash)
    game.photograph.activate
    Assert.assertEquals(Cash(0), game.photograph.cash)
    neighbours.late.get.activate
    Assert.assertEquals(Cash(0), game.photograph.cash)
  }

  @Test
  def isTargetedWithMoney() = {
    val game = new StubGame
    game.otherPlayer.target = game.photograph
    val neighbours = game.board.getNeighbours(game.photograph)
    game.photograph.store(Cash(14))
    Assert.assertEquals(Cash(14), game.photograph.cash)
    neighbours.early.get.activate
    Assert.assertEquals(Cash(14), game.photograph.cash)
    game.photograph.activate
    Assert.assertEquals(Cash(14), game.photograph.cash)
    neighbours.late.get.activate
    Assert.assertEquals(Cash(14), game.photograph.cash)
  }
}