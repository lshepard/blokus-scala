package blokus.test {

  import blokus.Board
  
  object BoardTest {
  
    def run { 

      // start out empty
      val b = new Board(5, 5)

      // make first move
      val p1 = new Piece("+++")
      val player1 = new Player(1)
      
      val b2 = b.makeMove(new Move(player1, p1, 1, 1))

      // make second move
      val p2 = new Piece("+ +",
			 " + ",
			 "+++")
      val player2 = new Player(2)
      val b3 = b2.makeMove(new Move(player2, p2, 2, 2))
    
      println(b, "\n\n", b2, "\n\n", b3)
    }
  }
}
