package com.kevinsprong.gamesolver;

/**
 * Abstract class to define a game between two players on a 2D board
 */
public abstract class TwoPlayerGame {
    // class fields; access provided via get/set
	// fields needed to define game play
	private String p1MoveStrat;  // playerMove method needs to use this flag
    private String p2MoveStrat;
    private GameState gameState;
    private int gameWinner = 0;  // 1 or 2
    
    // solver parameters
    private int searchPly;  // mutually exclusive with searchTime; defer to
    private int searchTime;  // constructor to set one to <N>, one to infinity
    private int winCondition;  // get to this number to "win"
    
    // constructors
    public TwoPlayerGame() {
    	this.p1MoveStrat = "AlphaBeta";
    	this.p2MoveStrat = "DefaultCOmputer";
    	// leave specific game implementations to set rest of fields
    }
    public TwoPlayerGame(String p1Strat, String p2Strat) {
    	this.p1MoveStrat = p1Strat;
    	this.p2MoveStrat = p2Strat;
    	// leave specific game implementations to set rest of fields
    }
    
    
    // abstract methods
    // initialize board (to avoid having to do it in constructor
    public abstract void initializeBoard();
    // given a game state, whose turn, and strategies, make next move
    public abstract void playerMove();
    // calculate the new board resulting from a move on a given game state
    public abstract GameState calcUpdatedGameState(GameState gsIn, String move);
    // update official board after move
    public abstract void updateGameState();
    // given a gameState and whose turn it is, find list of legal moves
    public abstract String[] findLegalMoves(GameState gsIn);
    // determine if win condition met - returns 0, 1, 2 for no winner yet/p1/p2
    public abstract int determineWinner();
    // compute board evaluation
    public abstract double evaluateGameState(GameState gsIn);
    
    
    // get/set for p1MoveStrat
 	public String getP1MoveStrat() {
 		return this.p1MoveStrat;
 	}
 	public void setP1MoveStrat(String p1MoveStratIn) {
 		this.p1MoveStrat= p1MoveStratIn;
 	}
 	// get/set for p2MoveStrat
 	public String getP2MoveStrat() {
 		return this.p2MoveStrat;
 	}
 	public void setP2MoveStrat(String p2MoveStratIn) {
 		this.p2MoveStrat= p2MoveStratIn;
 	}
 	// get/set for gameState
 	public GameState getGameState() {
 		return this.gameState;
 	}
 	public void setGameState(GameState gameStateIn) {
 		this.gameState= gameStateIn;
 	}
 	// get/set for gameWinner
 	public int getGameWinner() {
 		return this.gameWinner;
 	}
 	public void setGameWinner(int gameWinnerIn) {
 		this.gameWinner= gameWinnerIn;
 	}
 	// get/set for searchPly
 	public int getSearchPly() {
 		return this.searchPly;
 	}
 	public void setSearchPly(int searchPlyIn) {
 		this.searchPly= searchPlyIn;
 	}
 	// get/set for searchTime
 	public int getSearchTime() {
 		return this.searchTime;
 	}
 	public void setSearchTime(int searchTimeIn) {
 		this.searchTime= searchTimeIn;
 	}
    // get/set for win condition
  	public int getWinCondition() {
  		return this.winCondition;
  	}
  	public void setWinCondition(int winConditionIn) {
  		this.winCondition= winConditionIn;
  	}
}


