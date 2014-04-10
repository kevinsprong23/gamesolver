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
    public GameState initializeBoard(GameState gameState) {
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
        
        return gameState;
    }
    
    // given a game state and strategies, return next game state
    public String playerMove(GameState gameState, String p1Strat, String p2Strat) {
        String move = null;
    	if (gameState.getPlayerToMove() == 1) {
    		if (p2Strat.equals("AlphaBeta")) { // use AlphaBetaSolver
    			return "U";
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
    	        
    	        return Integer.toString(pt1) + "_" + Integer.toString(pt1y) + "_" + Integer.toString(pt1x);          
    		}	
    	}
    	
    	return move;
    }
    
    // separate method to update state manually
    public GameState updateGameState(GameState gameState, String move) {
    	int[][] currentBoard = gameState.getBoardState();
    	int playerMoved = gameState.getPlayerToMove();
    	if (playerMoved == 1) {
    		// create new board to handle collapsing
    		int [][] newBoard = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
    		int numZerosInPath = 0; // will need this when collapsing moves
    		int[] row = {0,0,0,0}; // only if L or R
    		
    		// update depending on move
    		switch (move) {
    		case "U":
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
    		case "D":	
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
    		case "L":
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
    		case "R":
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
    			// move everything left into blank spots
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
    	
    	// switch player 1 to 2 and vice versa
    	gameState.setPlayerToMove(((playerMoved + 1) % 2) + 1);
    	return gameState;
    }

    // given a move history, calculate the score
    public double updateGameScore(GameState gameState, String move) {
    	return 0; // implement this later
    }
    
    // given a gameState and whose turn it is, find list of legal moves
    public String[] findLegalMoves(GameState gameState) {
    	return new String[] {" "};
    }
    
    // determine if win condition met - returns 0, 1, 2 for no winner yet/p1/p2
    public int determineWinner(GameState gameState) {
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
    
    public int randIntInRange(int min, int max) {
    	// Usually this can be a field rather than a method variable
    	Random rand = new Random();

    	// nextInt is normally exclusive of the top value,
    	// so add 1 to make it inclusive
    	int randomNum = rand.nextInt((max - min) + 1) + min;

    	return randomNum;
    }

    // main function that runs the game
	public static void main( String[] args ) {
        System.out.println( "Hello World!" );
    }
   
}
