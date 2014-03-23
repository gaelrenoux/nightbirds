package fr.renoux.nightbirds.rules.generics

abstract trait WithoutTarget extends Card {

  /** Activate this card's power */
  final def activate() = {
    if (callbacks.activateWithoutTarget(this)) {
      this.doProceed()
      SuccessfulActivation(this, None, board.getNeighbours(this))
    } else {
      DeclinedActivation
    }
  }

  /** Whatever happens when the card is activated with success */
  protected def doProceed()
}