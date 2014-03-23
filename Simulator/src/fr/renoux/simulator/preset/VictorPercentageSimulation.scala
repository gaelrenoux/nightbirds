package fr.renoux.simulator.preset

import fr.renoux.simulator.core.Simulation
import fr.renoux.simulator.core.Game
import fr.renoux.simulator.core.Result
import fr.renoux.simulator.core.Analysis
import fr.renoux.simulator.core.Player
import java.util.HashMap
import scala.collection.mutable
import scala.collection.mutable.SynchronizedMap
import scala.actors.threadpool.AtomicInteger
import fr.renoux.simulator.core.GameFactory

class VictorPercentageAnalysis(val percentage : Map[Player, Double]) extends Analysis 

class VictorPercentageSimulation(val iterations: Int, val factory : GameFactory) extends Simulation[VictorPercentageAnalysis] {
  
  private var scores = new mutable.HashMap[Player, AtomicInteger] with mutable.SynchronizedMap[Player, AtomicInteger]
  
  protected def agregate(r: Result) = 
    scores.getOrElseUpdate(r.victor, new AtomicInteger).incrementAndGet()
  
  override def getAnalysis = {
    val total = iterations.toDouble
    val percentages = scores.mapValues(_.intValue().toDouble / total * 100).toMap
    
    new VictorPercentageAnalysis(percentages)
  }

}