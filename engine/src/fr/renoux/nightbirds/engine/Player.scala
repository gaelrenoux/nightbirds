package fr.renoux.nightbirds.engine

import fr.renoux.nightbirds.rules.cardtypes.Color
import fr.renoux.nightbirds.rules.state.CardType
import fr.renoux.nightbirds.rules.state.DistrictPublicState
import fr.renoux.nightbirds.rules.state.GamePublicState
import fr.renoux.nightbirds.rules.state.Neighbour
import fr.renoux.nightbirds.rules.state.PublicPosition

/** Not trusted. He returns stuff but can't modify the GameState */
trait Player {
  def initGame(gs: GamePublicState, myColor: Color)

  /** Returns a card and a district to put it in */
  def place(gs: GamePublicState, myHand : Set[CardType]): (CardType, DistrictPublicState)

  /** Returns a choice to activate a card, and optionally a target to go wih it */
  def activate(gs: GamePublicState, card : PublicPosition): (Boolean, Option[Neighbour])

  /** Returns a choice to react to a card being targeted */
  def react(gs: GamePublicState, target : PublicPosition, origin : PublicPosition): Boolean = true

  /** Returns a choice to react to a card being targeted */
  def witness(gs: GamePublicState, witness : PublicPosition, origin : PublicPosition): Boolean = true
  
  override def toString = {
    this.getClass().getSimpleName() + "@" + Integer.toHexString(this.hashCode())
  }
}