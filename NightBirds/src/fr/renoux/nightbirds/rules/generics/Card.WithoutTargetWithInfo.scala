package fr.renoux.nightbirds.rules.generics

trait WithoutTargetWithInfo[Info] extends Card {

  /** Activate this card's power on another */
  final def activate() = callbacks.activateWithoutTargetWithInfo(this) match {

    case None => DeclinedActivation

    case Some(info) =>
      this.doProceed(info)
      SuccessfulActivation(this, None, board.getNeighbours(this), Some(info))

  }

  protected def doProceed(info: Info)
}