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

  /**
   * Returns the cells of the board matrix which this move
   * would occupy.
   * This means taking the cells of the piece - which are origined
   * at (0,0), and translating to the origin of the given point.
   */
  def cells : List[Tuple2[Int,Int]] = {
    piece.matrix.cellsWithValue(1).map(
      (p: Tuple2[Int,Int]) => {
	(p._1 + point._1, p._2 + point._2)
      })
  }

  override def toString: String = {
    "P" + player.color + " (" + x + ", " + y + "): " + piece.toString.replace("\n", "_")
  }

}

} // close package
