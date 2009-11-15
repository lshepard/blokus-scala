package blokus.test {

  import blokus.Game
  
  object GameTest {
  
    def run { 
      testSmallGame
    }

    /**
     * Runs a short game between two players,
     * each with only a few small pieces
     * on a 3x3 board.
     */ 
    def testSmallGame {

      val board = new Board(new Matrix("1.",
				       ".2"))

      val pieces = List(new Piece("++"),
			new Piece("+"),
			new Piece("++",
				  "+"))

      val player1 = new Player(1, pieces)
      val player2 = new Player(2, pieces)

      val game = new Game(board, Array(player1, player2))

      assert(game.gameOver)
    }
  }
}
