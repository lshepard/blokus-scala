package blokus {

/**
 * A piece is just a specific matrix 0s of and 1s. It represents
 * a piece in the game at a specified orientation.
 */
class Piece (val matrix: Matrix) {
  val allRotations = calculateOrientations
  
  def this(rows: String *) =
    this(new Matrix(rows.toArray.
		    map((x:String) => x.
			replace("+", "1").
			replace(" ", "0"))))

  /**
   * Calculates all possible orientations of this piece, normalized
   * for isomorphic rotations.
   */
  private def calculateOrientations: Set[Matrix] = {
    val flipped = matrix.flipVertical
    Set(matrix, 
	matrix.rotateRight,
	matrix.rotateLeft,
	matrix.spin,
	flipped,
	flipped.rotateRight,
	flipped.rotateLeft,
	flipped.spin)
  }

  def orientations: Set[Piece] = allRotations.map(new Piece(_))

  /**
   * Returns the corners of this piece.
   */
  def corners: List[Cell] = {

    /**
     * Okay, for a given cell, we will determine
     * if any of its surrounding pieces are a corner.
     * Do this by comparing each of its unoccupied
     * adjacent cells against other adjacent unoccupied
     * neighbors.
     */
    def cornersForCell(cell: Cell) = {
      val emptyNeighbors = cell.neighbors.
        filter(matrix.value(_,0) == 0)

      emptyNeighbors.flatMap((cell1: Cell) =>
	for (cell2 <- emptyNeighbors;
	     if (cell2 != cell1 &&
		 cell1.x == cell.x && // de-dupe
		 cell2.x != cell1.x &&
		 cell2.y != cell1.y &&
		 ((cell2.x - cell1.x).abs + // no more than one square away
		  (cell2.y - cell1.y).abs == 2)
	       ))
	  yield new Cell(cell2.x, cell1.y)
	  );
    }

    cells.flatMap(cornersForCell)
  }

  /*
   * Get the cells that make up this piece.
   */
  def cells: List[Cell] = matrix.cells.filter(matrix.value(_,0) == 1)

  /**
   * Prints out a representation of this with the cells marked as "2"
   */
  def cellsToString(cells: List[Cell]): String =
    matrix.pad(1).insertCells(cells.map((cell: Cell) =>
      Cell(cell.x + 1, cell.y + 1)), 2).toString

  /**
   * Equality is defined by 
   */
  override def hashCode: Int = allRotations.hashCode
  override def equals(that: Any) : Boolean = that match {
    case that: Piece => (this.allRotations == that.allRotations)
    case _ => false
  }

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
  def all = Set(
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
