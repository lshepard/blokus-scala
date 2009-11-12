package blokus {


/**
 * A move is a piece applied to a particular place on the board.
 * The piece comes in a particular orientation.
 */
class Move (player: Player, 
	    val piece: Piece, 
	    val x: Int, 
	    val y: Int) {
   
  def matrix: Matrix =
    new Matrix(piece.m.substitute(1, player.color))

}

} // close package
