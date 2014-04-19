package com.kevinsprong.gamesolver;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class WinConditionTest {
	TwoPlayerGame game;
	
	// instantiate a game
	@Before
	public void setUp() {
		game = new Threes();	
	}
    
	//-------------------------------------------------------------------------
	// test player 1 wins
	@Test
	public void TestPlayer1Win1() {
		int[][] testBoard = {{0,6144,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(2);
		int winStatus = game.determineWinner();
		assertEquals(1, winStatus);
	}
	@Test
	public void TestPlayer1Win2() {
		int[][] testBoard = {{0,0,0,0},{0,0,6144,0},{0,0,0,0},{0,0,0,0}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(2);
		int winStatus = game.determineWinner();
		assertEquals(1, winStatus);
	}
	@Test
	public void TestPlayer1Win3() {
		int[][] testBoard = {{0,0,0,0},{0,0,0,0},{6144,0,0,0},{0,0,0,0}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(2);
		int winStatus = game.determineWinner();
		assertEquals(1, winStatus);
	}
	@Test
	public void TestPlayer1Win4() {
		int[][] testBoard = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,6144}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(2);
		int winStatus = game.determineWinner();
		assertEquals(1, winStatus);
	}
	@Test
	public void TestPlayer1Win5() {
		int[][] testBoard = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,768}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(2);
		game.setWinCondition(768);
		int winStatus = game.determineWinner();
		assertEquals(1, winStatus);
	}
	
	//-------------------------------------------------------------------------
	// test player 2 wins
	@Test
	public void TestPlayer2Win1() {
		int[][] testBoard = {{2,3,2,3},{3,2,3,2},{6,3,24,12},{48,96,192,48}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		int winStatus = game.determineWinner();
		assertEquals(2, winStatus);
	}
	@Test
	public void TestPlayer2Win2() {
		int[][] testBoard = {{3072,3,2,3},{1536,2,3,2},{96,3,24,48},{96,192,384,192}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		int winStatus = game.determineWinner();
		assertEquals(2, winStatus);
	}
	//-------------------------------------------------------------------------
	// test alive status
	@Test
	public void TestNoWin1() {
		int[][] testBoard = {{0,3,2,3},{1536,2,3,2},{96,3,24,48},{96,192,384,192}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		int winStatus = game.determineWinner();
		assertEquals(0, winStatus);
	}
	@Test
	public void TestNoWin2() {
		int[][] testBoard = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,6144}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(2);
		game.setWinCondition(6144*2);
		int winStatus = game.determineWinner();
		assertEquals(0, winStatus);
	}
	@Test
	public void TestNoWin3() {
		int[][] testBoard = {{0,3,2,3},{192,2,3,2},{96,3,24,2},{96,192,0,384}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(2);
		game.setWinCondition(6144*2);
		int winStatus = game.determineWinner();
		assertEquals(0, winStatus);
	}
}
