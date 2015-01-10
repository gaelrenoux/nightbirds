package old.fr.renoux.nightbirds.rules.generics

/** Wehenever it is possible but not mandatory to do an action, ask one of these. */
trait Callbacks {
  
  def reactToTarget(target: Card, source: Card): Boolean
  
  def reactToWitness(activation : SuccessfulActivation, witness : Card): Boolean
  
  def activateWithTarget(source: WithTarget): Option[Card]
  
  def activateWithTargetWithInfo[Info](source : WithTargetWithInfo[Info]) : Option[(Card, Info)]
  
  def activateWithoutTarget(source: WithoutTarget) : Boolean
  
  def activateWithoutTargetWithInfo[Info](source : WithoutTargetWithInfo[Info]) : Option[Info]
  
  def see(seen : Card)
}