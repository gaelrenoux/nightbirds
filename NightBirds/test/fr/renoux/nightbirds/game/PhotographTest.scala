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

class PhotographTest {

  var game: StubGame = null

  var leftCard: Card = null
  var rightCard: Card = null
  var photograph: Photograph = null

  @Before
  def prepare = {
    game = new StubGame

    leftCard = game.district(0).addStub(true)
    photograph = game.district(0).addPhotograph()
    rightCard = game.district(0).addStub(true)
  }

  @Test
  def seesNothing() = {
    Assert.assertEquals(Cash(0), photograph.cash)
    photograph.reveal()
    Assert.assertEquals(Cash(0), photograph.cash)
  }

  @Test
  def seesBefore() = {
    game.player.target = game.district(1).addStub()
    Assert.assertEquals(Cash(0), photograph.cash)
    leftCard.reveal()
    Assert.assertEquals(Cash(0), photograph.cash)
    photograph.reveal()
    Assert.assertEquals(Cash(2), photograph.cash)
  }

  @Test
  def seesAfter() = {
    game.player.target = game.district(1).addStub()
    Assert.assertEquals(Cash(0), photograph.cash)
    photograph.reveal()
    Assert.assertEquals(Cash(0), photograph.cash)
    rightCard.reveal()
    Assert.assertEquals(Cash(2), photograph.cash)
  }

  @Test
  def seesBoth() = {
    game.player.target = game.district(1).addStub()
    Assert.assertEquals(Cash(0), photograph.cash)
    leftCard.reveal()
    Assert.assertEquals(Cash(0), photograph.cash)
    photograph.reveal()
    Assert.assertEquals(Cash(2), photograph.cash)
    rightCard.reveal()
    Assert.assertEquals(Cash(4), photograph.cash)
  }

  @Test
  def isTargeted() = {
    game.player.target = photograph
    Assert.assertEquals(Cash(0), photograph.cash)
    leftCard.reveal()
    Assert.assertEquals(Cash(0), photograph.cash)
    photograph.reveal()
    Assert.assertEquals(Cash(0), photograph.cash)
    rightCard.reveal()
    Assert.assertEquals(Cash(0), photograph.cash)
  }

  @Test
  def isTargetedWithMoney() = {
    game.player.target = photograph
    photograph.store(Cash(14))
    Assert.assertEquals(Cash(14), photograph.cash)
    leftCard.reveal()
    Assert.assertEquals(Cash(14), photograph.cash)
    photograph.reveal()
    Assert.assertEquals(Cash(14), photograph.cash)
    rightCard.reveal()
    Assert.assertEquals(Cash(14), photograph.cash)
  }
}