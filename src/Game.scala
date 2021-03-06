package blokus {

/**
 * A game is mutable, since it's whole point is to drive the whole thing and
 * maintain state throughout. Maybe if we want to later make it immutable,
 * then okay.
 */
class Game (
  var board: Board,
  val players: List[Player]) {

  /**
   * This plays out an entire game.
   * 
   * (Yes, I know this could also be recursive, but ...
   * I'm lazy)
   */
  def playPrint {
    while (!gameOver) {
      for (player <- players) {
	val move = player.nextMove(board)
	if (move != null)
	  board = board.makeMove(move)
	println("---------")
	println(move)
	println(board)
      }
    }
  }

  /**
   * Game is over when none of the players can make any more moves.
   */
  def gameOver = !players.exists(board.possibleMoves(_).length > 0)

  def toJSON = {
    "{ \"board\": " + board.toString + 
    ", \"players\": [" + players.mkString(", ") + "]}"
  }
}

}
