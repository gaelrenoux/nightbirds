package fr.renoux.nightbirds.simulation.players

import fr.renoux.nightbirds.rules.engine.Player
import fr.renoux.nightbirds.rules.engine.Random
import fr.renoux.nightbirds.rules.cardtypes.Color
import fr.renoux.nightbirds.rules.state.Card
import fr.renoux.nightbirds.rules.state.District
import fr.renoux.nightbirds.rules.state.DistrictPublicState
import fr.renoux.nightbirds.rules.state.GamePublicState
import fr.renoux.nightbirds.rules.state.Neighbour
import fr.renoux.nightbirds.rules.state.LeftNeighbour
import fr.renoux.nightbirds.rules.state.RightNeighbour
import fr.renoux.nightbirds.rules.state.CardType
import fr.renoux.nightbirds.rules.state.Position
import fr.renoux.nightbirds.rules.state.PublicPosition
import fr.renoux.nightbirds.rules.engine.Activation
import fr.renoux.nightbirds.rules.engine.Pass
import fr.renoux.nightbirds.rules.state.CardPublicState
import fr.renoux.nightbirds.rules.cardtypes.BumType
import fr.renoux.nightbirds.rules.cardtypes.DjType
import fr.renoux.nightbirds.rules.cardtypes.CopType
import fr.renoux.nightbirds.rules.cardtypes.WhoreType
import fr.renoux.nightbirds.rules.cardtypes.TaxiType
import fr.renoux.nightbirds.rules.cardtypes.CookType
import fr.renoux.nightbirds.rules.cardtypes.ThugType
import fr.renoux.nightbirds.rules.cardtypes.PhotographType
import fr.renoux.nightbirds.rules.cardtypes.DealerType
import fr.renoux.nightbirds.rules.cardtypes.SkinheadType
import fr.renoux.nightbirds.rules.cardtypes.BurglarType
import fr.renoux.nightbirds.rules.engine.ActivateWithoutTarget
import fr.renoux.nightbirds.rules.engine.ActivateWithoutTarget
import fr.renoux.nightbirds.rules.engine.ActivateTaxi
import fr.renoux.nightbirds.rules.engine.WorksWithoutTarget
import fr.renoux.nightbirds.rules.engine.WorksWithTarget
import fr.renoux.nightbirds.rules.engine.WorksWithTaxi
import fr.renoux.nightbirds.rules.state.PublicPosition
import fr.renoux.nightbirds.rules.cardtypes.PrivateEyeType
import fr.renoux.nightbirds.rules.engine.WorksWithPrivateEye

/** Basic skeleton for a player class. */
abstract class AbstractPlayer extends Player {

  override def initGame(gs: GamePublicState, myColor: Color) = {
    /* The abstract player does nothing specific */
  }

  /**
   * Called when it is a card's turn to be activated. The return value will determine wether or not the card is activated, and activation properties (specifically the target).
   *
   * Delegates to a specific method for each type.
   */
  override def activate(gs: GamePublicState, card: CardPublicState, cardPosition: PublicPosition): Activation = card.cardType match {
    case Some(BumType) => activateBum(gs, card, cardPosition)
    case Some(BurglarType) => activateBurglar(gs, card, cardPosition)
    case Some(CookType) => activateCook(gs, card, cardPosition)
    case Some(CopType) => activateCop(gs, card, cardPosition)
    case Some(DealerType) => activateDealer(gs, card, cardPosition)
    case Some(DjType) => activateDj(gs, card, cardPosition)
    case Some(PhotographType) => activatePhotograph(gs, card, cardPosition)
    case Some(PrivateEyeType) => activatePrivateEye(gs, card, cardPosition)
    case Some(SkinheadType) => activateSkinhead(gs, card, cardPosition)
    case Some(TaxiType) => activateTaxi(gs, card, cardPosition)
    case Some(ThugType) => activateThug(gs, card, cardPosition)
    case Some(WhoreType) => activateWhore(gs, card, cardPosition)
    case _ => throw new IllegalArgumentException("Why are you asking me about " + card + " ?")
  }

  def activateBum(gs: GamePublicState, card: CardPublicState, cardPosition: PublicPosition) = {
    activateDefaultWithoutTarget(gs, card, cardPosition)
  }

  def activateBurglar(gs: GamePublicState, card: CardPublicState, cardPosition: PublicPosition) = {
    activateDefaultWithoutTarget(gs, card, cardPosition)
  }

  def activateCook(gs: GamePublicState, card: CardPublicState, cardPosition: PublicPosition) = {
    activateDefaultWithoutTarget(gs, card, cardPosition)
  }

  def activateDealer(gs: GamePublicState, card: CardPublicState, cardPosition: PublicPosition) = {
    activateDefaultWithTarget(gs, card, cardPosition)
  }

  def activateCop(gs: GamePublicState, card: CardPublicState, cardPosition: PublicPosition) = {
    activateDefaultWithTarget(gs, card, cardPosition)
  }

  def activateDj(gs: GamePublicState, card: CardPublicState, cardPosition: PublicPosition) = {
    activateDefaultWithoutTarget(gs, card, cardPosition)
  }

  def activatePhotograph(gs: GamePublicState, card: CardPublicState, cardPosition: PublicPosition) = {
    activateDefaultWithoutTarget(gs, card, cardPosition)
  }

  def activatePrivateEye(gs: GamePublicState, card: CardPublicState, cardPosition: PublicPosition): WorksWithPrivateEye = {
    ???
  }

  def activateSkinhead(gs: GamePublicState, card: CardPublicState, cardPosition: PublicPosition) = {
    activateDefaultWithTarget(gs, card, cardPosition)
  }

  def activateTaxi(gs: GamePublicState, card: CardPublicState, cardPosition: PublicPosition): WorksWithTaxi = {
    ???
  }

  def activateThug(gs: GamePublicState, card: CardPublicState, cardPosition: PublicPosition) = {
    activateDefaultWithTarget(gs, card, cardPosition)
  }

  def activateWhore(gs: GamePublicState, card: CardPublicState, cardPosition: PublicPosition) = {
    activateDefaultWithTarget(gs, card, cardPosition)
  }

  def activateDefaultWithoutTarget(gs: GamePublicState, card: CardPublicState, cardPosition: PublicPosition): WorksWithoutTarget = {
    ???
  }

  def activateDefaultWithTarget(gs: GamePublicState, card: CardPublicState, cardPosition: PublicPosition): WorksWithTarget = {
    ???
  }

  def see(gs: GamePublicState, position: PublicPosition, cardType: CardType) = {
    //do nothing
  }

}