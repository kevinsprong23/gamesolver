package com.kevinsprong.gamesolver;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LegalMoveTest {
	TwoPlayerGame game;
	
	// instantiate a game
	@Before
	public void setUp() {
		game = new Threes();
	}
    //-------------------------------------------------------------------------
	// check player 1 legal moves
    // order of output vector is always U,D,L,R
	@Test
	public void TestPlayer1Moves1() {
		int[][] testBoard = {{0,48,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		String[] legalMoves = game.findLegalMoves(game.getGameState());
		assertArrayEquals(new String[]{"D","L","R"}, legalMoves);
	}
	@Test
	public void TestPlayer1Moves2() {
		int[][] testBoard = {{3,3,3,3},{3,3,3,3},{3,3,3,3},{3,3,3,3}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		String[] legalMoves = game.findLegalMoves(game.getGameState());
		assertArrayEquals(new String[]{"U","D","L","R"}, legalMoves);
	}
	@Test
	public void TestPlayer1Moves3() {
		int[][] testBoard = {{3,2,3,2},{2,3,2,3},{6,1,24,1},{96,0,2,3}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		String[] legalMoves = game.findLegalMoves(game.getGameState());
		assertArrayEquals(new String[]{"D","L","R"}, legalMoves);
	}
	@Test
	public void TestPlayer1Moves4() {
		int[][] testBoard = {{3,2,3,2},{2,3,2,3},{6,1,24,1},{0,96,2,3}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		String[] legalMoves = game.findLegalMoves(game.getGameState());
		assertArrayEquals(new String[]{"D","L"}, legalMoves);
	}
	@Test
	public void TestPlayer1Moves5() {
		int[][] testBoard = {{1,3,1,3},{3,6,3,6},{6,12,24,48},{48,1,1,3}};
		game.getGameState().setBoardState(testBoard);
		game.getGameState().setPlayerToMove(1);
		String[] legalMoves = game.findLegalMoves(game.getGameState());
		assertArrayEquals(new String[]{}, legalMoves);
	}
}
