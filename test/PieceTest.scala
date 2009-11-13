package blokus.test {

  import blokus.Piece

  object PieceTest {
    def run {
/*
 * not sure how to flatten lists yet
      assert(new Piece("+++").orientations.toList.flatten
	     == List(new Piece("+++"),
		     new Piece("+",
			       "+",
			       "+")))
*/

      // check the lengths
      assert(Piece.all.length == 21)
      assert(Piece.all.map(_.orientations.toList).reduceLeft(_ ::: _).toArray.length
	     == 91)
    }
  }
}
