package com.kevinsprong.gamesolver;

import java.util.Arrays;

/**
 * Class to play one (or many) game(s) of 2048 using the TwoZeroFourEight class
 */
public class SingleBoardSolver
{
    public static void main( String[] args )
    {
        // make a new game
    	TwoZeroFourEight game = new TwoZeroFourEight("AlphaBeta", "DefaultComputer");
    	
    	game.setSearchPly(7);
    	game.setWinCondition(8192);
    	
    	int[][] inputBoard = {
    			{0,4,2,0},
    			{0,0,0,0},
    			{0,0,0,0},
    			{0,0,0,0}
    			};
    	
    	
    	// input printing
		GameState gs = game.getGameState();
		gs.setBoardState(inputBoard);
		
		System.out.println(Arrays.toString(gs.getBoardState()[0]));
		System.out.println(Arrays.toString(gs.getBoardState()[1]));
		System.out.println(Arrays.toString(gs.getBoardState()[2]));
		System.out.println(Arrays.toString(gs.getBoardState()[3]));
		
		
		// solve the board
		game.playerMove();
		game.updateGameState();	
		
		// update
		System.out.println("Best move: " + gs.getP1PreviousMove());

    }
}
