package fr.renoux.nightbirds.game

import fr.renoux.nightbirds.rules.specifics.cards.Burglar
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.specifics.colors.Yellow
import fr.renoux.nightbirds.rules.specifics.colors.Blue
import fr.renoux.nightbirds.rules.specifics.cards.Whore
import fr.renoux.nightbirds.rules.specifics.cards.DJ
import fr.renoux.nightbirds.rules.specifics.cards.Bum
import fr.renoux.nightbirds.rules.specifics.cards.Photograph
import fr.renoux.nightbirds.rules.specifics.cards.Cook
import fr.renoux.nightbirds.rules.specifics.colors.Green

class StubGame {
  val family = Family(Blue)
  val otherFamily = Family(Yellow)
  val yetAnotherFamily = Family(Green)

  val player: StubPlayer = new StubPlayer
  val otherPlayer: StubPlayer = new StubPlayer
  val yetAnotherPlayer: StubPlayer = new StubPlayer

  val board = new Board(family, otherFamily, yetAnotherFamily)

  val bum = new Bum(player, board, family)
  val burglar = new Burglar(player, board, family)
  val cook = new Cook(player, board, family)
  val dj = new DJ(player, board, family)
  val photograph = new Photograph(player, board, family)
  val whore = new Whore(player, board, family)

  val otherCard = new StubCardWithTarget(otherPlayer, board, otherFamily)
  
  board.districts(0).add(new StubCardWithTarget(otherPlayer, board, otherFamily))
  board.districts(0).add(bum)
  board.districts(0).add(new StubCardWithTarget(otherPlayer, board, otherFamily))
  board.districts(0).add(burglar)
  board.districts(0).add(new StubCardWithTarget(otherPlayer, board, otherFamily))
  board.districts(0).add(cook)
  board.districts(0).add(new StubCardWithTarget(otherPlayer, board, otherFamily))
  board.districts(0).add(dj)
  board.districts(0).add(new StubCardWithTarget(otherPlayer, board, otherFamily))
  board.districts(0).add(photograph)
  board.districts(0).add(new StubCardWithTarget(otherPlayer, board, otherFamily))
  board.districts(0).add(whore)
  board.districts(0).add(new StubCardWithTarget(otherPlayer, board, otherFamily))
  board.districts(0).add(otherCard)
  board.districts(0).add(new StubCardWithTarget(otherPlayer, board, otherFamily))
  
  val yetAnotherCardInAnotherDistrict = new StubCardWithTarget(yetAnotherPlayer, board, yetAnotherFamily)
  board.districts(1).add(yetAnotherCardInAnotherDistrict)
}