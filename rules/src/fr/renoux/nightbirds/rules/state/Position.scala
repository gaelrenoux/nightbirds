package fr.renoux.nightbirds.rules.state

import fr.renoux.nightbirds.rules.engine.CheaterException

case class Position(val district: District, val column: Int) {
  if (column >= district.size) throw new CheaterException("Column %d does not exist in %s of size %d".format(column, district, district.size))
  
  def public = PublicPosition(district.public, column)

  def left = if (column == 0) None else {
    Some(Position(district, column - 1))
  }

  def right = if (column == district.size - 1) None else {
    Some(Position(district, column + 1))
  }

  /** left and right neighbours */
  def neighbours = (district.size, column) match {
    case (1, _) => List()
    case (_, 0) => List(Position(district, column + 1))
    case (s, c) if c == s - 1 => List(Position(district, column - 1))
    case _ => List(Position(district, column - 1), Position(district, column + 1))
  }

  def get = district(column)
}

/** Utility class to specify a card in a public district */
case class PublicPosition(val district: DistrictPublicState, val column: Int) {
   def get = district(column)

  def left = if (column == 0) None else {
    Some(PublicPosition(district, column - 1))
  }

  def right = if (column == district.size - 1) None else {
    Some(PublicPosition(district, column + 1))
  }
}