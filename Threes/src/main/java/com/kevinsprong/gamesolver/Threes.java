package com.kevinsprong.gamesolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Extension of TwoPlayerGame to play the game Threes!
 */
public class Threes extends TwoPlayerGame {
	// expose weights for tuning
	private double[] heuristicWeights = {500, 2, 5, 0};
	
	// getter and setter
	public double[] getHeuristicWeights() {
		return this.heuristicWeights;
	}
	public void setHeuristicWeights(double[] heuristicWeightsIn) {
		this.heuristicWeights = heuristicWeightsIn;
	}
	
    
	// constructors
    public Threes() {
    	this.setP1MoveStrat("AlphaBeta");
    	this.setP2MoveStrat("DefaultComputer");
    	// solver parameters
    	this.setSearchPly(6);  
        this.setSearchTime(10000);  // milliseconds
        this.setWinCondition(6144);
        
        // initialize game state
    	GameState newGameState = new GameState();
    	int[][] blankBoard = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
        newGameState.setBoardState(blankBoard);
    	this.setGameState(newGameState);
 	
    }
    
    public Threes(String p1Strat, String p2Strat) {
    	this.setP1MoveStrat(p1Strat);
    	this.setP2MoveStrat(p2Strat);
    	// solver parameters
        this.setSearchPly(6);  
        this.setSearchTime(10000);  // milliseconds
        this.setWinCondition(6144);
        
        // initialize game state
    	GameState newGameState = new GameState();
    	int[][] blankBoard = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
        newGameState.setBoardState(blankBoard);
    	this.setGameState(newGameState);
    	
    }
    
    public Threes(String p1Strat, String p2Strat, int searchPly, int searchTime) {
    	this.setP1MoveStrat(p1Strat);
    	this.setP2MoveStrat(p2Strat);
    	// solver parameters
        this.setSearchPly(searchPly);  
        this.setSearchTime(searchTime);  // milliseconds
        this.setWinCondition(6144);
        
        // initialize game state
    	GameState newGameState = new GameState();
    	int[][] blankBoard = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
        newGameState.setBoardState(blankBoard);
    	this.setGameState(newGameState);
    }
    
