package fr.renoux.nightbirds.rules.state

class LegalBlankCardTest extends AbstractCardTest[BlankCard] {
  
  override def prepare() {
    card = new LegalBlankCard(family)
  }
}