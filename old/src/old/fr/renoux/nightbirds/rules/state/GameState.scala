package old.fr.renoux.nightbirds.rules.state

import fr.renoux.nightbirds.rules.elements.Family
import fr.renoux.nightbirds.rules.elements.District
import fr.renoux.nightbirds.rules.values.Cash
import fr.renoux.nightbirds.rules.Card

class GameState(val families: Set[Family], val districts: Set[District]) {

  def addToFamily(f: Family, c: Cash) = new GameState(families - f + f.store(c), districts)

  def takeFromFamily(f: Family, c: Cash) = new GameState(families - f + f.take(c), districts)

  def hold(f: Family, c: Card) = new GameState(families - f + f.hold(c), districts)

  /** release a card and get its bail money */
  def release(target: Card) {
    val bail = target.bail
    if (target.family.internalCash < bail) {
      throw new IllegalArgumentException
    }
    if (prison.remove(target)) {
      internalCash = internalCash + target.bail
      target.family.store((target.family.internalCash - target.bail).remaining)
      target.free()
    } else {
      throw new IllegalArgumentException
    }
  }

}