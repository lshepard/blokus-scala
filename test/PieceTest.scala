package blokus.test {

  import blokus.Piece

  object PieceTest {
    def run {

      // check the lengths
      assert(Piece.all.length == 21)
      assert(Piece.all.map(_.orientations.toList).reduceLeft(_ ::: _).toArray.length
	     == 91)

      val cells = new Piece("+++", " + ").matrix.cellsWithValue(1)
      assert(cells == Cell.list((0, 0),
				(0, 1),
				(0, 2),
				(1, 1)))
      
      val piece = new Piece("+++",
			    "+")

      assert(piece.corners == List(Cell(-1, -1),
				   Cell(-1, 3),
				   Cell(1, 3),
				   Cell(2, -1),
				   Cell(2, 1)))
    }
  }
}
