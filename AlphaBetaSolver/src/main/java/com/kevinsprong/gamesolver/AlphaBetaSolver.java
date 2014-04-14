package com.kevinsprong.gamesolver;

import java.util.ArrayList;

/**
 * Module to perform Alpha Beta Pruning to find the next move in a TwoPlayerGame
 */
public class AlphaBetaSolver {
	
	// methods
	// method to take a TPG and return the best move based on A-B pruning
	// uses functions from TwoPlayerGame:  	calcUpdatedGameState
	//										findLegalMoves
	//										determineWinner
	//										evaluateGameState
			
	public static String solveBoard(TwoPlayerGame game) {
		String bestMove = null;
		
		// get the game state and solver parameters
		GameState currentGameState = GameState.copyGameState(game.getGameState());
		int searchPly = game.getSearchPly();
		
		MoveNode originNode = new MoveNode(currentGameState, searchPly);
		double moveEval = alphaBeta(game, originNode, searchPly,
				Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true);
		
		// find the move that produced the best evaluation
		for (MoveNode child : originNode.getNodeChildren()) {
			if (child.getAlpha() == moveEval) {
				bestMove = child.getMoveName();
				break;
			}
		}
		game.getGameState().setGameEval(moveEval);
		
		return bestMove;
	}
	
	private static double alphaBeta(TwoPlayerGame game, MoveNode thisNode, 
			int searchPly, double alpha, double beta, 
			boolean maximizingPlayer) {
		
		// get the moveNode's game state
		GameState thisGS = thisNode.getNodeGameState();
		
		// if searchPly is 0, return current game evaluation
		if (searchPly == 0) {
			return game.evaluateGameState(thisGS);
		}
		// find legal moves
		String[] legalMoves = game.findLegalMoves(thisGS);
		
		// if none, return current game evaluation
		if (legalMoves.length == 0) {
			return game.evaluateGameState(thisGS);
		}
		
		// else set up Node Tree for legal moves
		
		
		
		
		return beta;

	}

}
