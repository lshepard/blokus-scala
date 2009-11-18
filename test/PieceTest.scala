package blokus.test {

  import blokus.Piece

  object PieceTest {
    def run {

      // check the lengths
      assert(Piece.all.size == 21)

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

      assert(  new Piece("+++",
			 "+")
	     == 
	       new Piece("+++",
			 "+"))

      var pieces = Set(new Piece("+++",
				 "+"),
		       new Piece("+++",
				 "  +"))
      assert(pieces.size == 1)

      pieces = Set(new Piece("+++",
			     "+"),
		   new Piece("+++++"),
		   new Piece("+++",
			     "+ +"))

      // notice that we are subtracting a rotated version
      assert(pieces - new Piece("+",
				"+",
				"++")
	     ==
	       Set(new Piece("+++++"),
		   new Piece("+++",
			     "+ +")))
    }
  }
}
