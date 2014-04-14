package com.kevinsprong.gamesolver;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LegalMoveTest {
	TwoPlayerGame game;
	
	// instantiate a game
	@Before
	public void setUp() {
		game = new TwoZeroFourEight();	
	}
    //-------------------------------------------------------------------------
	// check player 1 legal moves
    // order of output vector is always U,D,L,R
	@Test
	public void TestPlayer1Moves1() {
		int[][] testBoard = {{0,2,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		String[] legalMoves = game.findLegalMoves(game.getGameState());
		assertArrayEquals(new String[]{"D","L","R"}, legalMoves);
	}
	@Test
	public void TestPlayer1Moves2() {
		int[][] testBoard = {{2,2,2,2},{2,2,2,2},{2,2,2,2},{2,2,2,2}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		String[] legalMoves = game.findLegalMoves(game.getGameState());
		assertArrayEquals(new String[]{"U","D","L","R"}, legalMoves);
	}
	@Test
	public void TestPlayer1Moves3() {
		int[][] testBoard = {{2,4,8,4},{4,2,16,8},{8,16,4,32},{128,0,2,16}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		String[] legalMoves = game.findLegalMoves(game.getGameState());
		assertArrayEquals(new String[]{"D","L","R"}, legalMoves);
	}
	@Test
	public void TestPlayer1Moves4() {
		int[][] testBoard = {{2,4,8,4},{4,2,16,8},{8,16,4,32},{0,128,2,16}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		String[] legalMoves = game.findLegalMoves(game.getGameState());
		assertArrayEquals(new String[]{"D","L"}, legalMoves);
	}
	@Test
	public void TestPlayer1Moves5() {
		int[][] testBoard = {{2,4,8,4},{4,2,16,8},{8,16,4,32},{1024,128,2,16}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		String[] legalMoves = game.findLegalMoves(game.getGameState());
		assertArrayEquals(new String[]{} , legalMoves);
	}
	//-------------------------------------------------------------------------
	// check player 2 legal moves
	// order of output vector is always 2_n_n, 4_n,n, where n increases first 
	// over j, then over i
	public void TestPlayer2Moves1() {
		int[][] testBoard = {{2,4,8,4},{4,2,2,8},{8,16,4,32},{128,0,2,16}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(2);
		String[] legalMoves = game.findLegalMoves(game.getGameState());
		assertArrayEquals(new String[]{"2_3_1","4_3_1"}, legalMoves);
	}
	public void TestPlayer2Moves2() {
		int[][] testBoard = {{2,4,0,4},{0,0,2,8},{8,16,4,32},{128,4,2,16}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(2);
		String[] legalMoves = game.findLegalMoves(game.getGameState());
		assertArrayEquals(new String[]{"2_0_2", "2_1_0", "2_1_1",
				"4_0_2", "4_1_0", "4_1_1"}, legalMoves);
	}
}
