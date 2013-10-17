package fr.renoux.nightbirds.game

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import fr.renoux.nightbirds.rules.specifics.cards.Bum
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Cash
import fr.renoux.nightbirds.rules.specifics.colors.Blue

class BumTest {

  var player : StubPlayer = new StubPlayer
  var bum: Bum = null
  var family: Family = null

  @Before
  def prepare = {
    family = Family(Blue)
    val b = new Board(family)
    bum = new Bum(player, b, family)
  }

  @Test
  def gainMoney() = {
    Assert.assertEquals(Cash(0), bum.cash)
    bum.activate
    Assert.assertEquals(Cash(1), bum.cash)
  }

  @Test
  def notGiving() = {
    bum.store(Cash(5))
    Assert.assertEquals(Cash(5), bum.cash)
    Assert.assertEquals(Cash(0), bum.take(Cash(3)))
    Assert.assertEquals(Cash(5), bum.cash)
    Assert.assertEquals(Cash(0), bum.take(Cash(8)))
    Assert.assertEquals(Cash(5), bum.cash)
  }

  @Test
  def notGivingFromFamily() = {
    family.store(Cash(5))    
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