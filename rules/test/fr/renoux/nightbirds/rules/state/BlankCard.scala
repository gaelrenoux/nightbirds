package fr.renoux.nightbirds.rules.state

object LegalBlankCardType extends CardType(Legal)
object IllegalBlankCardType extends CardType(Illegal)

class BlankCard(f: Family)(cardType: CardType) extends Card(f)(cardType) with WithoutTarget {
  override def specificActivate() = {
    store(Cash(2))
  }
}

class LegalBlankCard(f: Family) extends BlankCard(f)(LegalBlankCardType) with WithoutTarget {
  override def specificActivate() = {
    store(Cash(2))
  }
}

class IllegalBlankCard(f: Family) extends BlankCard(f)(IllegalBlankCardType) with WithoutTarget {
  override def specificActivate() = {
    store(Cash(2))
  }
}