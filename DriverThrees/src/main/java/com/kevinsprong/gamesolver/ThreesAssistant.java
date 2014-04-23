package com.kevinsprong.gamesolver;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Class to help a person score high at threes
 */
public class ThreesAssistant {
    public static void main( String[] args ) {
    	int[] userStack = {}; // to reset an existing game state
    	
        // make a new game
    	Threes game = new Threes("AlphaBeta", "UserInput");
    	game.input = new Scanner(System.in);
    	game.setSearchPly(7);
    	
    	// manual init board
    	System.out.println("Enter the initial board as comma separated integers: ");
    	String boardStr = game.input.next();
    	String[] boardVals = boardStr.split(",");
    	int[][] userBoard = new int[4][4];
    	for (int i = 0; i < 16; i++) {
    		userBoard[i / 4][i % 4] = Integer.parseInt(boardVals[i]);
    	}
    	game.getGameState().setBoardState(userBoard);
    	
    	// now figure out game stack
    	int[] numTilesInStack = new int[3]; // ones, twos, threes respectively	
    	
    	if (userStack.length > 0) {
    		numTilesInStack = userStack;
    	} else { // set one up off the initialized board
    		numTilesInStack = new int[]{4,4,4};
    		for (int[] row : userBoard) {
    			for (int val: row) {
    				if (val == 1) {
    					numTilesInStack[0]--;
    				} else if (val == 2) {
    					numTilesInStack[1]--;
    				} else if (val == 3) {
    					numTilesInStack[2]--;			
    				}
    			}
    		}
    	}
    	// doesn't need to be accurate; just needs to have unique span
    	int [] moveStack = new int[12];
    	if (numTilesInStack[2] > 0) {
    		moveStack[11] = 3;
    	}
    	if (numTilesInStack[1] > 0) {
    		moveStack[10] = 2;
    	}
    	if (numTilesInStack[0] > 0) {
    		moveStack[9] = 1;
    	}
    	game.getGameState().setMoveStack(moveStack);

    	// debug printing
    	GameState gs = game.getGameState();
    	String prevMove = null;
    	if (gs.getPlayerToMove() == 2) { // since board has already been updated
    		prevMove = gs.getP1PreviousMove();
    	} else {
    		prevMove = gs.getP2PreviousMove();
    	}
    	System.out.println("Move " + gs.getMoveNum() + ", " + prevMove + ", board:");
    	System.out.println(Arrays.toString(gs.getBoardState()[0]));
    	System.out.println(Arrays.toString(gs.getBoardState()[1]));
    	System.out.println(Arrays.toString(gs.getBoardState()[2]));
    	System.out.println(Arrays.toString(gs.getBoardState()[3]));


    	// play the game until there is a winner
    	int winStatus = game.determineWinner();
    	int prevTile = 0;
    	while (winStatus == 0) {
    		// move player who is currently to move;
    		game.playerMove();
    		game.updateGameState();
    		winStatus = game.determineWinner();
    		
    		// debug printing
    		gs = game.getGameState();
    		prevMove = null;
    		if (gs.getPlayerToMove() == 2) { // since board has already been updated
        		prevMove = gs.getP1PreviousMove();
        	} else {
        		prevMove = gs.getP2PreviousMove();
        		// we just entered their move; update our external 
        		// move stack and reset for accurate alpha beta calcs
        		String[] parsedMove = prevMove.split("_");
        		prevTile = Integer.parseInt(parsedMove[0]);
        		if (prevTile <= 3) {
        			numTilesInStack[prevTile-1] -= 1;
        		}
        		// update our moveStack if we lose tile possibilities
        		for (int i = 0; i < 3; i++) {
        			if (numTilesInStack[i] == 0) {
        				moveStack[i + 9] = 0;
        			} else {
        				moveStack[i + 9] = i+1; // game update will overwrite; we need to reassign
        			}
        				
        		} 
        		// if all zero, reset move stack and numTiles
        		if (numTilesInStack[0] == 0 && numTilesInStack[1] == 0
        				&& numTilesInStack[2] == 0) {
        			numTilesInStack[0] = 4;
        			numTilesInStack[1] = 4;
        			numTilesInStack[2] = 4;
        			moveStack[9] = 1;
        			moveStack[10] = 2;
        			moveStack[11] = 3;
        		}
        		game.getGameState().setMoveStack(moveStack);    
        	}
    		System.out.println("Move " + gs.getMoveNum() + ", " + prevMove + ", board:");
    		System.out.println(Arrays.toString(gs.getBoardState()[0]));
    		System.out.println(Arrays.toString(gs.getBoardState()[1]));
    		System.out.println(Arrays.toString(gs.getBoardState()[2]));
    		System.out.println(Arrays.toString(gs.getBoardState()[3]));
    		System.out.println("Movestack: " + Arrays.toString(numTilesInStack));
    	}
    	game.input.close();
    	
    	System.out.println("Player " + winStatus + " wins!  Final Score " + 
    				game.getGameState().getGameScore());
    }
}
