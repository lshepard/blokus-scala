What is this?
=============

This is an as-yet-incomplete project to power the popular board game Blokus, 
written in Scala.

The goal of the project is to create Blokus-bots which can play against each
other.

Running a game
==============

Game play is run through the Game class. You can trigger a standard sized
game with 4 dumb computer players by running:

     scala script/play.scala


Testing
=======

Unit tests are built in- perhaps not 100% code coverage but at least some
tests for all the major pieces of functionality. Feel free to add more.
To compile and test, run the following:

     ./scripts/test.sh

Design
=============

Blokus is particularly well suited for a functional language. All objects
are immutable - which means when you make a move, you create an entirely
new Board. This allows for more easily tested code as well as fits with
a more purely functional approach.

Classes
-------

These are the main classes in the system.

    class Piece(matrix: Matrix)

An individual piece in the game is implemented as a matrix of 0s and 1s.
There are 21 distinct individual pieces. If you take into account all of
their possible rotations there are 91 different moves available.

    class Move(player: Player, piece: Piece, point: Tuples[Int,Int])

A move consists of a given piece, applied to a location on the board.
The (x, y) is the top left location of the piece. This is true even if
the top left location is empty for a given piece.

    class Board(matrix: Matrix)

The board represents the state of the board at any given point. The board
contains a matrix of Ints such that 0 is an empty space, and the numbers
represent the different team moves. Board knows and enforces all the rules
of the game.

    class Player(color: Int, pieces: List[Piece])

A player in the game. Belongs to a color and has an array of Piece objects.
By default, the player begins with the default set of pieces. Eventually,
different computer algorithms can be written by subclassing from player,
as all the game logic lives elsewhere.
 
    class Game(board: Board, players: Array[Player])

The game represents the entire game state at any given time. For
convenience, the Game is a mutable object.

    class Matrix(m: Array[Array[Int]])

Finally, Matrix represents a grid of numbers. This versatile class is 
the base for both the game board, individual pieces, and moves in the 
game.


Future plans
============

Clearly, get the basic game working. After that, I think we need a
decent human interface, as well as a few competing Player objects
to get some algorithms into the mix.
