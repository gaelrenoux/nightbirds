package fr.renoux.nightbirds.engine

import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.Yellow
import fr.renoux.nightbirds.rules.Blue
import fr.renoux.nightbirds.rules.Black


class GameMotor {

  val board = new Board(new Family(Black), new Family(Yellow), new Family(Blue))

  /** Reveal a card */
  def reveal(card: Card, player: Player) = {
   card.reveal
  }

}