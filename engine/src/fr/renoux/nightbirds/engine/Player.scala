package fr.renoux.nightbirds.engine

import fr.renoux.nightbirds.rules.state.GameState
import fr.renoux.nightbirds.rules.state.Family
import fr.renoux.nightbirds.rules.cardtypes.Color
import fr.renoux.nightbirds.rules.state.District
import fr.renoux.nightbirds.rules.state.Card

/** Not trusted. He returns stuff but can't modify the GameState */
abstract class Player {
  def initGame(gs: GameState, attributions: Map[Color, Player])

  /** Returns a card and a district to put it in */
  def place(gs: GameState): (Card, District)

  /** Returns a card to activate and eventually a target to go wih it */
  def activate(gs: GameState): (Card, Option[Card])
}