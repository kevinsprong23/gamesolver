package com.kevinsprong.gamesolver;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Class to play one (or many) game(s) of 2048 using the TwoZeroFourEight class
 */
public class Driver2048 {
    public static void main( String[] args ) {
        // make a new game
    	TwoZeroFourEight game = new TwoZeroFourEight("AlphaBeta", "UserInput");
    	game.input = new Scanner(System.in);
    	game.setSearchTime(100);
    	game.initializeBoard();
    	game.getGameState().setBoardState(new int[][]{
    			{128, 8, 8, 4},
    			{256, 8, 2, 0},
    			{1024, 4, 0, 0},
    			{64, 0, 0, 0}
    			});
    	
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
        	}
    		System.out.println("Move " + gs.getMoveNum() + ", " + prevMove + ", board:");
    		System.out.println(Arrays.toString(gs.getBoardState()[0]));
    		System.out.println(Arrays.toString(gs.getBoardState()[1]));
    		System.out.println(Arrays.toString(gs.getBoardState()[2]));
    		System.out.println(Arrays.toString(gs.getBoardState()[3]));
    	}
    	game.input.close();
    	
    	System.out.println("Player " + winStatus + " wins!  Final Score " + 
    				game.getGameState().getGameScore());
    }
}
