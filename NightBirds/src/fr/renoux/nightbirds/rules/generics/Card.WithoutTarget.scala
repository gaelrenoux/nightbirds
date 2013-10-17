package fr.renoux.nightbirds.rules.generics

abstract trait WithoutTarget extends Card {

  /** Activate this card's power */
  final def activate() = {
    this.doProceed()
    board.getNeighbours(this)
  }
  
  /** Whatever happens when the card is activated with success */
  protected def doProceed()
}