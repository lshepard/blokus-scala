package blokus {

/**
 * A piece is just a specific matrix 0s of and 1s.
 */
class Piece (val m: Matrix) {

  def this(rows: String *) =
    this(new Matrix(rows.toArray.
		    map((x:String) => x.replace("+", "1"))))

  /**
   * Calculates all possible orientations of this piece, normalized
   * for isomorphic rotations.
   */ 
  def orientations: Set[Piece] = {
    val flipped = m.flipVertical
    Set(m, 
	m.rotateRight,
	m.rotateLeft,
	m.spin,
	flipped,
	flipped.rotateRight,
	flipped.rotateLeft,
	flipped.spin).map(new Piece(_))
  }

  def equals (that: Piece) = 
    this.orientations == that.orientations

  override def toString: String =
    m.m.map(_.map(_ match {
      case 0 => " "
      case 1 => "+"
      case _ => ""
    }).mkString("")).mkString("\n") 

}

object Piece {
  

  def all = Array(
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

}

} // close package
