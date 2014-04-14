package com.kevinsprong.gamesolver;

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
		String bestMove = "";
		
		// get the game state and solver parameters
		GameState currentGameState = GameState.copyGameState(game.getGameState());
		int searchPly = game.getSearchPly();
		
		MoveNode originNode = new MoveNode(currentGameState, searchPly);
		double moveEval = alphaBeta(game, originNode, searchPly, true);
		
		// find the move that produced the best evaluation
		for (MoveNode child : originNode.getNodeChildren()) {
			if (child.getBeta() == moveEval) {
				bestMove = child.getMoveName();
				break;
			}
		}
		game.getGameState().setGameEval(moveEval);
		
		return bestMove;
	}
	
	private static double alphaBeta(TwoPlayerGame game, MoveNode thisNode, 
			int searchPly, boolean maximizingPlayer) {
		
		// get the moveNode's game state
		GameState thisGS = thisNode.getNodeGameState();
		
		// if searchPly is 0 or there is a winner, return game evaluation
		if ((searchPly == 0) || 
				(game.determineWinner(GameState.copyGameState(thisGS)) != 0) ){
			double eval = game.evaluateGameState(thisGS);
			if (maximizingPlayer) {
				thisNode.setAlpha(eval);
			} else {
				thisNode.setBeta(eval);
			}
			return eval;
		}

		// else set up Node Tree for legal moves
		GameState nextGS;
		MoveNode newChild;
		String[] legalMoves = game.findLegalMoves(thisGS);
		for (String move : legalMoves) {
			nextGS = GameState.copyGameState(thisGS);
			nextGS = game.calcUpdatedGameState(nextGS, move);
			nextGS.setPlayerToMove((nextGS.getPlayerToMove() % 2) + 1);
			newChild = new MoveNode(nextGS, searchPly-1, move);
			thisNode.getNodeChildren().add(newChild);
		}
		
		// get alpha and beta
		double alpha = thisNode.getAlpha();
		double beta = thisNode.getBeta();
		
		// loop over children and return 
		if (maximizingPlayer) {
			for (MoveNode child : thisNode.getNodeChildren()) {
				child.setAlpha(thisNode.getAlpha());
				child.setBeta(thisNode.getBeta());
				alpha = Math.max(alpha, alphaBeta(game, child, searchPly-1, false));
				thisNode.setAlpha(alpha);
				if (alpha >= beta) {
					break;
				}
			}	
			return alpha;
		} else { // minimizing player
			for (MoveNode child : thisNode.getNodeChildren()) {
				child.setAlpha(thisNode.getAlpha());
				child.setBeta(thisNode.getBeta());
				beta = Math.min(beta, alphaBeta(game, child, searchPly-1,true));
				thisNode.setBeta(beta);
				if (alpha >= beta) {
					break;
				}
			}
			return beta;
		}

	}

}
