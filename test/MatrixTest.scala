package blokus.test {

  import blokus.Matrix
  
  object MatrixTest {
  
    def run { 
      assert(new Matrix(2, 3) 
	     == new Matrix(Array(Array(0, 0, 0),
				 Array(0, 0, 0))))

      assert(new Matrix(Array("555",
			      "666666",
			      "1"))
	     ==
	       new Matrix(Array("555000",
				"666666",
				"100000")))
	
      var m = new Matrix(Array("01239",
			       "34569"))
      
      assert(m.height == 2, "invalid height")
      assert(m.width == 5, "invalid width")
      
      assert(m.transpose == new Matrix(Array("03",
					     "14",
					     "25",
					     "36",
					     "99")))
    
      assert(m.flipHorizontal == new Matrix(Array("93210",
						  "96543")))
      
	
      assert(m.flipVertical == new Matrix(Array("34569",
						"01239")))
      
      // test adding matrices together

      assert(new Matrix("101").insert(new Matrix("2"),0,2)
	     ==
	     new Matrix("102"))

      assert(new Matrix("001","001").insert(new Matrix("2"),1,2)
	     ==
	     new Matrix("001","002"))

      assert(new Matrix("001","000").insertCells(
	List(Cell(0,0), Cell(1, 1)), 2)
	     ==
	       new Matrix("201","020"))

      assert(new Matrix("123","456").pad(1) ==
	new Matrix("00000",
		   "01230",
		   "04560",
		   "00000"))

    }
  }
}
