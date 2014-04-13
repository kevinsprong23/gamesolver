package com.kevinsprong.gamesolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Extension of TwoPlayerGame to play the game 2048
 */
public class TwoZeroFourEight extends TwoPlayerGame {
    
	// constructors
    public TwoZeroFourEight() {
    	this.setP1MoveStrat("AlphaBeta");
    	this.setP2MoveStrat("DefaultComputer");
    	// solver parameters
        this.setSearchPly(0);  
        this.setSearchTime(100);  // milliseconds
        this.setWinCondition(2048);
        
        // initialize game state
    	GameState newGameState = new GameState();
    	int[][] blankBoard = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
        newGameState.setBoardState(blankBoard);
    	this.setGameState(newGameState);
 	
    }
    
    public TwoZeroFourEight(String p1Strat, String p2Strat) {
    	this.setP1MoveStrat(p1Strat);
    	this.setP2MoveStrat(p2Strat);
    	// solver parameters
        this.setSearchPly(0);  
        this.setSearchTime(100);  // milliseconds
        this.setWinCondition(2048);
        
        // initialize game state
    	GameState newGameState = new GameState();
    	int[][] blankBoard = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
        newGameState.setBoardState(blankBoard);
    	this.setGameState(newGameState);
    	
    }
    
    public TwoZeroFourEight(String p1Strat, String p2Strat, int searchPly, int searchTime) {
    	this.setP1MoveStrat(p1Strat);
    	this.setP2MoveStrat(p2Strat);
    	// solver parameters
        this.setSearchPly(searchPly);  
        this.setSearchTime(searchTime);  // milliseconds
        this.setWinCondition(2048);
        
        // initialize game state
    	GameState newGameState = new GameState();
    	int[][] blankBoard = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
        newGameState.setBoardState(blankBoard);
    	this.setGameState(newGameState);
    }
    
    public TwoZeroFourEight(String p1Strat, String p2Strat, 
    		int searchPly, int searchTime, int winCondition) {
    	this.setP1MoveStrat(p1Strat);
    	this.setP2MoveStrat(p2Strat);
    	// solver parameters
        this.setSearchPly(searchPly);  
        this.setSearchTime(searchTime);  // milliseconds
        this.setWinCondition(winCondition); // get to this number to win!
        
        // initialize game state
    	GameState newGameState = new GameState();
    	int[][] blankBoard = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
        newGameState.setBoardState(blankBoard);
    	this.setGameState(newGameState);
    }
    
    // implementation of abstract methods
    public void initializeBoard() {
    	
    	GameState currentState = this.getGameState();
    	
    	// choose which numbers we will get
    	int pt1 = this.generateNewCell();
    	int pt2 = this.generateNewCell();
    	// choose two random locations for tiles
        int randomNum1 = this.randIntInRange(0, 15);
        int randomNum2 = this.randIntInRange(0, 15);
        while (randomNum2 == randomNum1) {
        	randomNum2 = this.randIntInRange(0, 15);
        }
        int pt1x = (randomNum1 % 4);
        int pt1y = randomNum1 / 4;
        int pt2x = (randomNum2 % 4);
        int pt2y = randomNum2 / 4;
        
        // assign to game state
        int[][] currentBoard = currentState.getBoardState();
        currentBoard[pt1y][pt1x] = pt1;
        currentBoard[pt2y][pt2x] = pt2;
        currentState.setBoardState(currentBoard);
        
        this.setGameState(currentState);
    }
    
