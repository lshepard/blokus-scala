package blokus.test {

  import blokus.Piece

  object PieceTest {
    def run {
      assert(Pieces.all.length == 21)
      assert(Pieces.allCombos.length == 91)
    }
  }
}