    public Threes(String p1Strat, String p2Strat, 
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
    	
    	// a random set of ones, twos, threes
    	int[] rows = new int[9];
    	int[] cols = new int[9];
    	int[] vals = new int[9];
    	
    	// draw 9 values at random
    	int[] curMoveStack = currentState.getMoveStack();
    	for (int i = 0; i < 9; i++) {
    		vals[i] = curMoveStack[i];
    		curMoveStack[i] = 0;
    	}
    	
    	// generate random nums from 0 to 15 in loop, check against hash set, and make x/y
    	Set<Integer> randsChosen = new HashSet<Integer>();
    	int numsChosen = 0;
    	while (numsChosen < 9) { // could do fewer checks for blank spaces and have fewer, but then indexing gets hard
    		int randInt = this.randIntInRange(0, 15);
    		while (randsChosen.contains(randInt)) {
    			randInt = this.randIntInRange(0, 15);
    		}
    		rows[numsChosen] = randInt % 4;
    		cols[numsChosen] = randInt / 4;
    	}
         
    	
    	// assign to game state
        int[][] currentBoard = currentState.getBoardState();
        for(int i = 0; i < 9; i++) {
        	currentBoard[rows[i]][cols[i]] = vals[i];
        }
        currentState.setBoardState(currentBoard);
        
        // set remaining move stack to one of each
    	currentState.setMoveStack(curMoveStack);
    	
    	// assign to game state
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
    			currentState.setP1PreviousMove(AlphaBetaSolver.solveBoard(this));
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
    			// update all of this with threes logic
    			
    			// choose which number(s) we will get
    	    	int pt1 = this.generateNewCell();
    	    	
    	    	// find zero tiles on current board and their indices
    	    	ArrayList<int[]> zeroList = this.getValidComputerMoveTiles(currentState);
    	    	
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
    		
    		// update depending on move
    		if (move.equals("U")) {
    			// move everything up one tile, collide at top.  one collision per move
    			for (int j = 0; j < 4; j++) {
    				// find and perform collision
    				for (int i = 1; i < 4; i++) {
    					if ((i == 1 || currentBoard[i-2][j] != 0) 
    							&& currentBoard[i][j] > 0) {
    						if (currentBoard[i][j] == currentBoard[i-1][j]) {
    							currentBoard[i-1][j] = 2*currentBoard[i-1][j];
    							currentBoard[i][j] = 0;
    							break;
    						}
    						// because board(i,j) > 0 this detects a 1 and 2
    						if (currentBoard[i][j] + currentBoard[i-1][j] == 3) {
    							currentBoard[i-1][j] = 3;
    							currentBoard[i][j] = 0;
    							break;
    						}
    					}
    				}
    				// move everything up if there is a zero above
    				for (int i = 1; i < 4; i++) {
    					if (currentBoard[i-1][j] == 0) {
    						currentBoard[i-1][j] = currentBoard[i][j];
    						currentBoard[i][j] = 0;
    					}
    				}
    			}
    		} else if (move.equals("D")) {
    			// move everything down one tile, collide at bottom.  one collision per move
    			for (int j = 0; j < 4; j++) {
    				// find and perform collision
    				for (int i = 2; i >= 0; i--) {
    					if ((i == 2 || currentBoard[i+2][j] != 0) 
    							&& currentBoard[i][j] > 0) {
    						if (currentBoard[i][j] == currentBoard[i+1][j]) {
    							currentBoard[i+1][j] = 2*currentBoard[i+1][j];
    							currentBoard[i][j] = 0;
    							break;
    						}
    						// because board(i,j) > 0 this detects a 1 and 2
    						if (currentBoard[i][j] + currentBoard[i+1][j] == 3) {
    							currentBoard[i+1][j] = 3;
    							currentBoard[i][j] = 0;
    							break;
    						}
    					}
    				}
    				// move everything down if there is a zero below
    				for (int i = 2; i >= 0; i--) {
    					if (currentBoard[i+1][j] == 0) {
    						currentBoard[i+1][j] = currentBoard[i][j];
    						currentBoard[i][j] = 0;
    					}
    				}
    			}
    		} else if (move.equals("L")) {
    			// move everything left one tile, collide at left.  one collision per move
    			for (int i = 0; i < 4; i++) {
    				// find and perform collision
    				for (int j = 1; j < 4; j++) {
    					if ((j == 1 || currentBoard[i][j-2] != 0) 
    							&& currentBoard[i][j] > 0) {
    						if (currentBoard[i][j] == currentBoard[i][j-1]) {
    							currentBoard[i][j-1] = 2*currentBoard[i][j-1];
    							currentBoard[i][j] = 0;
    							break;
    						}
    						// because board(i,j) > 0 this detects a 1 and 2
    						if (currentBoard[i][j] + currentBoard[i][j-1] == 3) {
    							currentBoard[i][j-1] = 3;
    							currentBoard[i][j] = 0;
    							break;
    						}
    					}
    				}
    				// move everything left if there is a zero adjacent
    				for (int j = 1; j < 4; j++) {
    					if (currentBoard[i][j-1] == 0) {
    						currentBoard[i][j-1] = currentBoard[i][j];
    						currentBoard[i][j] = 0;
    					}
    				}
    			}
    		} else if (move.equals("R")) {
    			// move everything right one tile, collide at right.  one collision per move
    			for (int i = 0; i < 4; i++) {
    				// find and perform collision
    				for (int j = 2; j >= 0; j--) {
    					if ((j == 2 || currentBoard[i][j+2] != 0) 
    							&& currentBoard[i][j] > 0) {
    						if (currentBoard[i][j] == currentBoard[i][j+1]) {
    							currentBoard[i][j+1] = 2*currentBoard[i][j+1];
    							currentBoard[i][j] = 0;
    							break;
    						}
    						// because board(i,j) > 0 this detects a 1 and 2
    						if (currentBoard[i][j] + currentBoard[i][j+1] == 3) {
    							currentBoard[i][j+1] = 3;
    							currentBoard[i][j] = 0;
    							break;
    						}
    					}
    				}
    				// move everything right if there is a zero adjacent
    				for (int j = 2; j >= 0; j--) {
    					if (currentBoard[i][j+1] == 0) {
    						currentBoard[i][j+1] = currentBoard[i][j];
    						currentBoard[i][j] = 0;
    					}
    				}
    			}
    		}
    			
    		// assign new board back to game state
    		currentState.setBoardState(currentBoard);	// unnec. due to obj pointer pass?
    		
    	} else {
    		
    		// parse move
    		String[] parsedMove = move.split("_");
    		int pt1 = Integer.parseInt(parsedMove[0]);
    		int pt1y = Integer.parseInt(parsedMove[1]);
    		int pt1x = Integer.parseInt(parsedMove[2]);
    	
    		currentBoard[pt1y][pt1x] = pt1;
    		// assign it to the board
    		currentState.setBoardState(currentBoard);	
    		
    		// update move stack to zero out the move element
        	int[] moveList = currentState.getMoveStack();
        	for (int j = 0; j < 12; j++) {
        		if (moveList[j] > 0) {
        			moveList[j] = 0;
        			break;
        		}
        		if (moveList[j] == 11) { // array is all zeros; reset move stack
        			currentState.setMoveStack(this.generateNewMoveStack());
        			break;
        		}
        	}
    		
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
    	// this call calculates the updated board and moveStack
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
    	// making a tile >= 3 gives 3 ^ the multiple of 3 it is
    	// ignore second argument, score here is solely derived from board
    	
    	int totalScore = 0;
    	int[][] currentBoard = currentState.getBoardState();
    	
    	for (int[] row : currentBoard) {
    		for (int val : row) {
    			if (val >= 3) {
    				totalScore += Math.pow(3, (this.logb(val/3, 2) + 1));
    			}
    		}
    	}
    	
    	
    	// modify currentState that we are pointing to
    	currentState.setGameScore(totalScore);
    }
    