    // given a game state and strategies, return next player move
    public void playerMove() {
    	// get needed variables
    	GameState currentState = this.getGameState();
    	String p1Strat = this.getP1MoveStrat();
    	String p2Strat = this.getP2MoveStrat();
    	
    	if (currentState.getPlayerToMove() == 1) { // dont toggle flag until we actually update the board in other func
    		if (p1Strat.equals("AlphaBeta")) { // use AlphaBetaSolver
    			currentState.setP1PreviousMove("L");
    		} else if (p1Strat.equals("Random")) {
    			// copy game state so we dont overwrite any of it
    			GameState currentStateCopy = GameState.copyGameState(currentState);
    			// pick a random legal move
    			String[] legalMoves = this.findLegalMoves(currentStateCopy);
    			int idx = this.randIntInRange(0, legalMoves.length - 1);
    			
    			// instead of return statement
    			currentState.setP1PreviousMove(legalMoves[idx]);
    		}
    	} else {
    		if (p2Strat.equals("DefaultComputer")) { // choose a tile at random
    			// choose which numbers we will get
    	    	int pt1 = this.generateNewCell();
    	    	
    	    	// find zero tiles on current board and their indices
    	    	int[][] currentBoard = currentState.getBoardState();
    	    	List<int[]> zeroList = new ArrayList<int[]>();
    	    	for (int i = 0; i < 4; i++) {
    	    		for (int j = 0; j < 4; j++) {
    	    			if (currentBoard[i][j] == 0) {
    	    				zeroList.add(new int[]{i, j});
    	    			}
    	    		}
    	    	}
    	    	
    	    	// choose random location for tile from among zero indices
    	        int randomNum1 = this.randIntInRange(0, zeroList.size()-1);
    	        int pt1y = zeroList.get(randomNum1)[0];
    	        int pt1x = zeroList.get(randomNum1)[1];
    	        
    	        // instead of return statement
    	        currentState.setP2PreviousMove(Integer.toString(pt1) + "_" + Integer.toString(pt1y) + "_" + Integer.toString(pt1x));          
    		}	
    	}
    	
    }
    
