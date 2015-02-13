package fr.renoux.nightbirds.rules.engine

import fr.renoux.nightbirds.rules.cardtypes.Color
import fr.renoux.nightbirds.utils.Logger
import fr.renoux.nightbirds.rules.state.GameState
import fr.renoux.nightbirds.utils.Info
import fr.renoux.nightbirds.rules.state.Card
import javax.smartcardio.CardTerminal
import fr.renoux.nightbirds.rules.state.District
import fr.renoux.nightbirds.rules.state.CardType

object GameLogger {

  /** Logs the game about to start with the color order */
  def gameStarts(colorsSequence: Seq[Color]) = Logger.info("Game starts, players are " + colorsSequence.mkString("", ", ", ""))

  /** Logs the game ending with the score */
  def gameEnds(gs: GameState) = if (Logger.level <= Info) {
    Logger.info("Game ends, with score : " + gs.toScore)
  }

  /** Logs the start of the round, with the first player this round */
  def roundStarts(color: Color) = Logger.info(Tab + "Round starts, first player is " + color)

  /** Logs the end of the round, with the current score */
  def roundEnds(gs: GameState) = Logger.info(Tab + "Round ends, with score : " + gs.toScore)

  /** Logs that placement has been done for the round, and gives the current state of the board */
  def roundPlacementDone(gs: GameState) = if (Logger.level <= Info) {
    Logger.info(Tab + "Placement done : ")
    gs.districts foreach { d => Logger.info(TabTab + d.toFixedString) }
    Logger.info(Tab + "Starting activations ")
  }

  /** Logs that activations have been done for the round, and gives the current state of the board (before money returns to the families) */
  def roundActivationDone(gs: GameState) = if (Logger.level <= Info) {
    Logger.info(Tab + "Activations done : ")
    gs.districts foreach { d => Logger.info(TabTab + d.toFixedString) }
  }

  /** Logs that a card has been placed, with the current state of its district */
  def placed(card: Card) =
    Logger.debug(string(8) + card.family.color.toFixedString + " placed " + card.cardType.toFixedString + " in " + card.position.get.district.toFixedString)

  /** Logs that a card has been activated on no target, with the current state of its district */
  def activated(card: Card) = {
    val builder = new StringBuilder("        ")
    builder.append(card.family.color.toFixedString).append(" ")
    builder.append("activated      ").append(Tab)
    builder.append(card.toFixedString).append(Tab)
    builder.append(string(4 + Card.FixedStringLength)).append(Tab)
    builder.append(" in ").append(card.position.get.district.toFixedString)
    Logger.debug(builder.toString)
  }

  /** Logs that a card has been activated on a target, with the target and the current state of its district */
  def activated(card: Card, target: Card) = {
    val builder = new StringBuilder("        ")
    builder.append(card.family.color.toFixedString).append(" ")
    builder.append("activated      ").append(Tab)
    builder.append(card.toFixedString).append(Tab)
    builder.append(" on " + target.toFixedString).append(Tab)
    builder.append(" in ").append(card.position.get.district.toFixedString)
    Logger.debug(builder.toString)
  }

  /** Logs that a player declined to activate a card */
  def declinedActivation(card: Card) = {
    val builder = new StringBuilder("        ")
    builder.append(card.family.color.toFixedString).append(" ")
    builder.append("didn't activate").append(Tab)
    builder.append(card.toFixedString).append(Tab)
    builder.append(string(4 + Card.FixedStringLength)).append(Tab)
    builder.append(" in ").append(card.position.get.district.toFixedString)
    Logger.debug(builder.toString)
  }

  private def string(nb: Int) = new String(Array.fill(nb)(' '))

  private val Tab = string(4)
  private val TabTab = string(8)
  private val TabTabTab = string(12)
}
