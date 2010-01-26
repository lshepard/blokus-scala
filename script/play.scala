/**
 * Runs a fully-automated Blokus game on a standard 20x20 board
 * with 4 players.
 *
 * For now they are all dumb computers, but we could easily
 * substitute a human player or different algorithms.
 */
import blokus._

println("Setup")
val board = new Board(20, 20)

val pieces = Piece.all
  
var players = List(new Player(1, pieces),
		   new Player(2, pieces),
		   new Player(3, pieces),
		   new Player(4, pieces))

val game = new Game(board, players)

println("Beginning game")
game.playPrint