	// given a gameState and whose turn it is, find list of legal moves
    public String[] findLegalMoves(GameState gameStateIn) {
    	
    	GameState currentState = GameState.copyGameState(gameStateIn);
    	
    	int[][] boardOrig = currentState.getBoardState();
    	ArrayList<String> legalMoveList = new ArrayList<String>();
    	
    	if (currentState.getPlayerToMove() == 1) {
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
    		
    	} else { // computer player
    		// get the valid move tiles
    		ArrayList<int[]> zeroList = this.getValidComputerMoveTiles(currentState);
    		
	    	// legal moves are the top of the moveStack or the bonusMoveList, to open tiles
	    	// opposite P1's move
	    	// parse moveStack
	    	List<Integer> uniqueMoves = new ArrayList<Integer>();
	    	int topMove = this.getGameState().getMoveStack()[1];
	    	uniqueMoves.add(topMove);
	    	for (int bonus : this.findBonusMoves()) {
	    		uniqueMoves.add(bonus);
	    	}
	    	
	    	Integer[] legalMoveTiles = uniqueMoves.toArray(
	    			new Integer[uniqueMoves.size()]);
	    	// build list of moves
	    	for (int tile : legalMoveTiles) { // yay auto-unboxing
	    		for (int[] pos : zeroList) {
	    			legalMoveList.add(Integer.toString(tile) + "_" + 
	    					Integer.toString(pos[0]) + "_" + 
	    					Integer.toString(pos[1]));
	    		}
	    	}
    	}

    	// convert array list into array of strings
    	return legalMoveList.toArray(new String[legalMoveList.size()]);
    }
    
    private ArrayList<int[]> getValidComputerMoveTiles(GameState gsIn) {
    	ArrayList<int[]> zeroList = new ArrayList<int[]>();
    	
    	// get indices of open spaces along row/col opposite move
    	int[][] boardOrig = gsIn.getBoardState();
    	
    	String p1Move = gsIn.getP1PreviousMove();
    	if (p1Move.equals("U")) {
    		for (int i = 0; i < 4; i++) {
    			if (boardOrig[3][i] > 0) {
    				zeroList.add(new int[]{3,i});
    			}
    		}
    	} else if (p1Move.equals("D")) {
    		for (int i = 0; i < 4; i++) {
    			if (boardOrig[0][i] > 0) {
    				zeroList.add(new int[]{0,i});
    			}
    		}
    	} else if (p1Move.equals("L")) {
    		for (int i = 0; i < 4; i++) {
    			if (boardOrig[i][3] > 0) {
    				zeroList.add(new int[]{i,3});
    			}
    		}
    	} else if (p1Move.equals("R")) {
    		for (int i = 0; i < 4; i++) {
    			if (boardOrig[i][0] > 0) {
    				zeroList.add(new int[]{i,0});
    			}
    		}
    	}
    	return zeroList;
    	
	}
	// determine if win condition met - returns 0, 1, 2 for no winner yet/p1/p2
    public int determineWinner() {
    	GameState currentState = GameState.copyGameState(this.getGameState());

    	return this.determineWinner(currentState);
    }
    // with gs argument for alpha beta calls
    public int determineWinner(GameState currentState) {
    	// check loser first
    	if (currentState.getPlayerToMove() == 1 && 
    			this.findLegalMoves(currentState).length == 0) {
    		// can do this first since getting to p1's win condition 
    		// requires a successful move which leaves at least one empty space 
    		// (conditions don't overlap)
    		return 2;
    	}

    	// find max tile on board
    	int maxTile = getMaxTile(currentState);

    	if (maxTile >= this.getWinCondition()) {
    		return 1;
    	}

    	// if neither then p1 is still alive
    	return 0;
    }
    
