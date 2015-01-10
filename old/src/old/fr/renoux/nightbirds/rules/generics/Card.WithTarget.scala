package old.fr.renoux.nightbirds.rules.generics

abstract trait WithTarget extends Card {

  /** Activate this card's power on another */
  final def activate() = callbacks.activateWithTarget(this) match {

    case None => DeclinedActivation

    case Some(target) =>
      if (target.targeted(this)) {
        this.doProceed(target)
        SuccessfulActivation(this, Some(target), board.getNeighbours(this).remove(target))
      } else {
        BlockedActivation(this, target)
      }

  }

  protected def doProceed(target: Card)
}