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

class PhotographTest {
  
  var player : StubPlayer = new StubPlayer
  var board : Board = null
  
  var photograph : Photograph = null
  var leftCard : StubCardWithTarget = null
  var rightCard : StubCardWithTarget = null
  var family :Family = null
  var otherFamily :Family = null
  
  @Before
  def prepare : Unit = {
    family = Family(Blue)
    otherFamily = Family(Yellow)
    board = new Board(family, otherFamily)
    photograph = new Photograph(player, board, family)
    leftCard = new StubCardWithTarget(player, board, otherFamily)
    rightCard = new StubCardWithTarget(player, board, otherFamily)
    
    val d = board.districts.head
    d.add(leftCard)
    d.add(photograph)
    d.add(rightCard)
  }

  @Test
  def seesNothing() = {
    Assert.assertEquals(Cash(0), photograph.cash)
    photograph.activate
    Assert.assertEquals(Cash(0), photograph.cash)
  }

  @Test
  def seesBefore() = {
    Assert.assertEquals(Cash(0), photograph.cash)
    leftCard.activate
    Assert.assertEquals(Cash(0), photograph.cash)
    photograph.activate
    Assert.assertEquals(Cash(2), photograph.cash)
  }

  @Test
  def seesAfter() = {
    Assert.assertEquals(Cash(0), photograph.cash)
    photograph.activate
    Assert.assertEquals(Cash(0), photograph.cash)
    rightCard.activate()
    photograph.witness(rightCard)
    Assert.assertEquals(Cash(2), photograph.cash)
  }

  @Test
  def seesBoth() = {
    Assert.assertEquals(Cash(0), photograph.cash)
    leftCard.activate
    Assert.assertEquals(Cash(0), photograph.cash)
    photograph.activate
    Assert.assertEquals(Cash(2), photograph.cash)
    rightCard.activate
    photograph.witness(rightCard)
    Assert.assertEquals(Cash(4), photograph.cash)
  }

  @Test
  def isTargeted() = {
    Assert.assertEquals(Cash(0), photograph.cash)
    photograph.targeted(leftCard)
    leftCard.doProceed(photograph)
    Assert.assertEquals(Cash(0), photograph.cash)
    photograph.doProceed
    Assert.assertEquals(Cash(0), photograph.cash)
    photograph.targeted(rightCard)
    rightCard.doProceed(photograph)
    Assert.assertEquals(Cash(0), photograph.cash)
  }

  @Test
  def isTargetedWithMoney() = {
    photograph.store(Cash(14))
    Assert.assertEquals(Cash(14), photograph.cash)
    photograph.targeted(leftCard)
    leftCard.doProceed(photograph)
    Assert.assertEquals(Cash(14), photograph.cash)
    photograph.activate
    Assert.assertEquals(Cash(14), photograph.cash)
    photograph.targeted(rightCard)
    rightCard.doProceed(photograph)
    Assert.assertEquals(Cash(14), photograph.cash)
  }
}