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

  var player : StubPlayer = new StubPlayer
  var family: Family = null
  var otherFamily: Family = null
  var board : Board = null

  @Before
  def prepare = {
    family = Family(Blue)
    otherFamily = Family(Yellow)
    board = new Board(family, otherFamily)
  }

  @Test
  def gainMoney() = {
    val dj = new DJ(player, board, family)
    val district = board.districts.head
    
    district.add(new StubCardWithTarget(player, board, otherFamily))
    district.add(new StubCardWithTarget(player, board, otherFamily))
    district.add(dj)
    district.add(new StubCardWithTarget(player, board, otherFamily))
    
    Assert.assertEquals(Cash(0), dj.cash)
    dj.activate()
    Assert.assertEquals(Cash(1), dj.cash)
  }

  @Test
  def gainMoneyAlone() = {
    val dj = new DJ(player, board, family)
    val district = board.districts.head
    
    district.add(dj)
    
    Assert.assertEquals(Cash(0), dj.cash)
    dj.activate()
    Assert.assertEquals(Cash(1), dj.cash)
  }

  @Test
  def loseMoney() = {
    val dj = new DJ(player, board, family)
    val district = board.districts.head
    
    val one = new StubCardWithTarget(player, board, otherFamily)
    val two = new StubCardWithTarget(player, board, otherFamily)
    val three = new StubCardWithTarget(player, board, otherFamily)
    
    one.store(Cash(0))
    two.store(Cash(2))
    three.store(Cash(4))
    Assert.assertEquals(Cash(0), one.cash)
    Assert.assertEquals(Cash(2), two.cash)
    Assert.assertEquals(Cash(4), three.cash)
    
    district.add(one)
    district.add(two)
    district.add(dj)
    district.add(three)
    
    dj.activate()
    
    Assert.assertEquals(Cash(0), one.cash)
    Assert.assertEquals(Cash(1), two.cash)
    Assert.assertEquals(Cash(3), three.cash)

  }
}