function Blokus(height, width) {
  var row = [];
  for (var i = 0; i < width; ++i) {
    row.push(0);
  }

  this.board = [];
  for (var i = 0; i < height; ++i) {
    this.board.push(row);
  }

  this.displayBoard();
}

/**
 * Gets the next move in the place.
 */
Blokus.prototype.getNextMove = function() {

  // take the board and send it to the server
  var message = {
    board: this.board
  };

  jQuery.getJSON('getNextMove.php', 
		 $.toJSON(message),
		 Blokus.moveResponse);
}

Blokus.moveResponse = function(response) {
  // response is just the board
  console.log("hello");
  blokus.board = response;
  blokus.displayBoard();
}

/**
 * @param board  a 2-dimensional array of colors
 */
Blokus.prototype.displayBoard = function() {
  var html = '<table>';
  
  for (var i = 0; i < this.board.length; ++i) {
    html = html + '<tr>';
    for (var j = 0; j < this.board[i].length; ++j) {
      var color = this.board[i][j];
      html = html + 
	'<td class="color_' + 
	color + '">'
	+ color + '&nbsp;'
	+'</td>';
    }
    html = html + '</tr>';
  }

  html = html + '</table>';

  // write the html
  document.getElementById('board').innerHTML = html;
}

var blokus = new Blokus(10, 10);