    // compute board evaluation
    public double evaluateGameState(GameState gameStateIn) {
    	// update all of this with threes stuff
    	
    	int[][] board = gameStateIn.getBoardState();
    	
    	// heuristics to assess board.  SCORE IS RELATIVE TO HUMAN PLAYER
    	double[] heuristicVals = {0, 0, 0, 0};
    	double[] heuristicWeights = this.getHeuristicWeights();
    	double finalScore = 0;
    	
    	//---------------------------------------------------------------------
    	// Heuristic 1:  there is a win condition
    	int winStatus = this.determineWinner(gameStateIn);
    	if (winStatus == 1) {
    		heuristicVals[0] = 1;
    	} else if (winStatus == 2) {
    		heuristicVals[0] = -1;
    	} else {
    		heuristicVals[0] = 0;
    	}
    	
    	//---------------------------------------------------------------------
    	// Heuristic 2:  monotonicity
    	int[] monoScores;
    	int totalScore = 0;
    	// loop over rows
    	for (int[] row : board) {
    		monoScores = checkMonotonicity(row);
    		totalScore += Math.max(monoScores[0], monoScores[1]);
    	}
    	// loop over cols
    	int[] col = {0,0,0,0};
    	for (int j = 0; j < 4; j++) {
    		for (int i = 0; i < 4; i++) {
    			col[i] = board[i][j];
    		}
    		monoScores = checkMonotonicity(col);
    		totalScore += Math.max(monoScores[0], monoScores[1]);
    	}

    	heuristicVals[1] = (double) totalScore;


    	//---------------------------------------------------------------------
    	// Heuristic 3:  the "smoothness" of the board
    	// in log space to account for merges needed
    	double totalDeviation = 0;  // will be negative to penalize deviation
    	// probe rightwards 
    	for (int i = 0; i < 4; i++) {
    		for (int j = 0; j < 3; j++) {
    			if (board[i][j] != 0) {
    				// probe rightwards 
    				for (int k = j+1; k < 4; k++) { 
	    				// check smoothness versus first non-zero tile
	    				if (board[i][k] != 0) {
	    					totalDeviation += Math.abs(logb(board[i][j], 2) - 
	    							logb(board[i][k], 2));
	    					break;
	    				}
	    		    }   				
    			}	
    		}
    	}   
    	// probe downwards
    	for (int j = 0; j < 4; j++) {
    		for (int i = 0; i < 3; i++) {
    			if (board[i][j] != 0) {
    				// probe rightwards 
    				for (int k = i+1; k < 4; k++) { 
    					// check smoothness versus first non-zero tile
    					if (board[k][j] != 0) {
    						totalDeviation += Math.abs(logb(board[i][j], 2) - 
    								logb(board[k][j], 2));
    						break;
    					}
    				}   				
    			}	
    		}
    	}   	
    	heuristicVals[2] = -1 * totalDeviation;


    	//---------------------------------------------------------------------
    	// Heuristic 4:  the number of open tiles

    	double openTiles = 0;
    	for (int i = 0; i < 4; i++) {
    		for (int j = 0; j < 4; j++) {
    			if (board[i][j] == 0) {
    				openTiles += 1;
    			}
    		}
    	}
    	heuristicVals[3] = Math.pow(openTiles, 2);
    	
    	
    	//---------------------------------------------------------------------
    	// aggregate and return
    	for (int i = 0; i < 4; i++) {
    		finalScore += heuristicVals[i] * heuristicWeights[i];
    	}
    	return finalScore;
    }
    
