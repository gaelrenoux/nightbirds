package old.fr.renoux.nightbirds.rules.generics

trait WithTargetWithInfo[Info] extends Card {

  /** Activate this card's power on another */
  final def activate() = callbacks.activateWithTargetWithInfo(this) match {

    case None => DeclinedActivation

    case Some((target, info)) =>
      if (target.targeted(this)) {
        this.doProceed(target, info)
        SuccessfulActivation(this, Some(target), board.getNeighbours(this).remove(target), Some(info))
      } else {
        BlockedActivation(this, target, Some(info))
      }

  }

  protected def doProceed(target: Card, info: Info)
}