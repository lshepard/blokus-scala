package blokus {


/**
 * A move is a piece applied to a particular place on the board.
 * The piece comes in a particular orientation.
 */
class Move (val player: Player, 
	    val piece: Piece, 
	    val cell: Cell) {

  def this(player: Player, piece: Piece, tuple: Tuple2[Int,Int]) =
    this(player, piece, Cell(tuple))

  def x: Int = cell.x
  def y: Int = cell.y
   
  def matrix: Matrix =
    piece.matrix.substitute(1, player.color)

  /**
   * Returns the cells of the board matrix which this move
   * would occupy.
   * This means taking the cells of the piece - which are origined
   * at (0,0), and translating to the origin of the given point.
   */
  def cells : List[Cell] = {
    piece.matrix.cellsWithValue(1).map(
      (p: Cell) => {
	Cell(p.x + cell.x, p.y + cell.y)
      })
  }

  override def toString: String = {
    "P" + player.color + " (" + x + ", " + y + "): " + piece.toString.replace("\n", "_")
  }

}

} // close package
