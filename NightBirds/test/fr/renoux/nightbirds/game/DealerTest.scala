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
import fr.renoux.nightbirds.rules.specifics.cards.Dealer

class DealerTest {

  var game: StubGame = null

  var dealer: Dealer = null

  @Before
  def prepare = {
    game = new StubGame
    dealer = game.district(0).addDealer()
  }

  @Test
  def deals() = {
    game.player.target = game.district(0).addStub(guts=Guts(3))
    game.player.target.store(Cash(15))
    Assert.assertEquals(Cash(0), dealer.cash)
    dealer.reveal()
    Assert.assertEquals(Cash(11), game.player.target.cash)
    Assert.assertEquals(Cash(4), dealer.cash)
  }

  @Test
  def dealsNoMoney() = {
    game.player.target = game.district(0).addStub(guts=Guts(3))
    game.player.target.confiscate()
    Assert.assertEquals(Cash(0), dealer.cash)
    Assert.assertEquals(Cash(0), game.player.target.cash)
    Assert.assertEquals(Cash(0), game.player.target.family.cash)
    dealer.reveal()
    Assert.assertEquals(Cash(0), game.player.target.cash)
    Assert.assertEquals(Cash(0), dealer.cash)
  }
}