    // separate method to update game board of GameState manually
    public GameState calcUpdatedGameState(GameState gameStateIn, String move) {
    	
    	// give ourselves clean object to work with
    	GameState currentState = GameState.copyGameState(gameStateIn);
    	
    	int[][] currentBoard = currentState.getBoardState();
    	int playerMoved = currentState.getPlayerToMove();

    	if (playerMoved == 1) {
    		
    		// create new board to handle collapsing
    		int [][] newBoard = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
    		int numZerosInPath = 0; // will need this when collapsing moves
    		
    		// update depending on move
    		if (move.equals("U")) {
    			// detect and collapse
    			for (int j = 0; j < 4; j++) { // process each col independently
    	    		for (int i = 0; i < 3; i++) {  // current tile to check
    	    			for (int k = i+1; k < 4; k++) { // probe downwards 
    	    				// break if you know condition can't be met
    	    				if ((currentBoard[k][j] != currentBoard [i][j]) &&
    	    						(currentBoard[k][j] != 0)) {
    	    					break;
    	    				} 
    	    				// if there is a collision, assign it and break
    	    				if (currentBoard[i][j] == currentBoard[k][j]) {
    	    					// assign 
    	    					currentBoard[i][j] = currentBoard[i][j]*2;
    	    					currentBoard[k][j] = 0;
    	    					// update score
    	    					updateGameScore(currentState, currentBoard[i][j]);
    	    					break;
    	    				} 
    	    			}
    	    		}
    	    	}
    			// move everything up into blank spots
    			for (int j = 0; j < 4; j++) { // each element will move up by the number of zeros above it
    				numZerosInPath = 0;
    	    		for (int i = 0; i < 4; i++) {
    	    			if (currentBoard[i][j] == 0) {
    	    				numZerosInPath++;
    	    			} else {
    	    				newBoard[i-numZerosInPath][j] = currentBoard[i][j];
    	    			}
    	    		}
    	    	}
    		} else if (move.equals("D")) {
    			// detect and collapse
    			for (int j = 0; j < 4; j++) {
    	    		for (int i = 3; i > 0; i--) {
    	    			for (int k = i-1; k >=0; k--) { // probe upwards 
    	    				// break if you know condition can't be met
    	    				if ((currentBoard[k][j] != currentBoard [i][j]) &&
    	    						(currentBoard[k][j] != 0)) {
    	    					break;
    	    				} 
    	    				// if there is a collision, assign it and break
    	    				if (currentBoard[i][j] == currentBoard[k][j]) {
    	    					// assign 
    	    					currentBoard[i][j] = currentBoard[i][j]*2;
    	    					currentBoard[k][j] = 0;
    	    					// update score
    	    					updateGameScore(currentState, currentBoard[i][j]);
    	    					break;
    	    				} 
    	    			}
    	    		}
    	    	}
    			// move everything down into blank spots
    			for (int j = 0; j < 4; j++) { // each element will move down by the number of zeros below it
    				numZerosInPath = 0;
    	    		for (int i = 3; i >= 0 ; i--) {
    	    			if (currentBoard[i][j] == 0) {
    	    				numZerosInPath++;
    	    			} else {
    	    				newBoard[i+numZerosInPath][j] = currentBoard[i][j];
    	    			}
    	    		}
    	    	}
    		} else if (move.equals("L")) {
    			// detect and collapse
    			for (int i = 0; i < 4; i++) {
    	    		for (int j = 0; j < 3; j++) {
    	    			for (int k = j+1; k < 4; k++) { // probe rightwards 
    	    				// break if you know condition can't be met
    	    				if ((currentBoard[i][k] != currentBoard [i][j]) &&
    	    						(currentBoard[i][k] != 0)) {
    	    					break;
    	    				} 
    	    				// if there is a collision, assign it and break
    	    				if (currentBoard[i][j] == currentBoard[i][k]) {
    	    					// assign 
    	    					currentBoard[i][j] = currentBoard[i][j]*2;
    	    					currentBoard[i][k] = 0;
    	    					// update score
    	    					updateGameScore(currentState, currentBoard[i][j]);
    	    					break;
    	    				} 
    	    			}
    	    		}
    	    	}
    			// move everything up into blank spots
    			for (int i = 0; i < 4; i++) { 
    				numZerosInPath = 0;
    	    		for (int j = 0; j < 4; j++) {
    	    			if (currentBoard[i][j] == 0) {
    	    				numZerosInPath++;
    	    			} else {
    	    				newBoard[i][j-numZerosInPath] = currentBoard[i][j];
    	    			}
    	    		}
    	    	}
    		} else if (move.equals("R")) {
    			// detect and collapse
    			for (int i = 0; i < 4; i++) {
    	    		for (int j = 3; j > 0; j--) {
    	    			for (int k = j-1; k >=0; k--) { // probe leftwards
    	    				// break if you know condition can't be met
    	    				if ((currentBoard[i][k] != currentBoard [i][j]) &&
    	    						(currentBoard[i][k] != 0)) {
    	    					break;
    	    				} 
    	    				// if there is a collision, assign it and break
    	    				if (currentBoard[i][j] == currentBoard[i][k]) {
    	    					// assign 
    	    					currentBoard[i][j] = currentBoard[i][j]*2;
    	    					currentBoard[i][k] = 0;
    	    					// update score
    	    					updateGameScore(currentState, currentBoard[i][j]);
    	    					break;
    	    				} 
    	    			}
    	    		}
    	    	}
    			// move everything up into blank spots
    			for (int i = 0; i < 4; i++) { 
    				numZerosInPath = 0;
    	    		for (int j = 3; j >= 0; j--) {
    	    			if (currentBoard[i][j] == 0) {
    	    				numZerosInPath++;
    	    			} else {
    	    				newBoard[i][j+numZerosInPath] = currentBoard[i][j];
    	    			}
    	    		}
    	    	}
    		} 
    		// assign new board back to game state
    		currentState.setBoardState(newBoard);	
    		
    	} else {
    		
    		// parse move
    		String[] parsedMove = move.split("_");
    		int pt1 = Integer.parseInt(parsedMove[0]);
    		int pt1y = Integer.parseInt(parsedMove[1]);
    		int pt1x = Integer.parseInt(parsedMove[2]);
    	
    		currentBoard[pt1y][pt1x] = pt1;
    		// assign it to the board
    		currentState.setBoardState(currentBoard);	
    		
    	}
    	
        return currentState;
    }
    
    // wrapper function for calculating updated game state 
    public void updateGameState() {
    	// get game variables we need
    	GameState currentState = this.getGameState();
    	int playerMoved = currentState.getPlayerToMove();
    	String currentMove = null;
    	if (playerMoved == 1) {
    		currentMove = currentState.getP1PreviousMove();
    	} else {
    		currentMove = currentState.getP2PreviousMove();
    	}
    	// this call calculates the updated board
    	GameState newGameState = calcUpdatedGameState(currentState, currentMove);
    	// switch playerToMove - flip 1 to 2 and vice versa
    	newGameState.setPlayerToMove((playerMoved % 2) + 1);
    	// add a move to the overall list
    	if (playerMoved == 1) {
    		newGameState.setMoveNum(newGameState.getMoveNum() + 1);
    	}
    	// calculate the move and update the official game state
    	this.setGameState(newGameState);
	
    }

