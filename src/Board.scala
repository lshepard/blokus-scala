package blokus {

/**
 * Represents the state of the board at any given cell.
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
    if (isLegalMove(move))
      new Board(matrix.insert(move.matrix, move.x, move.y))
    else
      throw new Exception("Could not make that move")
  }

  /**
   * Calculate a list of all possible legal moves
   * for this player on this board.
   *
   * This is not the most efficient implementation.
   *
   */
  def possibleMoves(player: Player) : List[Move] = {
    // check for each piece, in each orientation, where
    // there is space for the piece on the board

    val allPossiblePieces = player.pieces.flatMap(_.orientations)
    val cells = matrix.cells

    /**
     * Get all legal moves for the specific piece out of all
     * possible cells on the board.
     */
    def possibleMovesForPiece(piece: Piece) = {
      cells.map(new Move(player, piece, _)).filter(isLegalMove)
    }

    // now take the cross product of these two
    allPossiblePieces.toList.flatMap(possibleMovesForPiece)
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
    isItInBounds(move) &&
    isThereSpaceForMove(move) &&
    (if (isFirstMove(move))
      isCornerMove(move)
    else
      !isAdjacentToSelf(move.cells, move.player.color) &&
       isCornerToSelf(move))
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
		  move.cell._1, move.cell._2).substitute(99, 0) == matrix
  }

  /**
   * Does the move touch a corner?
   */
  def isCornerMove(move: Move) : Boolean = {
    move.cells.map((cell: Cell) =>
      (cell.x == 0 ||
       cell.x == matrix.height - 1) &&
      (cell.y == 0 ||
       cell.y == matrix.width - 1)).
    reduceLeft(_ || _)
  }

  /**
   * Is this our first move of the game?
   */
  def isFirstMove(move: Move) : Boolean = {
    matrix.cellsWithValue(move.player.color).length == 0
  }

  /**
   * Check if the move is adjacent to anything on the board.
   */
  def isAdjacentToSelf(cells: List[Cell], color: Int): Boolean =
    cells.flatMap(_.neighbors).
      map(matrix.present(_)).
      reduceLeft(_ || _)

  def isCornerToSelf(move: Move): Boolean = {
    (move.piece.corners.
     map(_.shift(move.cell)).
     filter(matrix.present(_)).length > 0)
  }

  override def toString =
    matrix.m.map(_.map((x:Int) => x match {
      case 0 => "."
      case _ => x
    }).mkString("")).mkString("\n")

}

} // close package
