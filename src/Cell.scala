package blokus {

class Cell (val tuple: Tuple2[Int,Int]) {

  def this(x: Int, y: Int) =
    this(new Tuple2[Int,Int](x, y))

  def x = tuple._1
  def y = tuple._2

  def _1 = tuple._1
  def _2 = tuple._2

  def neighbors : List[Cell] =
    List(toTop, toBottom, toLeft, toRight)

  def toLeft: Cell   = Cell(x, y - 1)
  def toRight: Cell  = Cell(x, y + 1)
  def toTop: Cell    = Cell(x - 1, y)
  def toBottom: Cell = Cell(x + 1, y)

  def shift(upperLeftCell: Cell) = {
    Cell(upperLeftCell.x + x,
	 upperLeftCell.y + y)
  }

  override def equals(that: Any): Boolean = that match {
    case that: Cell => (this.tuple == that.tuple)
    case _ => false
  }

  override def toString = "C" + this.tuple.toString

}

/**
 * Allows for shorthand initialization.
 */
object Cell {
  def apply(t: Tuple2[Int,Int]) = new Cell(t)

  def list(tuples: Tuple2[Int,Int]*) =
    tuples.map(new Cell(_)).toList
}

}

