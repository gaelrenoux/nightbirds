package fr.renoux.nightbirds.rules.state

trait WithoutTarget extends Card {
  def activate(gs: GameState): Unit = {
    reveal()
    specificActivate(gs)
    tap()
  }
  def specificActivate(gs: GameState): Unit = specificActivate()
  def specificActivate(): Unit = Unit
}

trait WithTarget extends Card {
  def activate(target: Card, gs: GameState): Unit = {
    reveal()
    specificActivate(target, gs)
    tap()
  }
  def specificActivate(target: Card, gs: GameState): Unit = specificActivate(target)
  def specificActivate(target: Card): Unit = Unit
}