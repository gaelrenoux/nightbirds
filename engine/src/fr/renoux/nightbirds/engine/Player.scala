package fr.renoux.nightbirds.engine

import fr.renoux.nightbirds.rules.state.GameState
import fr.renoux.nightbirds.rules.state.Family
import fr.renoux.nightbirds.rules.cardtypes.Color
import fr.renoux.nightbirds.rules.state.District
import fr.renoux.nightbirds.rules.state.Card
import fr.renoux.nightbirds.rules.state.GamePublicState

/** Not trusted. He returns stuff but can't modify the GameState */
abstract class Player {
  def initGame(gs: GamePublicState, attributions: Map[Color, Player])

  /** Returns a card and a district to put it in */
  def place(gs: GamePublicState): (Card, District)

  /** Returns a card to activate and eventually a target to go wih it */
  def activate(gs: GamePublicState): (Card, Option[Card])
}