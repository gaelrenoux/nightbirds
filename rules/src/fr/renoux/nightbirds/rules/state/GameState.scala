package fr.renoux.nightbirds.rules.state

import fr.renoux.nightbirds.rules.cardtypes.Color
import scala.util.Properties
import fr.renoux.nightbirds.rules.state.Position

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

  def endRound() = {
    districts.foreach { d =>
      d.cards.foreach(_.sleep())
      d.clear()
    }
    families.foreach { _.resetHand() }
  }

  def find(c: Card) = {
    var index = -1
    val district = districts find { d =>
      index = d.indexOf(c)
      index > -1
    }
    district.map(Position(_, index))
  }

  /**
   * Returns the game state with the "fog of war" : only public information is included.
   *  This includes the revealed cards and the public informations about players.
   */
  def public = {
    new GamePublicState(families.map { _.public }, districts.map { _.public })
  }

  override def toString = {
    val builder = new StringBuilder
    val prefix = families.mkString("", " ", " : ")
    districts.mkString(prefix, " ; ", "")
  }

}

class GamePublicState(
  val families: IndexedSeq[FamilyPublicState],
  val districts: IndexedSeq[DistrictPublicState]) {

  def family(color: Color) = families.filter(_.color == color).headOption

}