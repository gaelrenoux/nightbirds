package fr.renoux.nightbirds.rules.state

class GameState(val families: IndexedSeq[Family], val districts: IndexedSeq[District]) {

  /** To construct the initial game state for each round */
  def this(families: IndexedSeq[Family]) = {
    this(families, {
      var i = -1
      Vector.fill(families.length) { i = i + 1; new District(i) }
    })
  }

  def store(f: Family, c: Cash) = replaceFamily(f, f.store(c))
  def take(f: Family, c: Cash) = replaceFamily(f, f.take(c))

  def hold(f: Family, c: Card) = replaceFamily(f, f.hold(c))

  def store(cd: Card, c: Cash) = replaceCard(cd, cd.store(c))
  def take(cd: Card, c: Cash) = replaceCard(cd, cd.take(c))

  def replaceFamily(oldF: Family, newF: Family) = {
    val ixOld = families.indexOf(oldF)
    val newFamilies = families.updated(ixOld, newF)
    new GameState(newFamilies, districts)
  }

  def replaceDistrict(oldD: District, newD: District) = {
    val ixOld = families.indexOf(oldD)
    val newDistricts = districts.updated(ixOld, newD)
    new GameState(families, newDistricts)
  }

  def replaceCard(oldC: Card, newC: Card) = {
    val newDistricts = districts.map { d =>
      d.cards.indexOf(oldC) match {
        case -1 => d
        case ix => d.updated(ix, newC)
      }
    }

    new GameState(families, newDistricts)
  }
  
  def resetForRound = {
    new GameState(families)
  }

}