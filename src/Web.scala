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
	val m = new Matrix(obj("board").asInstanceOf[List[List[Double]]])
	val board = new Board(m)
	val player = new Player(1, Piece.all)
	val move = player.nextMove(board)
	board.makeMove(move).matrix.toString
      }
      case None => "invalid string: " + args(0)
    }

    println(result)
  }
}

}