    // given a move history, calculate the score
    public void updateGameScore(GameState currentState, int tileMade) {
    	// formula:  making tile 2^n gives score (n-1)*2^n
        // extract n
    	double base = logb(tileMade, 2); // should be an int here anyways
        int n = (int) Math.round(base);
        // calc the score
        int tileScore = (int) ((n - 1) * Math.pow(2, n));
        
    	// modify currentState that we are pointing to
    	currentState.setGameScore(currentState.getGameScore() + tileScore);
    }
    
    private double logb(int a, int b) {
    	// return base b logarithm of a
		return Math.log(a)/Math.log(b);
	}

	// given a gameState and whose turn it is, find list of legal moves
    public String[] findLegalMoves(GameState gameStateIn) {
    	GameState currentState = GameState.copyGameState(gameStateIn);
    	
    	int[][] boardOrig = currentState.getBoardState();
    	ArrayList<String> legalMoveList = new ArrayList<String>();
    	
    	// test each move individually
    	// test Up
    	GameState gameStateU = this.calcUpdatedGameState(currentState, "U");
    	if (!checkBoardEquality(gameStateU, currentState)) { // then up was a legal move
    		legalMoveList.add("U");
    	}
    	// test Down
    	currentState.setBoardState(boardOrig);
    	GameState gameStateD = this.calcUpdatedGameState(currentState, "D");
    	if (!checkBoardEquality(gameStateD, currentState)) {
    		legalMoveList.add("D");
    	}
    	// test Left
    	currentState.setBoardState(boardOrig);
    	GameState gameStateL = this.calcUpdatedGameState(currentState, "L");
    	if (!checkBoardEquality(gameStateL, currentState)) {
    		legalMoveList.add("L");
    	}
    	// test Right
    	currentState.setBoardState(boardOrig);
    	GameState gameStateR = this.calcUpdatedGameState(currentState, "R");
    	if (!checkBoardEquality(gameStateR, currentState)) {
    		legalMoveList.add("R");
    	}
    	
    	// convert array list into array of strings
    	return legalMoveList.toArray(new String[legalMoveList.size()]);
    }
    
    // determine if win condition met - returns 0, 1, 2 for no winner yet/p1/p2
    public int determineWinner() {
    	GameState currentState = GameState.copyGameState(this.getGameState());

    	// check loser first
     	if (currentState.getPlayerToMove() == 1 && 
     			this.findLegalMoves(currentState).length == 0) {
     		// can do this first since getting to p1's win condition 
     		// requires a successful move which leaves at least one empty space 
     		// (conditions don't overlap)
    		return 2;
    	}
    	
    	// find max tile on board
    	int[][] currentBoard = currentState.getBoardState();
    	int maxTile = 0;
    	for (int i = 0; i < 4; i++) {
    		for (int j = 0; j < 4; j++) {
    			if (currentBoard[i][j] > maxTile) {
    				maxTile = currentBoard[i][j];
    			}
    		}
    	}
    	
    	if (maxTile >= this.getWinCondition()) {
    		return 1;
    	}
    	
    	// if neither then p1 is still alive
    	return 0;
    }
    
    // compute board evaluation
    public double evaluateGameState(GameState gameStateIn) {
    	return 0;
    }
    
    // generate new tile for the game
    public int generateNewCell() {
    	// 90% to generate a two
    	double randNum = Math.random();
    	if (randNum <= 0.9) {
    		return 2;
    	} else {
    		return 4;
    	}
    }
    
    // check whether two boards are identical
    public boolean checkBoardEquality(GameState gs1, GameState gs2) {
    	boolean boardEq = true; // default
    	int[][] board1 = gs1.getBoardState();
    	int[][] board2 = gs2.getBoardState();
    	
    	// loop and break on a differing value anywhere
    	int[] idx = {0, 1, 2, 3};
    	for (int i : idx) {
    		for (int j : idx) {
    			if (board1[i][j] != board2[i][j]) {
    				boardEq = false;
    				break;
    			}
    		}
    	}
    	
    	return boardEq;
    }
    
    // generate a random int in a range
    public int randIntInRange(int min, int max) {

    	Random rand = new Random();

    	// nextInt is normally exclusive of the top value,
    	// so add 1 to make it inclusive
    	int randomNum = rand.nextInt((max - min) + 1) + min;

    	return randomNum;
    }

}
