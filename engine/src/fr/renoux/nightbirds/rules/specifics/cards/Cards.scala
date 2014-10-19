package fr.renoux.nightbirds.rules.specifics.cards

import fr.renoux.nightbirds.rules.CardType
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Callbacks
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.Family

object CardFactory {
  def make(cardType: CardType, c: Callbacks, b: Board, f: Family): Card = (cardType : @unchecked) match {
    case fr.renoux.nightbirds.rules.Bum => new Bum(c, b, f)
    case fr.renoux.nightbirds.rules.Burglar => new Burglar(c, b, f)
    case fr.renoux.nightbirds.rules.Cook => new Cook(c, b, f)
    case fr.renoux.nightbirds.rules.Cop => new Cop(c, b, f)
    case fr.renoux.nightbirds.rules.Dealer => new Dealer(c, b, f)
    case fr.renoux.nightbirds.rules.Dj => new Dj(c, b, f)
    case fr.renoux.nightbirds.rules.Photograph => new Photograph(c, b, f)
    case fr.renoux.nightbirds.rules.PrivateEye => new PrivateEye(c, b, f)
    case fr.renoux.nightbirds.rules.Skinhead => new Skinhead(c, b, f)
    case fr.renoux.nightbirds.rules.Taxi => new Taxi(c, b, f)
    case fr.renoux.nightbirds.rules.Thug => new Thug(c, b, f)
    case fr.renoux.nightbirds.rules.Whore => new Whore(c, b, f)
  } 
}