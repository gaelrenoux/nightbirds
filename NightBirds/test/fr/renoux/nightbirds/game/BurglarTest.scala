package fr.renoux.nightbirds.game

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.specifics.cards.Burglar
import fr.renoux.nightbirds.rules.generics.Cash
import fr.renoux.nightbirds.rules.specifics.colors.Yellow
import fr.renoux.nightbirds.rules.specifics.colors.Blue

class BurglarTest {
  
  var player : StubPlayer = new StubPlayer
  var burglar : Burglar = null
  var otherCard : StubCardWithTarget = null
  var family :Family = null
  var otherFamily :Family = null
  
  @Before
  def prepare = {
    family = Family(Blue)
    otherFamily = Family(Yellow)
    val b = new Board(family, otherFamily)
    burglar = new Burglar(player, b, family)
    otherCard = new StubCardWithTarget(player, b, otherFamily)
  }

  @Test
  def undisturbed() = {
    Assert.assertEquals(Cash(0), burglar.cash)
    burglar.activate
    Assert.assertEquals(Cash(4), burglar.cash)
  }

  @Test
  def disturbedBefore() = {
    Assert.assertEquals(Cash(0), burglar.cash)
    burglar.targeted(otherCard)
    Assert.assertEquals(Cash(0), burglar.cash)
    burglar.activate()
    Assert.assertEquals(Cash(3), burglar.cash)
  }

  @Test
  def disturbedAfter() = {
    Assert.assertEquals(Cash(0), burglar.cash)
    burglar.activate
    Assert.assertEquals(Cash(4), burglar.cash)
    burglar.targeted(otherCard)
    Assert.assertEquals(Cash(3), burglar.cash)
  }

  @Test
  def disturbedBoth() = {
    Assert.assertEquals(Cash(0), burglar.cash)
    burglar.targeted(otherCard)
    Assert.assertEquals(Cash(0), burglar.cash)
    burglar.activate
    Assert.assertEquals(Cash(3), burglar.cash)
    burglar.targeted(otherCard)
    Assert.assertEquals(Cash(2), burglar.cash)
  }

  @Test
  def onlyWitness() = {
    Assert.assertEquals(Cash(0), burglar.cash)
    burglar.activate
    burglar.witness(otherCard)
    Assert.assertEquals(Cash(4), burglar.cash)
  }
}