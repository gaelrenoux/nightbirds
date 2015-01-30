package fr.renoux.nightbirds.rules.state

case class Position(val district : District, val column : Int) {
  def public = PublicPosition(district.public, column)
  
  def left = if (column == 0) None else {
    Some(Position(district, column - 1))
  }
  
  def right = if (column == district.size -1) None else {
    Some(Position(district, column + 1))
  }
  
  def get = district(column)
}

/** Utility class to specify a card in a public district */
case class PublicPosition(val district : DistrictPublicState, val column : Int)