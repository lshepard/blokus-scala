package blokus {

class Player (val color: Int,
	      var pieces: Array[Piece]) {
  
  def this(color: Int) =
    this(color, Piece.all)

  

}

}
