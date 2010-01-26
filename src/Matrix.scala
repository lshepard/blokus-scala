package blokus {

/**
 * Matrix represents a grid of numbers. This versatile class is the base for
 * both the game board, individual pieces, and moves in the game. It
 * handles all rotation, transposition, etc.
 * 
 */
class Matrix(var m: Array[Array[Int]]) {
  m = fillout

  def this(l: List[List[Double]]) =
    this(l.map(_.map(_.toInt).toArray).toArray)

  def width = m(0).length
  def height = m.length

  /**
   * Takes a new, smaller matrix and adds it into the existing
   * one at the specified point. This is intended to apply
   * a blokus move to a particular location, but it turns out
   * to be useful in other ways as well.
   *
   * I'm sure there's an excellent way to write this using
   * recursion, but I'm a little too lazy for that at the moment.
   */
  def insert(minor: Matrix, x: Int, y: Int): Matrix = {
    var n = new Matrix(m)
    for (i <- 0 until minor.height) {
      for (j <- 0 until minor.width) {
	val replacement = minor.m(i)(j)
	  n.m(x + i)(y + j) = 
	    if (replacement > 0 ) 
	      replacement
	    else 
              m(x + i)(y + j)
      }
    }
    n
  }

  /**
   * Turns uneven input into a rectangular matrix.
   */
  def fillout = {
    val maxLength = m.map(_.length).reduceLeft(_.max(_))
    for(row <- m) yield {
      val tailList = 
	new Array(maxLength - row.length).
	map((x:Int) => 0).toList
      
      (row.toList ::: tailList).toArray
    }
  }

  def insertCells(cells: List[Cell], value: Int): Matrix = {
    var n = new Matrix(m)
    cells.foreach((cell) =>
      n.set(cell, value))
    n
  }

  /*
   * Add a number of padding cells around the edge of the matrix.
   */
  def pad(padding: Int) =
    translate(padding * 2 + height, padding * 2 + width,
	      (i: Int, j: Int) => {
		(i + padding, j + padding)
	      })

  /**
   * Returns the (x, y) coordinates of each
   * of the cells matching a color within this piece.
   * Allows callers to avoid looping through
   * the double-layer array to do work.
   */
  def cells: List[Cell] = {
    List.range(0, height).flatMap(
      (i) => List.range(0, width).map(
	Cell(i, _)))
  }

  def cellsWithValue(value: Int): List[Cell] = {
    cells.filter((p:Cell) => m(p.x)(p.y) == value)
  }

  def substitute(from: Int, to: Int) =
    new Matrix(m.map((row: Array[Int]) => row.map(
	(x:Int) => if (x == from) to else x)))

  /**
   * Moves elements around within the matrix.
   * 
   * Applies an (i, j) => (i, j) transformation to every
   * element in a matrix. This is the based used
   * by all the other functions to flip, rotate, etc.
   */
  def translate(h:Int, w:Int, f: (Int, Int) => (Int, Int)) = {
    var n = new Matrix(h, w)
    for (i <- 0 until height) {
      for (j <- 0 until width) {
	val point = f(i, j)
	n.m(point._1)(point._2) = m(i)(j)
      }
    }
    n
  }

  def transpose: Matrix = 
    translate(width, height, (i: Int, j: Int) => (j, i))

  def flipHorizontal: Matrix =
    translate(height, width, (i: Int, j: Int) => (i, width - j - 1))

  def flipVertical: Matrix =
    translate(height, width, (i: Int, j: Int) => (height - i - 1, j))

  def spin = flipVertical.flipHorizontal
  def rotateRight = transpose.flipVertical
  def rotateLeft = rotateRight.spin

  ////////////////////////////////////////
  // Constructors and overridden methods

  /**
   * Accepts the matrix in string format, for shorthand.
   * For example:
   * 
   *   new Matrix(Array("0000","0000"))
   *
   */
  def this(rows : Array[String]) =
    this(
      rows.map(_.
	toCharArray.
	map(_.toInt - '0'.toInt)))

  /**
   * Even shorter way of specifying the above.
   *
   *  new Matrix("0000","0000")
   *
   */
  def this(rows: String *) =
    this(rows.toArray)

 /**
  * Shorthand for new array of all zeros. For example:
  *
  *   new Matrix(2, 4)
  */
  def this(h: Int, w: Int) =
    this(new Array[Array[Int]](h).
	 map(_ => new Array[Int](w).
	     map(_ => 0)))
  
  def set(cell: Cell, value: Int): Boolean = 
    if (cell.x >= height ||
	cell.x < 0 ||
	cell.y >= width ||
	cell.y < 0)
      false
    else {
      m(cell.x)(cell.y) = value
      true
    }

  def value(cell: Cell, default: Int): Int = {
    if (cell.x >= height ||
	cell.x < 0 ||
	cell.y >= width ||
	cell.y < 0)
      default
    else
      m(cell.x)(cell.y)
  }

  // Use the fact that Lists do equality right to delegate 
  def toLists = m.map(_.toList).toList
  override def hashCode: Int = toLists.hashCode
  override def equals(that: Any): Boolean = that match {
      case that: Matrix => (this.toLists == that.toLists)
      case _ => false
  }


  override def toString: String =
    "[[" + m.map(_.mkString(", ")).mkString("],\n [") + "]]\n"

}

} // close package
