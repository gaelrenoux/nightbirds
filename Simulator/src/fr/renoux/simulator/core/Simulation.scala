package fr.renoux.simulator.core

import scala.collection.mutable

abstract class Simulation[A <: Analysis] {
  
  val iterations : Int
  val factory : GameFactory

  protected def agregate(r: Result)
  def getAnalysis: A

  def execute = {
    for (i <- 0 until iterations) {
      val g = factory.makeGame
      val r = g.execute
      agregate(r)
    }
  }
}