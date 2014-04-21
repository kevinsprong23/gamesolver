package com.kevinsprong.gamesolver;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.kevinsprong.gamesolver.AlphaBetaSolver;

public class AlphaBetaThreesTest {

	Threes game;
	
	// instantiate a game
	@Before
	public void setUp() {
		game = new Threes();	
	}

	// a forced win exists in search space
	
	@Test
	public void TestPlayer1Move1() {
		int[][] testBoard = {{3072,1536,768,384},{48,96,192,192},{2,3,6,12},{3,24,12,2}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		game.getGameState().setMoveStack(game.generateNewMoveStack());

		String bestMove = AlphaBetaSolver.solveBoard(game);

		// check against truth
		assertEquals("R", bestMove);
	}
	// a forced win exists but is beyond search ply
	@Test
	public void TestPlayer1Move2() {
		int[][] testBoard = {{3072,1536,768,384},{24,48,96,192},{24,3,6,12},{3,24,12,2}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		game.getGameState().setMoveStack(game.generateNewMoveStack());

		String bestMove = AlphaBetaSolver.solveBoard(game);

		// check against truth
		assertEquals("U", bestMove);
	}
	

}
