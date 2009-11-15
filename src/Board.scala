package blokus {

/**
 * Represents the state of the board at any given point.
 * Handles all the aspects of making and checking moves
 * for validity.
 *
 * Each Board object is immutable. You can make moves,
 * but they only produce new boards.
 * 
 */
class Board (val matrix: Matrix) {

  /**
   * Define an empty board of a particular size.
   */
  def this(x: Int, y: Int) =
    this(new Matrix(new Array[Array[Int]](x).
		    map(_ => new Array[Int](y).
			map(_ => 0))))

  /**
   * Apply a move to this board, and return a new
   * Board object in return.
   */
  def makeMove(move: Move): Board = {
    new Board(matrix.insert(move.matrix, move.x, move.y))
  }

  /**
   * Calculate a list of all possible legal moves
   * for this player on this board.
   *
   */
  def possibleMoves(player: Player) : List[Move] = {
    // check for each piece, in each orientation, where
    // there is space for the piece on the board

    val allPossiblePieces = player.pieces.flatMap(_.orientations)
    val points = matrix.cells

    /**
     * Get all legal moves for the specific piece out of all
     * possible points on the board.
     */
    def possibleMovesForPiece(piece: Piece) = {
      points.map(new Move(player, piece, _)).filter(isLegalMove)
    }

    // now take the cross product of these two
    allPossiblePieces.flatMap(possibleMovesForPiece)
  }
	      
  /**
   * Decide whether a given move is legal in the context of this board.
   *
   * Either it's the first move, in which case we have to go in a corner,
   * or we need to make sure that it's NOT adjacent to itself,
   * and that it touches on at least one corner.
   *
   * (For now, this function doesn't calculate the corner-to-self rule)
   */
  def isLegalMove(move: Move) : Boolean = {
    if (isFirstMove(move)) {
      isItInBounds(move) &&
      isCornerMove(move)
    }
    else
      (isItInBounds(move) &&
       isThereSpaceForMove(move) &&
       !isAdjacentToSelf(move.cells, move.player.color))
  }

  /**
   * Gotta check this first to avoid array out of bounds exceptions.
   */
  def isItInBounds(move: Move) : Boolean = {
    (move.x + move.piece.matrix.height <= matrix.height) &&
    (move.y + move.piece.matrix.width <= matrix.width)
  }

  /**
   * Figure out if there is empty space on the board for this move.
   *
   * Map the new piece to an int nobody will use (99), and insert it
   * Then replace all 99s with 0s and check that the boards are the same
   *
   */
  def isThereSpaceForMove(move: Move) : Boolean = {
    matrix.insert(move.piece.matrix.substitute(1, 99),
		  move.point._1, move.point._2).substitute(99, 0) == matrix
  }

  /**
   * Are we in a corner and it's the start of the game?
   */
  def isCornerMove(move: Move) : Boolean = {
    (move.x == 0 || 
     move.x == matrix.height - 1) &&
    (move.y == 0 ||
     move.y == matrix.width - 1)
  }

  /**
   * Is this our first move of the game?
   */
  def isFirstMove(move: Move) : Boolean = {
    matrix.cellsWithValue(move.player.color).length == 0
  }

  def getAdjacentCells(p: Tuple2[Int, Int]) : List[Tuple2[Int, Int]] =
    List((p._1    , p._2 - 1),
	 (p._1    , p._2 + 1),
	 (p._1 - 1,     p._2),
	 (p._1 + 1,     p._2))

  /**
   * Check if the move is adjacent to anything on the board.
   */
  def isAdjacentToSelf(cells: List[Tuple2[Int,Int]], color: Int): Boolean =
    cells.flatMap(getAdjacentCells).
      map(getCellValue(_) == 1).reduceLeft(_ || _)
  
  /**
   * Get the value at the given cell. Handles array overbounds
   * and just returns null.
   */
  def getCellValue(point: Tuple2[Int,Int]) = {
    if (point._1 >= matrix.height ||
	point._1 < 0 ||
	point._2 >= matrix.width ||
	point._2 < 0)
      0
    else
      matrix.m(point._1)(point._2)
  }
    
  override def toString =
    matrix.m.map(_.map((x:Int) => x match {
      case 0 => "."
      case _ => x
    }).mkString("")).mkString("\n")

}

} // close package
