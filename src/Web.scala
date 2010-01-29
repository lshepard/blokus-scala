package blokus {

import scala.util.parsing.json

/**
 * Provides the interface to the web.
 *
 */
object Web {
  
  def main(args: Array[String]) = {
    // assume the first arg is the whole json structure

    val json = scala.util.parsing.json.JSON.parseFull(args(0))

    val result = json match {
      case Some(x) => {
	val obj = x.asInstanceOf[Map[String, Any]]

	val mode = obj("mode").asInstanceOf[String]

	if (mode == "init") {
	  val height = obj("height").asInstanceOf[Int]
	  val width = obj("width").asInstanceOf[Int]
	  val player_count = obj("player_count").asInstanceOf[Int]

	  val board = new Board(height, width);
	  
	  val pieces = Piece.all

	  val players =
	    for (i <- 1 to player_count) yield
	      new Player(i, pieces)

	  new Game(board, players.toList).toString
	}

	// do this for every move
	else if (mode == "move") {
	  val m = new Matrix(obj("board").asInstanceOf[List[List[Double]]])
	  val board = new Board(m)
	  val player = new Player(obj("current_player").asInstanceOf[Int], Piece.all)
	  val move = player.nextMove(board)
	  board.makeMove(move).matrix.toString
	}
      }
      case None => "invalid string: " + args(0)
    }

    println(result)
  }
}

}
