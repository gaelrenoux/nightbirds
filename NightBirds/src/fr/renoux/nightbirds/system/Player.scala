package fr.renoux.nightbirds.system

import fr.renoux.nightbirds.rules.generics.WithTarget
import fr.renoux.nightbirds.rules.generics.Card

/* Something that will react to events */
abstract class Player(val board: BoardView) {
  def reactToTarget(target: Card, source: Card): Boolean
  def reactToWitness(witness: Card, source: Card, target: Option[Card]): Boolean
  def activate(source: Card): Boolean
  def getTargetForActivation(source: Card): Card
  def playCard: Card
}