    // check monotonicity of a vector in log space ignoring zeros
    private int[] checkMonotonicity(int[] vec) {
    	int[] scores = {0,0}; // forward and reverse dir respectively
		for (int i = 0; i < vec.length-1; i++) {
			if (vec[i] > 0) {
				// find next non-zero element
				for (int j = i+1; j < vec.length; j++) {
					if (vec[j] > 0) {
						if (vec[i] >= vec[j]) { // reverse dir
							scores[1] += logb(vec[i], 2) - logb(vec[j], 2);
						}
						if (vec[j] >= vec[i]) { // forward dir
							scores[1] += logb(vec[j], 2) - logb(vec[i], 2);
						}
						break;
					}
				}
			}
		}  	
		return scores;
	}
    
    
    
	// generate new tile for the game
    public int generateNewCell() {
    	int rand = this.randIntInRange(1,21);
    	int output = 0;
    	// 1/21 to return a bonus tile
    	if (rand == 1) {
    		int[] bonusMoves = this.findBonusMoves();
    		int bidx = randIntInRange(0, bonusMoves.length-1);
    		output = bonusMoves[bidx];
    	} else { // 20/21 to return first non-zero tile in moveStack
    		// find first non-zero tile of moveStack
    		int [] moveStack = this.getGameState().getMoveStack();
    		for (int i = 0; i < moveStack.length; i++) {
    			if (moveStack[i] > 0) {
    				output = moveStack[i];
    				break;
    			}
    		}
    	}
    	return output;
    }
    
    public int[] findBonusMoves() {
    	int bonusTiles[] = {};
    	
    	// find max tile
    	int maxTile = this.getMaxTile(this.getGameState());
    	if (maxTile >= 48) {
    		// return all multiples of 6 that are between 6 and maxTile/8
    		int maxTileMult = maxTile / 8 / 6;
    		bonusTiles = new int[maxTileMult];
    		for (int i = 1; i < maxTileMult; i++) {
    			bonusTiles[i-1] = 3 * (int) Math.pow(2, i);
    		}
    		
    	}
    	
    	return bonusTiles;
    }
    
    public int[] generateNewMoveStack() {
    	// generate 12 vector of four 1's, four 2's, four 3's.
    	int[] newMoveStack = new int[12];
    	double[] doubleStack = new double[12];
    	double[] doubleStackCopy = new double[12];
    	int[] doubleStackIndex = new int[12];
    	double thisDouble;
    	
    	// create array of Random numbers and a sorted copy
    	for (int i = 0; i < 12; i++) {
    		thisDouble = Math.random();
    		doubleStack[i] = thisDouble;
    		doubleStackCopy[i] = thisDouble;	
    	}
    	Arrays.sort(doubleStackCopy);
    	// find each number in sorted array
    	for (int i = 0; i < 12; i++) {
    		for (int j = 0; j < 12; j++) {
    			if (doubleStack[i] == doubleStackCopy[j]) {
    				doubleStackIndex[i] = j;
    				break;
    			}
    		}
    	}
    	
    	for (int i = 0; i < 12; i++) {
    		newMoveStack[i] = doubleStackIndex[i] / 3 + 1; // integer division exploit to round the num down
    	}
    	
    	return newMoveStack;
    }
    
    
    // get max tile of a game state
    public int getMaxTile(GameState gameStateIn) {
    	int [][] currentBoard = gameStateIn.getBoardState();
    	int maxTile = 0;
    	for (int i = 0; i < 4; i++) {
    		for (int j = 0; j < 4; j++) {
    			if (currentBoard[i][j] > maxTile) {
    				maxTile = currentBoard[i][j];
    			}
    		}
    	}
    	return maxTile;
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
    
    // vector multiply
    public int vectormult(int[] a, int[] b) {
    	int total = 0;
    	
    	for (int i = 0; i < a.length; i++) {
    		total += a[i] * b[i];
    	}
    	
    	return total;
    }
    
    // return a log at a given base
    public double logb(double a, double b) {
    	return Math.log(a) / Math.log(b);
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
