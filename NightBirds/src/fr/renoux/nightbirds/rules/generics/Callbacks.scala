package fr.renoux.nightbirds.rules.generics

/** Wehenever it is possible but not mandatory to do an action, ask one of these. */
trait Callbacks {
  
  def reactToTarget(target: Card, source: Card): Boolean
  
  def reactToWitness(witness: Card, source: Card, target: Option[Card]): Boolean
  
  def activate(source: Card): Boolean
  
  def getTargetForActivation(source: Card): Card
  
  def getMoreForActivation[More](source: WithTargetAndMore[More]): More
}