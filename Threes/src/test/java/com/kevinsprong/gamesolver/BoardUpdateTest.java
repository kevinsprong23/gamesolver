package com.kevinsprong.gamesolver;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BoardUpdateTest {
	TwoPlayerGame game;
	
	// instantiate a game
	@Before
	public void setUp() {
		game = new Threes();	
	}
    // shift with no collision
	@Test
	public void TestPlayer1Move1() {
		int[][] testBoard = {{0,1,2,0},{0,3,6,0},{0,2,0,96},{1536,0,0,128}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		GameState newState = game.calcUpdatedGameState(game.getGameState(), "L");
		int[][] resultBoard = newState.getBoardState();
		
		// check against truth
		int[][] truthBoard = {{1,2,0,0},{3,6,0,0},{2,0,96,0},{1536,0,128,0}};	
		for (int i = 0; i < 4; i++) {
			assertArrayEquals(truthBoard[i], resultBoard[i]);
		}
	}
	// shift with 1 collision
	@Test
	public void TestPlayer1Move2() {
		int[][] testBoard = {{1,2,3,0},{0,3,6,0},{0,0,0,192},{192,0,0,0}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		GameState newState = game.calcUpdatedGameState(game.getGameState(), "L");
		int[][] resultBoard = newState.getBoardState();

		// check against truth
		int[][] truthBoard = {{3,3,0,0},{3,6,0,0},{0,0,192,0},{192,0,0,0}};	
		for (int i = 0; i < 4; i++) {
			assertArrayEquals(truthBoard[i], resultBoard[i]);
		}
	}
	// shift with one collision on three tiles
	@Test
	public void TestPlayer1Move3() {
		int[][] testBoard = {{3,2,1,0},{3,2,1,0},{3,0,0,96},{96,0,0,0}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		GameState newState = game.calcUpdatedGameState(game.getGameState(), "U");
		int[][] resultBoard = newState.getBoardState();

		// check against truth
		int[][] truthBoard = {{6,2,1,0},{3,2,1,96},{96,0,0,0},{0,0,0,0}};
		for (int i = 0; i < 4; i++) {
			assertArrayEquals(truthBoard[i], resultBoard[i]);
		}
	}
	// shift with two collisions on two pairs of two tiles
	@Test
	public void TestPlayer1Move4() {
		int[][] testBoard = {{3,0,0,0},{3,0,0,0},{192,0,0,0},{192,0,0,0}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		GameState newState = game.calcUpdatedGameState(game.getGameState(), "U");
		int[][] resultBoard = newState.getBoardState();

		// check against truth
		int[][] truthBoard = {{6,0,0,0},{192,0,0,0},{192,0,0,0},{0,0,0,0}};	
		for (int i = 0; i < 4; i++) {
			assertArrayEquals(truthBoard[i], resultBoard[i]);
		}
	}
	// shift with one collisions on four equal tiles
	@Test
	public void TestPlayer1Move5() {
		int[][] testBoard = {{96,0,0,0},{96,0,0,0},{96,0,0,0},{96,0,0,0}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		GameState newState = game.calcUpdatedGameState(game.getGameState(), "U");
		int[][] resultBoard = newState.getBoardState();

		// check against truth
		int[][] truthBoard = {{192,0,0,0},{96,0,0,0},{96,0,0,0},{0,0,0,0}};	
		for (int i = 0; i < 4; i++) {
			assertArrayEquals(truthBoard[i], resultBoard[i]);
		}
	}
	// shift with no collisions on populated board
	@Test
	public void TestPlayer1Move6() {
		int[][] testBoard = {{192,2,3,6},{192,2,3,6},{192,2,3,0},{192,0,0,0}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		GameState newState = game.calcUpdatedGameState(game.getGameState(), "R");
		int[][] resultBoard = newState.getBoardState();

		// check against truth
		int[][] truthBoard = {{192,2,3,6},{192,2,3,6},{0,192,2,3},{0,192,0,0}};
		for (int i = 0; i < 4; i++) {
			assertArrayEquals(truthBoard[i], resultBoard[i]);
		}
	}
	// shift with no collisions on populated board
	@Test
	public void TestPlayer1Move7() {
		int[][] testBoard = {{96,2,3,6},{192,3,6,0},{96,2,3,12},{384,24,0,48}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		GameState newState = game.calcUpdatedGameState(game.getGameState(), "D");
		int[][] resultBoard = newState.getBoardState();

		// check against truth
		int[][] truthBoard = {{96,2,0,0},{192,3,3,6},{96,2,6,12},{384,24,3,48}};	
		for (int i = 0; i < 4; i++) {
			assertArrayEquals(truthBoard[i], resultBoard[i]);
		}
	}
	// shift with no collisions on open column, one collision on right
	@Test
	public void TestPlayer1Move8() {
		int[][] testBoard = {{0,0,0,1},{0,0,6,2},{0,0,3,3},{2,1,3,6}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		GameState newState = game.calcUpdatedGameState(game.getGameState(), "U");
		int[][] resultBoard = newState.getBoardState();

		// check against truth
		int[][] truthBoard = {{0,0,6,3},{0,0,3,3},{2,1,3,6},{0,0,0,0}};
		for (int i = 0; i < 4; i++) {
			assertArrayEquals(truthBoard[i], resultBoard[i]);
		}
	}
}
