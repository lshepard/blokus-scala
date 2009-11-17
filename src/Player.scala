package blokus {

class Player (val color: Int,
	      var pieces: Set[Piece]) {
  
  def this(color: Int) =
    this(color, Piece.all)

  /**
   * This is the heart of the automated bot game. For now
   * it just picks the first possible move, but eventually
   * we can hook this up to algorithms and stuff.
   */
  def nextMove(board: Board): Move = {
    var moves = board.possibleMoves(this)
    if (moves.isEmpty)
      null
    else {
      val move = chooseMove(moves)
      // remove the piece
      pieces = pieces - move.piece
      move
    }
  }

  /**
   * This is the method that other players will have to override.
   * You get a list of all possible moves, and it's up to you to
   * sort through them.
   *
   * The default implementation just chooses one randomly.
   */
  def chooseMove(moves: List[Move]) = {
    val rng = new util.Random()
    moves(rng.nextInt(moves.length))
  }

  override def toString =
    "Player" + color + " with " + pieces.size + " pieces."
}

}
