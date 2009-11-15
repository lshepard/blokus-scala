package blokus {

/**
 * A piece is just a specific matrix 0s of and 1s. It represents
 * a piece in the game at a specified orientation.
 */
class Piece (val matrix: Matrix) {

  def this(rows: String *) =
    this(new Matrix(rows.toArray.
		    map((x:String) => x.replace("+", "1"))))

  /**
   * Calculates all possible orientations of this piece, normalized
   * for isomorphic rotations.
   */ 
  def orientations: Set[Piece] = {
    val flipped = matrix.flipVertical
    Set(matrix, 
	matrix.rotateRight,
	matrix.rotateLeft,
	matrix.spin,
	flipped,
	flipped.rotateRight,
	flipped.rotateLeft,
	flipped.spin).map(new Piece(_))
  }

  def equals (that: Piece) = 
    this.orientations == that.orientations

  override def toString: String =
    matrix.m.map(_.map(_ match {
      case 0 => " "
      case 1 => "+"
      case _ => ""
    }).mkString("")).mkString("\n") 

}

object Piece {
  
  /**
   * A list of all 21 pieces in the official Blokus board game.
   */
  def all = List(
    new Piece("+"),
    new Piece("++"),

    // three squares
    new Piece("+++"),

    new Piece("++",
	      "+ "),

    // four squares
    new Piece("++++"),

    new Piece("+++",
	      "+  "),

    new Piece("+++",
	      " + "),

    new Piece("++",
	      "++"),

    new Piece("++ ",
	      " ++"),
    
    // five squares
	      
    new Piece("+++++"),

    new Piece("++++",
	      "+   "),
    
    new Piece("++  ",
	      " +++"),
    
    new Piece("+++",
	      "++ "),

    new Piece("+++",
	      "+ +"),
    
    new Piece("++++",
	      " +  "),

    new Piece("+++",
	      " + ",
	      " + "),
    
    new Piece("+++",
	      "+  ",
	      "+  "),

    new Piece("++ ",
	      " ++",
	      "  +"),
    
    new Piece("+  ",
	      "+++",
	      "  +"),
    
    new Piece("+  ",
	      "+++",
	      " + "),
    
    new Piece(" + ",
	      "+++",
	      " + ")
    )

  // convenient for testing
  def single = new Piece("+")

}

} // close package
