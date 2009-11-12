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
    new Board(matrix.add(move.matrix, move.x, move.y))
  }

  override def toString =
    matrix.toString.replace("0", " ")

}

} // close package
