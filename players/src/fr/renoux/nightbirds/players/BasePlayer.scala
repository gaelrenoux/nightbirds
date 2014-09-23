package fr.renoux.nightbirds.players

import fr.renoux.nightbirds.playercontract.PlayerLike.Initializer
import fr.renoux.nightbirds.playercontract.Board
import fr.renoux.nightbirds.playercontract.PlayerLike
import fr.renoux.nightbirds.playercontract.CardType
import fr.renoux.nightbirds.playercontract.Activation
import fr.renoux.nightbirds.playercontract.Status
import fr.renoux.nightbirds.playercontract.Target
import fr.renoux.nightbirds.playercontract.Color

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