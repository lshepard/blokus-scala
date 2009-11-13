package blokus {


/**
 * A move is a piece applied to a particular place on the board.
 * The piece comes in a particular orientation.
 */
class Move (val player: Player, 
	    val piece: Piece, 
	    val x: Int, 
	    val y: Int) {
   
  def matrix: Matrix =
    piece.matrix.substitute(1, player.color)

  def cells : List[Tuple2[Int,Int]] = {
    piece.matrix.cells(1)
  }


  override def toString: String = {
    "P" + player.color + " (" + x + ", " + y + "): \n" + piece
  }

}

} // close package
