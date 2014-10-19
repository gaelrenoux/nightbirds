package fr.renoux.nightbirds.players

import fr.renoux.nightbirds.rules.PlayerLike.Initializer
import fr.renoux.nightbirds.rules.Board
import fr.renoux.nightbirds.rules.PlayerLike
import fr.renoux.nightbirds.rules.CardType
import fr.renoux.nightbirds.rules.Activation
import fr.renoux.nightbirds.rules.Status
import fr.renoux.nightbirds.rules.Target
import fr.renoux.nightbirds.rules.Color

abstract class BasePlayer extends PlayerLike {

  private var _myColor: Color = null
  private var _others: Seq[Color] = null
  private var _firstPlayer: Color = null

  final def init(): Initializer = new Initializer {
    def you(yours: Color) = { _myColor = yours; this }
    def them(others: Color*): Initializer = { _others = others; this }
    def first(first: Color): Initializer = { _firstPlayer = first; this }
  }

  lazy val myColor = _myColor
  lazy val others = _others
  lazy val firstPlayer = _firstPlayer

  var board : Board = null
  var status : Status = null

  final def update(board: Board, status: Status) = {
    this.board = board
    this.status = status
    
    /* proceed to natural deductions */
    
    this.update()
  }
  
  def update()

}