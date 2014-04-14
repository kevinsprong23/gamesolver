package com.kevinsprong.gamesolver;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class WinConditionTest {
	TwoPlayerGame game;
	
	// instantiate a game
	@Before
	public void setUp() {
		game = new TwoZeroFourEight();	
	}
    
	//-------------------------------------------------------------------------
	// test player 1 wins
	@Test
	public void TestPlayer1Win1() {
		int[][] testBoard = {{0,2048,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(2);
		int winStatus = game.determineWinner();
		assertEquals(1, winStatus);
	}
	@Test
	public void TestPlayer1Win2() {
		int[][] testBoard = {{0,0,0,0},{0,0,2048,0},{0,0,0,0},{0,0,0,0}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(2);
		int winStatus = game.determineWinner();
		assertEquals(1, winStatus);
	}
	@Test
	public void TestPlayer1Win3() {
		int[][] testBoard = {{0,0,0,0},{0,0,0,0},{2048,0,0,0},{0,0,0,0}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(2);
		int winStatus = game.determineWinner();
		assertEquals(1, winStatus);
	}
	@Test
	public void TestPlayer1Win4() {
		int[][] testBoard = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,2048}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(2);
		int winStatus = game.determineWinner();
		assertEquals(1, winStatus);
	}
	@Test
	public void TestPlayer1Win5() {
		int[][] testBoard = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,4096}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(2);
		game.setWinCondition(4096);
		int winStatus = game.determineWinner();
		assertEquals(1, winStatus);
	}
	
	//-------------------------------------------------------------------------
	// test player 2 wins
	@Test
	public void TestPlayer2Win1() {
		int[][] testBoard = {{2,4,2,4},{4,2,4,2},{8,4,32,16},{128,256,512,256}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		int winStatus = game.determineWinner();
		assertEquals(2, winStatus);
	}
	@Test
	public void TestPlayer2Win2() {
		int[][] testBoard = {{1024,4,2,4},{512,2,4,2},{256,4,32,64},{128,256,512,256}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		int winStatus = game.determineWinner();
		assertEquals(2, winStatus);
	}
	//-------------------------------------------------------------------------
	// test alive status
	@Test
	public void TestNoWin1() {
		int[][] testBoard = {{0,4,2,4},{512,2,4,2},{256,4,32,2},{128,256,512,256}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		int winStatus = game.determineWinner();
		assertEquals(0, winStatus);
	}
	@Test
	public void TestNoWin2() {
		int[][] testBoard = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,4096}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(2);
		game.setWinCondition(8192);
		int winStatus = game.determineWinner();
		assertEquals(0, winStatus);
	}
	@Test
	public void TestNoWin3() {
		int[][] testBoard = {{0,4,2,4},{512,2,4,2},{256,4,32,2},{128,256,0,256}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(2);
		game.setWinCondition(8192);
		int winStatus = game.determineWinner();
		assertEquals(0, winStatus);
	}
}
