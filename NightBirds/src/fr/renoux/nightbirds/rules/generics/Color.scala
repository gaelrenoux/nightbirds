package fr.renoux.nightbirds.rules.generics;

abstract class Color {
  def getCards(c : Callbacks, b: Board, f: Family): Set[Card]
}
