package blokus {

class Board (matrix: Matrix) {

  /**
   * Define an empty board of a particular size.
   */
  def this(x: Int, y: Int) =
    this(new Matrix(new Array[Array[Int]](x).
		    map(_ => new Array[Int](y).
			map(_ => 0))))

  def makeMove(move: Move): Board = {
    new Board(matrix.insert(move.matrix, move.x, move.y))
  }

  /**
   * Calculate a list of all possible legal moves
   * for this player on this board.
   */
  def possibleMoves(player: Player) : Array[Move] = {
    // check for each piece, in each orientation, where
    // there is space for the piece on the board

    // naive way: iterate through every possible spot
    // on the board and check for location.
    for (set <- player.pieces.map(_.orientations)) yield {
      set.map((piece: Piece) =>
      for(i <- 0 until matrix.height)
	for (j <- 0 until matrix.width) {
	  val move = new Move(player, piece, i, j)
	  if (isLegalMove(move))
	    move
	  else
	    null
	})
    }

    Array[Move](null)
  }
	      
  /**
   * Decide whether a given move is legal in the context of this board.
   */
  def isLegalMove(move: Move) = {
    isThereSpaceForMove(move) &&
    isCornerToSelf(move) &&
    !isAdjacentToSelf(move)
  }

  /**
   * Figure out if there is empty space on the board for this move.
   *
   * Map the new piece to an int nobody will use (99), and insert it
   * Then replace all 99s with 0s and check that the boards are the same
   *
   */
  def isThereSpaceForMove(move: Move) = {
    matrix.insert(move.piece.m.substitute(1, 99),
		  move.x, move.y).substitute(99, 0) == matrix
  }

  // TODO
  def isCornerToSelf(move: Move) = true
  def isAdjacentToSelf(move: Move) = false

    
  override def toString =
    matrix.toString.replace("0", " ")

}

} // close package
