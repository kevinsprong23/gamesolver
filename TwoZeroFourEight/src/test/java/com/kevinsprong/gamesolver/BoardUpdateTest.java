package com.kevinsprong.gamesolver;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BoardUpdateTest {
	TwoPlayerGame game;
	
	// instantiate a game
	@Before
	public void setUp() {
		game = new TwoZeroFourEight();	
	}
    // shift with no collision
	@Test
	public void TestPlayer1Move1() {
		int[][] testBoard = {{0,2,4,0},{0,4,8,0},{0,0,0,128},{128,0,0,0}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		GameState newState = game.calcUpdatedGameState(game.getGameState(), "L");
		int[][] resultBoard = newState.getBoardState();
		
		// check against truth
		int[][] truthBoard = {{2,4,0,0},{4,8,0,0},{128,0,0,0},{128,0,0,0}};	
		for (int i = 0; i < 4; i++) {
			assertArrayEquals(truthBoard[i], resultBoard[i]);
		}
	}
	// shift with 1 collision
	@Test
	public void TestPlayer1Move2() {
		int[][] testBoard = {{0,4,4,0},{0,4,8,0},{0,0,0,128},{128,0,0,0}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		GameState newState = game.calcUpdatedGameState(game.getGameState(), "L");
		int[][] resultBoard = newState.getBoardState();

		// check against truth
		int[][] truthBoard = {{8,0,0,0},{4,8,0,0},{128,0,0,0},{128,0,0,0}};	
		for (int i = 0; i < 4; i++) {
			assertArrayEquals(truthBoard[i], resultBoard[i]);
		}
	}
	// shift with one collision on three tiles
	@Test
	public void TestPlayer1Move3() {
		int[][] testBoard = {{4,2,4,0},{4,4,8,0},{4,0,0,128},{128,0,0,0}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		GameState newState = game.calcUpdatedGameState(game.getGameState(), "U");
		int[][] resultBoard = newState.getBoardState();

		// check against truth
		int[][] truthBoard = {{8,2,4,128},{4,4,8,0},{128,0,0,0},{0,0,0,0}};	
		for (int i = 0; i < 4; i++) {
			assertArrayEquals(truthBoard[i], resultBoard[i]);
		}
	}
	// shift with two collisions on two pairs of two tiles
	@Test
	public void TestPlayer1Move4() {
		int[][] testBoard = {{4,0,0,0},{4,0,0,0},{128,0,0,0},{128,0,0,0}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		GameState newState = game.calcUpdatedGameState(game.getGameState(), "U");
		int[][] resultBoard = newState.getBoardState();

		// check against truth
		int[][] truthBoard = {{8,0,0,0},{256,0,0,0},{0,0,0,0},{0,0,0,0}};	
		for (int i = 0; i < 4; i++) {
			assertArrayEquals(truthBoard[i], resultBoard[i]);
		}
	}
	// shift with two collisions on four equal tiles
	@Test
	public void TestPlayer1Move5() {
		int[][] testBoard = {{128,0,0,0},{128,0,0,0},{128,0,0,0},{128,0,0,0}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		GameState newState = game.calcUpdatedGameState(game.getGameState(), "U");
		int[][] resultBoard = newState.getBoardState();

		// check against truth
		int[][] truthBoard = {{256,0,0,0},{256,0,0,0},{0,0,0,0},{0,0,0,0}};	
		for (int i = 0; i < 4; i++) {
			assertArrayEquals(truthBoard[i], resultBoard[i]);
		}
	}
	// shift with no collisions on populated board
	@Test
	public void TestPlayer1Move6() {
		int[][] testBoard = {{128,2,4,8},{128,2,4,0},{128,2,0,0},{128,0,0,0}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		GameState newState = game.calcUpdatedGameState(game.getGameState(), "R");
		int[][] resultBoard = newState.getBoardState();

		// check against truth
		int[][] truthBoard = {{128,2,4,8},{0,128,2,4},{0,0,128,2},{0,0,0,128}};	
		for (int i = 0; i < 4; i++) {
			assertArrayEquals(truthBoard[i], resultBoard[i]);
		}
	}
	// shift with no collisions on populated board
	@Test
	public void TestPlayer1Move7() {
		int[][] testBoard = {{128,2,4,8},{256,4,8,0},{128,2,4,64},{512,16,0,32}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		GameState newState = game.calcUpdatedGameState(game.getGameState(), "D");
		int[][] resultBoard = newState.getBoardState();

		// check against truth
		int[][] truthBoard = {{128,2,0,0},{256,4,4,8},{128,2,8,64},{512,16,4,32}};	
		for (int i = 0; i < 4; i++) {
			assertArrayEquals(truthBoard[i], resultBoard[i]);
		}
	}
}
