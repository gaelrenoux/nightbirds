package fr.renoux.nightbirds.rules.state

class GameState(
  val families: IndexedSeq[Family],
  val districts: IndexedSeq[District]) {

  /** To construct the initial game state for each round */
  def this(families: IndexedSeq[Family]) = {
    this(families, {
      var i = -1
      Vector.fill(families.length) { i = i + 1; new District(i) }
    })
  }

  def endRound() = districts.foreach { d =>
    d.cards.foreach(_.sleep())
    d.clear()
  }

  /**
   * Returns the game state with the "fog of war" : only public information is included.
   *  This includes the revealed cards and the public informations about players.
   */
  def public = this

}