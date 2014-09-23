package fr.renoux.nightbirds.rules.specifics.cards

import fr.renoux.nightbirds.playercontract.CardType
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Callbacks
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.rules.generics.Family

object CardFactory {
  def make(cardType: CardType, c: Callbacks, b: Board, f: Family): Card = (cardType : @unchecked) match {
    case fr.renoux.nightbirds.playercontract.Bum => new Bum(c, b, f)
    case fr.renoux.nightbirds.playercontract.Burglar => new Burglar(c, b, f)
    case fr.renoux.nightbirds.playercontract.Cook => new Cook(c, b, f)
    case fr.renoux.nightbirds.playercontract.Cop => new Cop(c, b, f)
    case fr.renoux.nightbirds.playercontract.Dealer => new Dealer(c, b, f)
    case fr.renoux.nightbirds.playercontract.Dj => new Dj(c, b, f)
    case fr.renoux.nightbirds.playercontract.Photograph => new Photograph(c, b, f)
    case fr.renoux.nightbirds.playercontract.PrivateEye => new PrivateEye(c, b, f)
    case fr.renoux.nightbirds.playercontract.Skinhead => new Skinhead(c, b, f)
    case fr.renoux.nightbirds.playercontract.Taxi => new Taxi(c, b, f)
    case fr.renoux.nightbirds.playercontract.Thug => new Thug(c, b, f)
    case fr.renoux.nightbirds.playercontract.Whore => new Whore(c, b, f)
  } 
}