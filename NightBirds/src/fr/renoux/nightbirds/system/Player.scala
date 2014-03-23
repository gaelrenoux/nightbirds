package fr.renoux.nightbirds.system

import fr.renoux.nightbirds.rules.generics.WithTarget
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.WithoutTargetWithInfo
import fr.renoux.nightbirds.rules.generics.Callbacks
import fr.renoux.nightbirds.rules.specifics.cards.Cop
import fr.renoux.nightbirds.rules.generics.WithTargetWithInfo
import fr.renoux.nightbirds.rules.specifics.cards.Skinhead
import fr.renoux.nightbirds.rules.generics.WithoutTarget
import fr.renoux.nightbirds.rules.generics.SuccessfulActivation

/* Something that will react to events */
abstract class Player extends Callbacks {
    
  def reactToTarget(target: Card, source: Card): Boolean = target match {
    case cop : Cop => doTargetedCop(cop, source)
    case skin : Skinhead => doTargetedSkinhead(skin, source)
    case _ => false
  }
  
  def reactToWitness(activation : SuccessfulActivation, witness: Card): Boolean = witness match {
    case cop : Cop if activation.source.isReprehensible => doWitnessIllegalCop(cop, activation.target, activation.source)
    case _ => false
  }
  
  def activateWithTarget(source: WithTarget): Option[Card]
  
  def activateWithTargetWithInfo[Info](source : WithTargetWithInfo[Info]) : Option[(Card, Info)]
  
  def activateWithoutTarget(source: WithoutTarget) : Boolean
  
  def activateWithoutTargetWithInfo[Info](source : WithoutTargetWithInfo[Info]) : Option[Info]
  
  def see(seen : Card)
  
  protected def doWitnessIllegalCop(witness: Cop, target: Option[Card], source: Card) = true
  protected def doTargetedCop(target: Cop, source: Card) = true
  protected def doTargetedSkinhead(target: Skinhead, source: Card) = true
}