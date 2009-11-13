package blokus.test {

  import blokus.Game
  
  object GameTest {
  
    def run { 
//      testSmallGame
      testFullGame
    }

    /**
     * Runs a short game between two players,
     * each with only a few small pieces
     * on a 3x3 board.
     */ 
    def testSmallGame {

      val board = new Board(3, 3)

      val pieces = Array(new Piece("++"),
			 new Piece("+"),
			 new Piece("++",
				   "+"))

      val player1 = new Player(1, pieces)
      val player2 = new Player(2, pieces)

      val game = new Game(board, Array(player1, player2))

      game.play
    }

    /**
     * This runs a regulation-size game.
     */
    def testFullGame {

      val board = new Board(20, 20)

      var players = Array(new Player(1, Piece.all),
			  new Player(2, Piece.all),
			  new Player(3, Piece.all),
			  new Player(4, Piece.all))

      val game = new Game(board, players)

      game.play
    }
  }
}
