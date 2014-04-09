package com.kevinsprong.gamesolver;

/**
 * Abstract class to define a game between two players on a 2D board
 */
public abstract class TwoPlayerGame {
    // class fields; access provided via get/set
	// fields needed to define gameplay
	private String p1MoveStrat;  // playerMove method needs to use this flag
    private String p2MoveStrat;
    private int[][] gameState;  // values are a mapping to game pieces
    private int playerToMove = 1;  // player 1 moves first by definition
    private String p1PreviousMove; 
    private String p2PreviousMove; 
    // fields needed for strategy and scoring
    private double gameEval;  // positive/negative infinity for player 1/2 win
    private double gameScore;  // score if game supports it
    private double moveNum = 0;
    private int gameWinner = 0;  // 1 or 2
    // solver parameters
    private int searchPly;  // mutually exclusive with searchTime; defer to
    private int searchTime;  // constructor to set one to <N>, one to infinity
    
    
    // constructor
    public TwoPlayerGame(String p1Strat, String p2Strat) {
    	this.p1MoveStrat = p1Strat;
    	this.p2MoveStrat = p2Strat;
    	// leave specific game implementations to set rest of fields
    }
    
    
    // abstract methods
    // given a game state, return gameEval
    public abstract int evaluateGameState(int[][] gameState);
    // given a game state, whose turn, and strats, return next game state
    public abstract int[][] playerMove(int[][] gameState, 
    		int playerToMove, String p1Strat, String p2Strat);
    // given a move history, calculate the score
    public abstract int updateGameScore(int[][] gameState, String move, 
    		int playerToMove);
    // given a gameState and whose turn it is, find list of legal moves
    public abstract String[] findLegalMoves(int[][] gameState, 
    		int playerToMove);
    // determine if win condition met - returns 0, 1, 2 for no winner yet/p1/p2
    public abstract int determineWinner(int[][] gameState, int playerToMove);
    
    
    // get/set methods here
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
    public int[][] getGameState() {
            return this.gameState;
    }
    public void setGameState(int[][] gameStateIn) {
            this.gameState= gameStateIn;
    }
    // get/set for playerToMove
    public int getPlayerToMove() {
            return this.playerToMove;
    }
    public void setPlayerToMove(int playerToMoveIn) {
            this.playerToMove= playerToMoveIn;
    }
    // get/set for p1PreviousMove
    public String getP1PreviousMove() {
            return this.p1PreviousMove;
    }
    public void setP1PreviousMove(String p1PreviousMoveIn) {
            this.p1PreviousMove= p1PreviousMoveIn;
    }
    // get/set for p2PreviousMove
    public String getP2PreviousMove() {
            return this.p2PreviousMove;
    }
    public void setP2PreviousMove(String p2PreviousMoveIn) {
            this.p2PreviousMove= p2PreviousMoveIn;
    }
    // get/set for gameEval
    public double getGameEval() {
            return this.gameEval;
    }
    public void setGameEval(double gameEvalIn) {
            this.gameEval= gameEvalIn;
    }
    // get/set for gameScore
    public double getGameScore() {
            return this.gameScore;
    }
    public void setGameScore(double gameScoreIn) {
            this.gameScore= gameScoreIn;
    }
    // get/set for moveNum
    public double getMoveNum() {
            return this.moveNum;
    }
    public void setMoveNum(double moveNumIn) {
            this.moveNum= moveNumIn;
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

}


