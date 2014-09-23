package fr.renoux.nightbirds.simulation

import fr.renoux.nightbirds.system.Player
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.playercontract.Yellow
import fr.renoux.nightbirds.playercontract.Blue
import fr.renoux.nightbirds.playercontract.Orange
import fr.renoux.nightbirds.playercontract.Black

class Simulation(playersNb : Int) {
  
  //val players = List.fill(playersNb)(new StubPlayer)
  val families = List(new Family(Orange), new Family(Yellow), new Family(Black), new Family(Blue))
  
  val board = new Board(families:_*)

  def doTurn() = {
    
    /* reset du plateau */
    board.districts.foreach { _.reset}
    
    /* tour de jeu de chaque joueur */
//    for (x <- 1 to 4) {
//      players.foreach { p =>
//      val (c, d) = p.playCard
//      d.add(c)
//      }
//    }
//    
    /* parcours des quartiers et des cartes */
    board.districts.map { _.cards }.flatten.foreach { c=>
      
      
     // c.callbacks.activateWithoutTarget(c);
    }
    
    
  }
  
}