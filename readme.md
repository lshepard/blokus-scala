What is this?
=============

This is an as-yet-incomplete project to power the popular board game Blokus, 
written in Scala. It is not yet complete.

The goal of the project is to create Blokus-bots which can play against each
other.

Design
=============

Blokus is particularly well suited for a functional language. 


Each of the above classes will be immutable, in classic functional style. A 
new game state is created with each move. For instance:

    val g = new Game(new Board(), Array(Player(1), Player(2)))
    g.play()

The actual gmae play will be like this::

    def play {
      while (notOver) {
        players.foreach(
        board = board.applyMove(_.nextMove(board)))
      }
    }

Or something like that. Haven't written that entirely yet.

Classes
-------

The following classes will form the bulk of the system.

    class Matrix(m: Array[Array[Int]])

Matrix represents a grid of numbers. This versatile class is the base
for both the game board, individual pieces, and moves in the game.
It handles all transformations

    class Piece(m: Matrix)

An individual piece in the game is implemented as a matrix of 0s and 1s.
There are 21 distinct individual pieces. If you take into account all of
their possible rotations there are 91 different moves available.

    class Move(p: Piece, x: Int, y: Int)

A move consists of a given piece, applied to a location on the board.
The (x, y) is the top left location of the piece. This is true even if
the top left location is empty for a given piece.


    class Board(m: Matrix)

The board represents the state of the board at any given point. The board
contains a matrix of Ints such that 0 is an empty space, and the numbers
represent the different team moves.

    class Player(color: Int, pieces: Array[Piece])

A player in the game. Belongs to a color and has an array of Piece objects.
By default, the player begins with the default set of pieces.
 
    class Game(b: Board, players: Array[Player])

Finally, the game represents the entire game state at any given time. For
convenience, we may make the Game a mutable object.


