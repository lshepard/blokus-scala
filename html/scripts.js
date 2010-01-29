function Blokus(height, width, player_count) { 
  var initial_request = {
    mode: 'init',
    height: height,
    width: width,
    players: player_count
  };

  jQuery.post('server.php',
	      $.toJSON(initial_request),
	      this.updateState,
	      'json');
}

/**
 * Set up the game for the first time.
 */
Blokus.initializeForm = function() {
  var html = '<div id="board">Loading...</div><div id="players"></div>';
  document.getElementById('game').innerHTML = html;

  // trigger the setup
  window.blokus = new Blokus(20, 20, 4);
}

/**
 * Gets the next move in the place.
 */
Blokus.prototype.getNextMove = function() {

  // take the board and send it to the server
  var message = {
    move: 'move'
    board: this.board,
    players: this.players,
    current_player: 1
  };

  jQuery.post('server.php',
	      $.toJSON(initial_request),
	      this.updateState,
	      'json');
}

/**
 * Updates the game state in JavaScript based on a 
 * response from the server.
 *
 * Updates come with the following fields:
 *  - board
 *  - players (and their pieces)
 */
Blokus.prototype.updateState = function(state) {
  
  this.board = state.board;
  this.players = state.players;

  blokus.displayBoard();
  blokus.displayPlayers();
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

/**
 * @param board  a 2-dimensional array of colors
 */
Blokus.prototype.displayPlayers = function() {
  // display a series of <div>s

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
  document.getElementById('players').innerHTML = html;
}

var blokus = new Blokus(10, 10);
