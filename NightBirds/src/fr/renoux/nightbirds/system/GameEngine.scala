package fr.renoux.nightbirds.system

import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.specifics.colors.Black
import fr.renoux.nightbirds.rules.specifics.colors.Blue
import fr.renoux.nightbirds.rules.specifics.colors.Yellow


class GameMotor {

  val board = new Board(new Family(Black), new Family(Yellow), new Family(Blue))

  /** Reveal a card */
  def reveal(card: Card, player: Player) = {
   card.reveal
  }

}