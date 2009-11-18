package blokus.test {

  import blokus.Board
  
  object BoardTest {
  
    def run { 
      testAdjacency
      testFirstMove
      testCornerCheck
      testMoves
      testPossibleMoves 
      testMultiplePlayers
      testComplexBoard
    }
    def testAdjacency {
      val board = new Board(new Matrix("000",
				       "100",
				       "000"))


      assert( board.isAdjacentToSelf(List(Cell(0, 0)), 1))
      assert( board.isAdjacentToSelf(List(Cell(1, 1)), 1))
      assert(!board.isAdjacentToSelf(List(Cell(1, 2)), 1))
      
      // test multiples
      assert( board.isAdjacentToSelf(List(Cell(1, 1),
					  Cell(1, 2)), 1))

      assert(! board.isAdjacentToSelf(List(Cell(2, 1),
					   Cell(2, 2)), 1))

      val biggerBoard = new Board(new Matrix("0002222",
					     "0002002",
					     "0000000"))

      val move = new Move(new Player(2), new Piece("+",
						   "++",
						   " ++"), (0, 1))

      assert(biggerBoard.isAdjacentToSelf(move.cells, move.player.color))
    }

    def testCornerCheck = {
      
      val b = new Board(new Matrix("000",
				   "100",
				   "000"))
 
      assert(b.isCornerToSelf(new Move(new Player(1),
				       Piece.single,
				       (2,1))))
      assert(!b.isCornerToSelf(new Move(new Player(1),
				     Piece.single,
				     (2,2))))
    }

    def testFirstMove = {
      
      val b = new Board(new Matrix("000",
				   "100",
				   "000"))

      val player1upperLeftMove = new Move(new Player(1),
				   Piece.single,
				   (0, 0))

      val player1topMove = new Move(new Player(1),
			     Piece.single,
			     (0, 1))

      val player2upperLeftMove = new Move(new Player(2),
					  Piece.single,
					  (0, 0))

      // it is player 2's first move cause he hasn't gone yet
      assert(b.isFirstMove(player2upperLeftMove))
      assert(!b.isFirstMove(player1upperLeftMove))


      assert(b.isCornerMove(player2upperLeftMove))
      assert(b.isCornerMove(player1upperLeftMove))
      assert(!b.isCornerMove(player1topMove))

    }

    /**
     * Walk through a simple scenario.
     * We start with an empty 5x5 board,
     * then place two moves on the board.
     * Check to make sure the board looks
     * right at each point, and that the
     * legality of the move is good.
     */
    def testMoves = {
      // start out empty
      val b1 = new Board(5, 5)
      assert(b1.matrix == new Matrix("00000",
				     "00000",
				     "00000",
				     "00000",
				     "00000"))

      // make first move
      val p1 = new Piece("+++")
      val player1 = new Player(1)
      val m1 = new Move(player1, p1, (0, 0))

      val b2 = b1.makeMove(m1)

      // it was legal beforehand, but after it's played,
      // you can't make that move anymore because a piece is there
      assert(b1.isLegalMove(m1))
      assert(b2.matrix == new Matrix("11100",
				     "00000",
				     "00000",
				     "00000",
				     "00000"))
      assert(!b2.isLegalMove(m1))
      // can't place below
      assert(!b2.isLegalMove(new Move(player1, p1, (1, 0))))
      // or offset
      assert(!b2.isLegalMove(new Move(player1, p1, (1, 2))))

      // make another move on the board, and verify that the resulting
      // board looks like we want it to look
      val p2 = new Piece("+ +",
			 " + ",
			 "+++")
      val player2 = new Player(2)
      val b3 = b2.makeMove(new Move(player2, p2, (2, 2)))

      assert(b3.matrix == new Matrix("11100",
				     "00000",
				     "00202",
				     "00020",
				     "00222"))
    }

    /**
     * Calculating the possible legal moves is currently not working right
     * Still figuring out why.
     */
    def testPossibleMoves = {

      // If we only have a single player with a one-piece,
      // then it should only have 4 possible moves
      val one_piece_player = new Player(1, Set(Piece.single))
      val b = new Board(2, 2)
      
      assert(b.possibleMoves(one_piece_player).length == 4)

      // now, i'm adding a 4-block piece. in this arrangement,
      // there should be 5 moves - the original 4, plus the
      // only one possible with the new one
      val two_piece_player = new Player(2, Set(Piece.single,
						 new Piece("++",
							   "++")))

      assert(b.possibleMoves(two_piece_player).length == 5)

      val b2 = b.makeMove(new Move(one_piece_player,
				   Piece.single,
				   (0, 0)))
      assert(b2.matrix == new Matrix("10",
				     "00"))

      // now that we're in the top left corner, there are only
      // three possible moves with this single piece

      assert(b2.isAdjacentToSelf(List(Cell(0,1)), 1))
      assert(b2.possibleMoves(one_piece_player).length == 1)
    }

    /**
     * Start off, player 1 has already moved top corner.
     * Make sure that player2 can't also move there.
     */
    def testMultiplePlayers = {
      val player1 = new Player(1, Set(Piece.single))
      val player2 = new Player(2, Set(Piece.single))
      val board = new Board(new Matrix("10","00"))

      val m_bad = new Move(player2, Piece.single, (0,0))

      assert(!board.isLegalMove(m_bad))
    }

    def testComplexBoard = {
      val board = new Board(new Matrix("2220001",
				       "2000111",
				       "0000000"))

      val player1 = new Player(1, Set(new Piece("++")))
      val player2 = new Player(1, Set(new Piece("++",
						"+")))

      // the move is bad because it doesn't satisfy corner adjacency
      val m_bad = new Move(player1, new Piece("++"), (1,1))
      assert(!board.isLegalMove(m_bad))
    }
  }
}
