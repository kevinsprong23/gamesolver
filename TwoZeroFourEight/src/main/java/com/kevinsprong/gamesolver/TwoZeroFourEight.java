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
    	GameState gameState = new GameState();
    	int[][] blankBoard = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
        gameState.setBoardState(blankBoard);
    	this.setGameState(gameState);
    	
    }
    
    public TwoZeroFourEight(String p1Strat, String p2Strat) {
    	this.setP1MoveStrat(p1Strat);
    	this.setP2MoveStrat(p2Strat);
    	// solver parameters
        this.setSearchPly(0);  
        this.setSearchTime(100);  // milliseconds
        this.setWinCondition(2048);
        
        // initialize game state
    	GameState gameState = new GameState();
    	int[][] blankBoard = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
        gameState.setBoardState(blankBoard);
    	this.setGameState(gameState);
    }
    
    public TwoZeroFourEight(String p1Strat, String p2Strat, int searchPly, int searchTime) {
    	this.setP1MoveStrat(p1Strat);
    	this.setP2MoveStrat(p2Strat);
    	// solver parameters
        this.setSearchPly(searchPly);  
        this.setSearchTime(searchTime);  // milliseconds
        this.setWinCondition(2048);
        
        // initialize game state
    	GameState gameState = new GameState();
    	int[][] blankBoard = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
        gameState.setBoardState(blankBoard);
    	this.setGameState(gameState);
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
    	GameState gameState = new GameState();
    	int[][] blankBoard = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
        gameState.setBoardState(blankBoard);
    	this.setGameState(gameState);
    }
    
    // implementation of abstract methods
    public void initializeBoard() {
    	
    	GameState gameState = this.getGameState();
    	
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
        int pt2x = (randomNum1 % 4);
        int pt2y = randomNum1 / 4;
        
        // assign to game state
        int[][] currentBoard = gameState.getBoardState();
        currentBoard[pt1y][pt1x] = pt1;
        currentBoard[pt2y][pt2x] = pt2;
        gameState.setBoardState(currentBoard);
        
        this.setGameState(gameState);
    }
    
    // given a game state and strategies, return next player move
    public void playerMove() {
    	// get needed variables
    	GameState gameState = this.getGameState();
    	String p1Strat = this.getP1MoveStrat();
    	String p2Strat = this.getP2MoveStrat();
    	
    	if (gameState.getPlayerToMove() == 1) { // dont toggle flag until we actually update the board in other func
    		if (p1Strat.equals("AlphaBeta")) { // use AlphaBetaSolver
    			gameState.setP1PreviousMove("U");
    		} else if (p1Strat.equals("Random")) {
    			// pick a random legal move
    			String[] legalMoves = this.findLegalMoves(gameState);
    			int idx = this.randIntInRange(0, legalMoves.length - 1);
    			
    			// instead of return statement
    			gameState.setP1PreviousMove(legalMoves[idx]);
    		}
    	} else {
    		if (p2Strat.equals("DefaultComputer")) { // choose a tile at random
    			// choose which numbers we will get
    	    	int pt1 = this.generateNewCell();
    	    	
    	    	// find zero tiles on current board and their indices
    	    	int[][] currentBoard = gameState.getBoardState();
    	    	List<int[]> zeroList = new ArrayList<int[]>();
    	    	for (int i = 0; i < 4; i++) {
    	    		for (int j = 0; j < 4; j++) {
    	    			if (currentBoard[i][j] == 0) {
    	    				zeroList.add(new int[]{i, j});
    	    			}
    	    		}
    	    	}
    	    	
    	    	// choose random location for tile from among zero indices
    	        int randomNum1 = this.randIntInRange(0, zeroList.size());
    	        int pt1y = zeroList.get(randomNum1)[0];
    	        int pt1x = zeroList.get(randomNum1)[1];
    	        
    	        // instead of return statement
    	        gameState.setP2PreviousMove(Integer.toString(pt1) + "_" + Integer.toString(pt1y) + "_" + Integer.toString(pt1x));          
    		}	
    	}
    	
    }
    
    // separate method to update game board of GameState manually
    public GameState calcUpdatedGameState(GameState gameState, String move) {
    	
    	int[][] currentBoard = gameState.getBoardState();
    	int playerMoved = gameState.getPlayerToMove();

    	if (playerMoved == 1) {
    		
    		// create new board to handle collapsing
    		int [][] newBoard = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
    		int numZerosInPath = 0; // will need this when collapsing moves
    		int[] row = {0,0,0,0}; // only if L or R
    		
    		// update depending on move
    		if (move.equals("U")) {
    			// detect and collapse
    			for (int j = 0; j < 4; j++) {
    	    		for (int i = 0; i < 3; i++) {
    	    			if (currentBoard[i][j] == currentBoard[i+1][j]) {
    	    				// assign 
    	    				currentBoard[i][j] = currentBoard[i][j]*2;
    	    				currentBoard[i+1][j] = 0;
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
    	    			if (currentBoard[i][j] == currentBoard[i-1][j]) {
    	    				// assign 
    	    				currentBoard[i][j] = currentBoard[i][j]*2;
    	    				currentBoard[i-1][j] = 0;
    	    			} 
    	    		}
    	    	}
    			// move everything down into blank spots
    			for (int j = 0; j < 4; j++) { // each element will move down by the number of zeros above it
    				numZerosInPath = 0;
    	    		for (int i = 3; i >= 0 ; i++) {
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
    				row = currentBoard[i];
    	    		for (int j = 0; j < 4; j++) {
    	    			if (row[j] == row[j+1]) {
    	    				// assign 
    	    				row[j] = row[j]*2;
    	    				row[j+1] = 0;
    	    			} 
    	    		}
    	    		newBoard[i] = row;
    	    	}
    			// move everything left into blank spots
    			for (int i = 0; i < 4; i++) {
    				numZerosInPath = 0;
    				row = newBoard[0];
    	    		for (int j = 0; j < 4; j++) {
    	    			if (row[j] == 0) {
    	    				numZerosInPath++;
    	    			} else {
    	    				row[j-numZerosInPath] = row[j];
    	    			}
    	    		}
    	    		newBoard[i] = row;
    	    	}
    		} else if (move.equals("R")) {
    			// detect and collapse
    			for (int i = 0; i < 4; i++) {
    				row = currentBoard[i];
    	    		for (int j = 0; j < 4; j++) {
    	    			if (row[j] == row[j-1]) {
    	    				// assign 
    	    				row[j] = row[j]*2;
    	    				row[j-1] = 0;
    	    			} 
    	    		}
    	    		newBoard[i] = row;
    	    	}
    			// move everything right into blank spots
    			for (int i = 0; i < 4; i++) {
    				numZerosInPath = 0;
    				row = newBoard[0];
    	    		for (int j = 3; j >= 0; j--) {
    	    			if (row[j] == 0) {
    	    				numZerosInPath++;
    	    			} else {
    	    				row[j+numZerosInPath] = row[j];
    	    			}
    	    		}
    	    		newBoard[i] = row;
    	    	}
    		}
    		// assign new board back to game state
    		gameState.setBoardState(newBoard);	
    		
    	} else {
    		
    		// parse move
    		String[] parsedMove = move.split("_");
    		int pt1 = Integer.parseInt(parsedMove[0]);
    		int pt1y = Integer.parseInt(parsedMove[1]);
    		int pt1x = Integer.parseInt(parsedMove[2]);
    	
    		currentBoard[pt1y][pt1x] = pt1;
    		// assign it to the board
    		gameState.setBoardState(currentBoard);	
    		
    	}
    	
        return gameState;
    }
    
    // given a move history, calculate the score
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
    	
    	GameState newGameState = calcUpdatedGameState(currentState, currentMove);
    	// switch playerToMove - flip 1 to 2 and vice versa
    	newGameState.setPlayerToMove(((playerMoved + 1) % 2) + 1);
    	// add a move to the overall list
    	if (playerMoved == 1) {
    		newGameState.setMoveNum(newGameState.getMoveNum() + 1);
    	}
    	// calc the move and update the official game state
    	this.setGameState(newGameState);
    	
    	
    	
    }

    // given a move history, calculate the score
    public void updateGameScore() {
    	// implement this later
    }
    
    // given a gameState and whose turn it is, find list of legal moves
    public String[] findLegalMoves(GameState gameState) {
    	ArrayList<String> legalMoveList = new ArrayList<String>();
    	
    	// test each move individually
    	// test Up
    	GameState gameStateU = this.calcUpdatedGameState(gameState, "U");
    	if (checkBoardEquality(gameStateU, gameState)) {
    		legalMoveList.add("U");
    	}
    	// test Down
    	GameState gameStateD = this.calcUpdatedGameState(gameState, "D");
    	if (checkBoardEquality(gameStateD, gameState)) {
    		legalMoveList.add("D");
    	}
    	// test Left
    	GameState gameStateL = this.calcUpdatedGameState(gameState, "L");
    	if (checkBoardEquality(gameStateL, gameState)) {
    		legalMoveList.add("L");
    	}
    	// test Right
    	GameState gameStateR = this.calcUpdatedGameState(gameState, "R");
    	if (checkBoardEquality(gameStateR, gameState)) {
    		legalMoveList.add("R");
    	}
    	
    	// convert array list into array of strings
    	return legalMoveList.toArray(new String[legalMoveList.size()]);
    }
    
    // determine if win condition met - returns 0, 1, 2 for no winner yet/p1/p2
    public int determineWinner() {
    	GameState gameState = this.getGameState();
    	
    	// check loser first
    	if (gameState.getPlayerToMove() == 1 && this.findLegalMoves(gameState).length == 0) {
    		return 2;
    	}
    	
    	// find max tile on board
    	int[][] currentBoard = gameState.getBoardState();
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
    	
    	return 0;
    }
    
    // compute board evaluation
    public double evaluateGameState(GameState gameState) {
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
