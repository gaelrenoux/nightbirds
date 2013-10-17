package fr.renoux.nightbirds.game

import fr.renoux.nightbirds.rules.generics.Callbacks
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.WithTargetAndMore

class StubPlayer extends Callbacks {
  
  var target = null

  def reactToTarget(target: Card, source: Card) = true

  def reactToWitness(witness: Card, source: Card, target: Option[Card]) = true

  def activate(source: Card) = true

  def getTargetForActivation(source: Card) = target
  
  def getMoreForActivation[More](source: WithTargetAndMore[More]): More = throw new UnsupportedOperationException

}