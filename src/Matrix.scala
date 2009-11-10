package blokus {

class Matrix(var m: Array[Array[Int]]) {
  m = fillout(m)

  // flips it on its side
  def transpose: Matrix =
    translate(width, height,
	      (i: Int, j: Int) => (j, i))

  def flipHorizontal: Matrix = 
    translate(height, width,
	      (i: Int, j: Int) => (i, width - j - 1))

  def flipVertical: Matrix =
    translate(height, width,
	      (i: Int, j: Int) => (height - i - 1, j))

  def translate(h:Int, w:Int, f: (Int, Int) => (Int, Int)) = {
    var n = new Matrix(h, w);
    for (i <- 0 until height) {
      for (j <- 0 until width) {
	val point = f(i, j)
	n.m(point._1)(point._2) = m(i)(j) 
      }
    }
    n
  }

  def fillout(m: Array[Array[Int]]) = {
    val maxLength = m.map(_.length).reduceLeft(_.max(_))
    for(row <- m) yield {
      val tailList = 
	new Array(maxLength - row.length).
	map((x:Int) => 0).toList
      
      (row.toList ::: tailList).toArray
    }
  }

  // Accepts an array of strings,
  // where each character is a digit 0-9
  def this(rows : Array[String]) =
    this(
      rows.map(
	_.toCharArray.
	filter(_.isDigit).map(_.toInt - '0'.toInt)))

  // Creates an array full of filler
  def this(h: Int, w: Int) =
    this(new Array[Array[Int]](h).
	 map(_ => new Array[Int](w).
	     map(_ => 0)))

  def width = m(0).length
  def height = m.length

  // Use the fact that Lists do equality right to delegate 
  def toLists = m.map(_.toList).toList
  override def equals(that: Any): Boolean = that match {
      case that: Matrix => (this.toLists == that.toLists)
      case _ => false
  }

  override def toString: String =
    m.map(_.mkString(" ")).mkString("\n") + "\n"

}

} // close package
