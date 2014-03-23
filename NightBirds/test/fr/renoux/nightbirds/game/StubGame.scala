package fr.renoux.nightbirds.game

import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.specifics.cards.Photograph
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.specifics.colors.Yellow
import fr.renoux.nightbirds.rules.specifics.colors.Blue
import fr.renoux.nightbirds.rules.specifics.colors.Black
import fr.renoux.nightbirds.rules.specifics.colors.Green
import fr.renoux.nightbirds.rules.specifics.colors.Orange
import fr.renoux.nightbirds.rules.specifics.colors.White
import fr.renoux.nightbirds.rules.specifics.cards.Bum
import fr.renoux.nightbirds.rules.specifics.cards.Burglar
import fr.renoux.nightbirds.rules.specifics.cards.Cook
import fr.renoux.nightbirds.rules.specifics.cards.DJ
import fr.renoux.nightbirds.rules.specifics.cards.Cop
import fr.renoux.nightbirds.rules.specifics.cards.Dealer
import fr.renoux.nightbirds.rules.generics.Card

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
      val card = new DJ(player, board, blue)
      board.districts(i).add(card)
      card
    }
    def addStub(targeted: Boolean = false) = {
      val card = if (targeted) {
        new StubCardWithTarget(player, board, blue)
      } else {
        new StubCardWithoutTarget(player, board, blue)
      }
      board.districts(i).add(card)
      card
    }
  }

}