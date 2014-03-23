package fr.renoux.simulator.core

import scala.collection.mutable

sealed trait Game {
  def execute(): Result
}