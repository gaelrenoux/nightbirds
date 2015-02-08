package fr.renoux.nightbirds.rules.state

class IllegalBlankCardTest extends AbstractCardTest[BlankCard] {
  
  override def prepare() {
    card = new IllegalBlankCard(family)
  }
}