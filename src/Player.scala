package blokus {

class Player (val color: Int,
	      var pieces: List[Piece]) {
  
  def this(color: Int) =
    this(color, Piece.all)

  /**
   * This is the heart of the automated bot game. For now
   * it just picks the first possible move, but eventually
   * we can hook this up to algorithms and stuff.
   */
  def nextMove(board: Board): Move = {
    var moves = board.possibleMoves(this)
    if (!moves.isEmpty) {
      val move = moves.head
      pieces = pieces.tail // rely on the ordering nature
      move
    }
    else
      null
  }

}

}
