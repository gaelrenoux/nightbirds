package fr.renoux.nightbirds.rules.generics

abstract trait WithTargetAndMore[More] extends Card {

  /** Activate this card's power on another */
  final def activate() = {
    val target = callbacks.getTargetForActivation(this)
    val more = callbacks.getMoreForActivation[More](this)
    
    if (target.targeted(this)) {
      this.doProceed(target, more)

      board.getNeighbours(this).remove(target)
    } else {
      Neighbours(None, None)
    }
  }

  protected def doProceed(target: Card, more: More)
}