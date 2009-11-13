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
   * I know, I'm sure there's a great way to do
   * this recursively instead of iteratively.
   * I'm just trying to get it to work and will
   * change out the implementation later.
   */
  def possibleMoves(player: Player) : List[Move] = {
    // check for each piece, in each orientation, where
    // there is space for the piece on the board

    // naive way: iterate through every possible spot
    // on the board and check for location.
    var moves : List[Move] = List()
    for (set <- player.pieces.map(_.orientations)) yield {
      set.map((piece: Piece) =>
      for(i <- 0 until matrix.height)
	for (j <- 0 until matrix.width) {
	  val move = new Move(player, piece, i, j)
	  if (isLegalMove(move))
	    moves = move :: moves
	  else
	    null
	})
    }
    moves    
  }
	      
  /**
   * Decide whether a given move is legal in the context of this board.
   */
  def isLegalMove(move: Move) = {
/*
    println("------", move,
	    "inbounds:", isItInBounds(move),
	    "space:", isThereSpaceForMove(move),
	    "not adjacent", !isAdjacentToSelf(move),
	    "corner to self", isCornerToSelf(move),
	    "is corner move", isCornerMove(move),
	    "first move", isFirstMove(move))
*/
    if (isFirstMove(move))
      isCornerMove(move)
    else
      isItInBounds(move) &&
      isThereSpaceForMove(move) &&
      !isAdjacentToSelf(move) &&
      isCornerToSelf(move)
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
		  move.x, move.y).substitute(99, 0) == matrix
  }

  /**
   * Are we in a corner and it's the start of the game?
   */
  def isCornerMove(move: Move) : Boolean = {
    (move.x == 0 || 
     move.x == matrix.height) &&
    (move.y == 0 ||
     move.y == matrix.width)
  }

  /**
   * Is this our first move of the game?
   */
  def isFirstMove(move: Move) : Boolean = {
    matrix.cells(move.player.color).length == 0
  }

  /**
   * Checks if the corner of this piece touches itself.
   */
  def isCornerToSelf(move: Move) : Boolean = {
    // check for each bit on the board
    // at least one of the pieces must be at the corner

    move.cells.foreach((cell: Tuple2[Int,Int]) => {
      if (checkCorner(cell._1 + move.x,
		      cell._2 + move.y,
		      move.player.color))
	return true
    })
    false
  }
  def checkCorner(x: Int, y: Int, color: Int) : Boolean = {
    getCellValue(x - 1, y - 1) == color ||
    getCellValue(x + 1, y - 1) == color ||
    getCellValue(x - 1, y + 1) == color ||
    getCellValue(x + 1, y + 1) == color
  }
  
  /**
   * Check if the move is adjacent to anything on the board.
   */
  def isAdjacentToSelf(move: Move): Boolean = {
    move.cells.foreach((cell: Tuple2[Int,Int]) => {
      if (checkAdjacent(cell._1 + move.x,
			cell._2 + move.y,
			move.player.color))
	return true
    })
    false
  }

  def checkAdjacent(x: Int, y: Int, color: Int): Boolean = {
    getCellValue(x,     y - 1) == color ||
    getCellValue(x,     y + 1) == color ||
    getCellValue(x - 1, y    ) == color ||
    getCellValue(x + 1, y    ) == color
  }

  /**
   * Get the value at the given cell. Handles array overbounds
   * and just returns null.
   */
  def getCellValue(x: Int, y: Int) = {
    if (x >= matrix.height ||
	x < 0 ||
	y >= matrix.width ||
	y < 0)
      0
    else
      matrix.m(x)(y)
  }
    
  override def toString =
    matrix.m.map(_.map((x:Int) => x match {
      case 0 => "."
      case _ => x
    }).mkString("")).mkString("\n")

}

} // close package
