package fr.renoux.nightbirds.engine

import fr.renoux.nightbirds.rules.state.GameState
import fr.renoux.nightbirds.rules.state.Family
import fr.renoux.nightbirds.rules.cardtypes.Color
import fr.renoux.nightbirds.rules.state.District
import fr.renoux.nightbirds.rules.state.Card
import fr.renoux.nightbirds.rules.state.GamePublicState
import fr.renoux.nightbirds.rules.state.CardPublicState
import fr.renoux.nightbirds.rules.state.Neighbour
import fr.renoux.nightbirds.rules.state.DistrictPublicState
import fr.renoux.nightbirds.rules.state.CardType
import fr.renoux.nightbirds.rules.state.Position

/** Not trusted. He returns stuff but can't modify the GameState */
trait Player {
  def initGame(gs: GamePublicState, myColor: Color)

  /** Returns a card and a district to put it in */
  def place(gs: GamePublicState, myHand : Set[CardType]): (CardType, DistrictPublicState)

  /** Returns a choice to activate a card, and optionally a target to go wih it */
  def activate(gs: GamePublicState, card : Position): (Boolean, Option[Neighbour])

  /** Returns a choice to react to a card being targeted */
  def react(gs: GamePublicState, target : Position, origin : Position): Boolean = true
  
  override def toString = {
    this.getClass().getSimpleName() + "@" + Integer.toHexString(this.hashCode())
  }
}