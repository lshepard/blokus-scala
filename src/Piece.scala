package blokus {

/**
 * A piece is just a specific matrix 0s of and 1s.
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
  
  /**
   * Returns the (x, y) coordinates of each
   * of the positive cells within this piece.
   * Allows callers to avoid looping through
   * the double-layer array to do work.
   */
  def cells: List[Tuple2[Int, Int]] = {
    var cells : List[Tuple2[Int, Int]] = List()
    for (i <- 0 until matrix.height)
      for (j <- 0 until matrix.width)
	if (matrix.m(i)(j) > 0)
	  cells = (i, j) :: cells

    cells
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

  // convenient for testing
  def single = new Piece("+")

}

} // close package
