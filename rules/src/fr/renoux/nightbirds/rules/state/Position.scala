package fr.renoux.nightbirds.rules.state

case class Position(val district: District, val column: Int) {
  def public = PublicPosition(district.public, column)

  def left = if (column == 0) None else {
    Some(Position(district, column - 1))
  }

  def right = if (column == district.size - 1) None else {
    Some(Position(district, column + 1))
  }

  /** left and right neighbours */
  def neighbours = column match {
    case 0 => List(Position(district, column + 1))
    case _ if column == district.size - 1 => List(Position(district, column - 1))
    case _ => List(Position(district, column - 1), Position(district, column + 1))
  }

  def get = district(column)
}

/** Utility class to specify a card in a public district */
case class PublicPosition(val district: DistrictPublicState, val column: Int)