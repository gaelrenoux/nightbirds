package fr.renoux.nightbirds.rules.generics

abstract trait WithTarget extends Card {

  /** Activate this card's power on another */
  final def activate() = {
    val target = callbacks.getTargetForActivation(this)
    if (target.targeted(this)) {
      this.doProceed(target)

      board.getNeighbours(this).remove(target)
    } else {
      Neighbours(None, None)
    }
  }

  protected def doProceed(target: Card)
}