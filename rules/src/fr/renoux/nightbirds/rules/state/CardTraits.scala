package fr.renoux.nightbirds.rules.state

trait WithoutTarget extends Card {
  def activate(gs: GameState): Unit = {
    reveal()
    specificActivate(gs)
    tap()
  }
  protected def specificActivate(gs: GameState): Unit = specificActivate()
  protected def specificActivate(): Unit = Unit
}

trait WithTarget extends Card {
  def activate(target: Card, gs: GameState): Unit = {
    reveal()
    specificActivate(target, gs)
    tap()
  }
  protected def specificActivate(target: Card, gs: GameState): Unit = specificActivate(target)
  protected def specificActivate(target: Card): Unit = Unit
}