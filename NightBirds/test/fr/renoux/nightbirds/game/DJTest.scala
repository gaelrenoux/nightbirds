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

  @Test
  def gainMoney() = {
    val game = new StubGame

    Assert.assertEquals(Cash(0), game.dj.cash)
    game.dj.activate()
    Assert.assertEquals(Cash(1), game.dj.cash)
  }

  @Test
  def loseMoney() = {
    val game = new StubGame

    val district = game.board.districtOf(game.dj)

    district.cards.zipWithIndex.foreach { cpl => cpl._1.store(Cash(cpl._2)) }
    district.cards.zipWithIndex.foreach { cpl => Assert.assertEquals(Cash(cpl._2), cpl._1.cash) }

    game.dj.activate

    district.cards.zipWithIndex.filter { c => game.dj != c._1 && game.bum != c._1 }.foreach { cpl =>
      Assert.assertEquals(Cash(scala.math.max(0, cpl._2 - 1)), cpl._1.cash)
    }

  }
}