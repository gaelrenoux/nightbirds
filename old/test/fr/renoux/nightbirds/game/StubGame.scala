package fr.renoux.nightbirds.game

import fr.renoux.nightbirds.rules.Black
import fr.renoux.nightbirds.rules.Blue
import fr.renoux.nightbirds.rules.Green
import fr.renoux.nightbirds.rules.Orange
import fr.renoux.nightbirds.rules.White
import fr.renoux.nightbirds.rules.Yellow
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.generics.Guts
import fr.renoux.nightbirds.rules.cards.Bum
import fr.renoux.nightbirds.rules.cards.Burglar
import fr.renoux.nightbirds.rules.cards.Cook
import fr.renoux.nightbirds.rules.cards.Cop
import fr.renoux.nightbirds.rules.cards.Dj
import fr.renoux.nightbirds.rules.cards.Dealer
import fr.renoux.nightbirds.rules.cards.Photograph
import fr.renoux.nightbirds.rules.cards.Skinhead
import fr.renoux.nightbirds.rules.cards.Taxi
import fr.renoux.nightbirds.rules.cards.Thug

class StubGame {

  val yellow = new Family(Yellow)
  val orange = new Family(Orange)
  val black = new Family(Black)
  val blue = new Family(Blue)
  val green = new Family(Green)
  val white = new Family(White)

  val board = new Board(yellow, orange, black, blue, green, white)
  val player: StubPlayer = new StubPlayer(board)

  def district(i: Int) = new {
    def addBum() = {
      val card = new Bum(player, board, blue)
      board.districts(i).add(card)
      card
    }
    def addBurglar() = {
      val card = new Burglar(player, board, blue)
      board.districts(i).add(card)
      card
    }
    def addCook() = {
      val card = new Cook(player, board, blue)
      board.districts(i).add(card)
      card
    }
    def addCop() = {
      val card = new Cop(player, board, blue)
      board.districts(i).add(card)
      card
    }
    def addDealer() = {
      val card = new Dealer(player, board, blue)
      board.districts(i).add(card)
      card
    }
    def addDj() = {
      val card = new Dj(player, board, blue)
      board.districts(i).add(card)
      card
    }
    def addPhotograph() = {
      val card = new Photograph(player, board, blue)
      board.districts(i).add(card)
      card
    }
    def addSkinhead() = {
      val card = new Skinhead(player, board, blue)
      board.districts(i).add(card)
      card
    }
    def addTaxi() = {
      val card = new Taxi(player, board, blue)
      board.districts(i).add(card)
      card
    }
    def addThug() = {
      val card = new Thug(player, board, blue)
      board.districts(i).add(card)
      card
    }

    def addStub(targeted: Boolean = false, guts: Guts = Guts(1)) = {
      val card = if (targeted) {
        new StubCardWithTarget(player, board, blue, guts)
      } else {
        new StubCardWithoutTarget(player, board, blue, guts)
      }
      board.districts(i).add(card)
      card
    }
  }

}