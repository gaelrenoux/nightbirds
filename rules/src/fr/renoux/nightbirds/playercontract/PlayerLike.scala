package fr.renoux.nightbirds.playercontract

trait PlayerLike {

  def init(): PlayerLike.Initializer

  def update(board: Board, status: Status)

  /** Called at the end of each turn */
  def reset()

  /** Returns a card to play and a district index. It will not be called unless the latest status received is Playing */
  def play: (CardType, Int)

  /** The current status indicates it is one of your face down cards. Activate it ? */
  def reveal(): Activation

  /** One of your card is a witness, do something with it ? Your target is necessarily the acting card */
  def witness(witness: CardType): Activation

  /** One of your card is a target, do something with it ? Your target is necessarily the acting card */
  def target(target: CardType): Activation

  /** The active player is spying on this card. Do you want to pay to see ? */
  def privateEye(target: Target): Boolean

}

object PlayerLike {
  trait Initializer {
    def you(yours: Color): Initializer
    def them(others: Color*): Initializer
    def first(first: Color): Initializer
  }
}

