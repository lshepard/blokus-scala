package blokus.test {

  import blokus.Cell
  
  object CellTest {
    def run {
      assert(Cell.list((0, 1),
		       (0, 2),
		       (0, 3),
		       (0, 4))
	     == List(new Cell(0, 1),
		     new Cell(0, 2),
		     new Cell(0, 3),
		     new Cell(0, 4)))

      assert(Cell(2,2).neighbors ==
	Cell.list((1, 2),
		  (3, 2),
		  (2, 1),
		  (2, 3)))

    }
  }
}
