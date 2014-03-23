package fr.renoux.nightbirds.game

import fr.renoux.nightbirds.rules.generics.Callbacks
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.WithoutTarget
import fr.renoux.nightbirds.rules.generics.WithoutTargetWithInfo
import fr.renoux.nightbirds.rules.generics.WithTarget
import fr.renoux.nightbirds.rules.generics.WithTargetWithInfo
import fr.renoux.nightbirds.system.Player
import fr.renoux.nightbirds.rules.specifics.cards.PrivateEye
import fr.renoux.nightbirds.rules.generics.District
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.specifics.cards.Taxi

class StubPlayer(board : Board, var target: Card = null) extends Player {

  override def reactToTarget(target: Card, source: Card) = true

  def reactToWitness(witness: Card, source: Card, target: Option[Card]) = true

  def activateWithoutTarget(source: WithoutTarget) = true

  def activateWithoutTargetWithInfo[Info](source: WithoutTargetWithInfo[Info]) = source match {
    case eye: PrivateEye => Some((board.districts(0), 0, board.districts(0), 0).asInstanceOf[Info])
    case _ => None
  }

  def activateWithTarget(source: WithTarget) = Some(target)

  def activateWithTargetWithInfo[Info](source: WithTargetWithInfo[Info]) = source match {
    case taxi: Taxi => Some(target, (board.districts(0), true).asInstanceOf[Info])
    case _ => None
  }
  
  def see(seen : Card) = {}

}