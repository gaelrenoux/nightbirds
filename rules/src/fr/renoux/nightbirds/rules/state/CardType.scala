package fr.renoux.nightbirds.rules.state

class CardType(val legality: Legality = Legal) {
  
  

}

trait WithTarget extends CardType {
  def activate(subject : Card, target : Card)(implicit gs: GameState)
}

trait WithoutTarget extends CardType {
  def activate(subject : Card)(implicit gs : GameState)
}