package blokus {


/**
 * A move is a piece applied to a particular place on the board.
 * The piece comes in a particular orientation.
 */
class Move (val player: Player, 
	    val piece: Piece, 
	    val point: Tuple2[Int,Int]) {

  def x: Int = point._1
  def y: Int = point._2
   
  def matrix: Matrix =
    piece.matrix.substitute(1, player.color)

  def cells : List[Tuple2[Int,Int]] = {
    piece.matrix.cellsWithValue(1)
  }

  override def toString: String = {
    "P" + player.color + " (" + x + ", " + y + "): " + piece.toString.replace("\n", "_")
  }

}

} // close package
