package com.kevinsprong.gamesolver;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.kevinsprong.gamesolver.AlphaBetaSolver;

public class AlphaBeta2048Test {

	TwoPlayerGame game;
	
	// instantiate a game
	@Before
	public void setUp() {
		game = new TwoZeroFourEight();	
	}

	// a forced win exists in search space
	@Test
	public void TestPlayer1Move1() {
		int[][] testBoard = {{1024,512,256,128},{8,16,64,64},{2,4,16,8},{8,64,32,2}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);

		String bestMove = AlphaBetaSolver.solveBoard(game);

		// check against truth
		assertEquals("R", bestMove);
	}
	// a forced win exists but is beyond search ply
	@Test
	public void TestPlayer1Move2() {
		int[][] testBoard = {{1024,512,256,128},{8,16,32,64},{8,0,0,0},{0,0,0,0}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);

		String bestMove = AlphaBetaSolver.solveBoard(game);

		// check against truth
		assertEquals("U", bestMove);
	}

}
