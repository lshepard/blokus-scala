package blokus {

/**
 * A piece is just a specific matrix of 0s and 1s.
 */
class Piece (m: Matrix) {

  def this(rows: String *) =
    this(new Matrix(rows.toArray.
		    map((x:String) => x.replace(" ", "0").replace("+", "1"))))

  def combos: Array[Piece] = {
    val flipped = m.flipVertical
    Set(m, 
	m.rotateRight,
	m.rotateLeft,
	m.spin,
	flipped,
	flipped.rotateRight,
	flipped.rotateLeft,
	flipped.spin).map(new Piece(_)).toArray
  }

  override def toString: String =
    m.m.map(_.map(_ match {
      case 0 => " "
      case 1 => "+"
      case _ => ""
    }).mkString("")).mkString("\n") 

}

object Pieces {
  
  def allCombos = all.map(_.combos.toList).reduceLeft(_ ::: _).toArray

  def all = Array(
    new Piece("+"),
    new Piece("++"),

    // three squares
    new Piece("+++"),

    new Piece("++",
	      "+ "),

    // four squares
    new Piece("++++"),

    new Piece("+++",
	      "+  "),

    new Piece("+++",
	      " + "),

    new Piece("++",
	      "++"),

    new Piece("++ ",
	      " ++"),
    
    // five squares
	      
    new Piece("+++++"),

    new Piece("++++",
	      "+   "),
    
    new Piece("++  ",
	      " +++"),
    
    new Piece("+++",
	      "++ "),

    new Piece("+++",
	      "+ +"),
    
    new Piece("++++",
	      " +  "),

    new Piece("+++",
	      " + ",
	      " + "),
    
    new Piece("+++",
	      "+  ",
	      "+  "),

    new Piece("++ ",
	      " ++",
	      "  +"),
    
    new Piece("+  ",
	      "+++",
	      "  +"),
    
    new Piece("+  ",
	      "+++",
	      " + "),
    
    new Piece(" + ",
	      "+++",
	      " + ")
    )

}

} // close package
