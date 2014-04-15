package com.kevinsprong.gamesolver;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * Class to hold game state
 */
public class GameState {
	private int[][] boardState;  // values are a mapping to game pieces
    private int playerToMove = 1;  // player 1 moves first by definition
    private String p1PreviousMove; 
    private String p2PreviousMove; 
    private double gameEval;  // positive/negative infinity for player 1/2 win
    private double gameScore;  // score if game supports it
    private double moveNum = 0;
    private ArrayList<Integer> moveQueue;
    
    //constructor
    public GameState() {
    	this.setBoardState(null);
    	this.setPlayerToMove(1);
    	this.setP1PreviousMove("");
    	this.setP2PreviousMove("");
    	this.setGameEval(0);
    	this.setGameScore(0);
    	this.setMoveNum(0);
    	this.setMoveQueue(new ArrayList<Integer>()); 
    }
    
    // create a copy of a game state to avoid in-function modifications
    public static GameState copyGameState(GameState gameStateIn) {
    	GameState gsOut = new GameState();
    	
    	// need to duplicate values here otherwise pointer will be copied
    	int[][] boardOrig = gameStateIn.getBoardState();
    	int[][] boardNew = new int[boardOrig.length][];
    	for (int i = 0; i < boardOrig.length; i++) {
    		boardNew[i] = Arrays.copyOf(boardOrig[i], boardOrig[i].length);
    	}
    	
    	gsOut.setBoardState(boardNew); 
    	gsOut.setPlayerToMove(gameStateIn.getPlayerToMove()); 
    	gsOut.setP1PreviousMove(gameStateIn.getP1PreviousMove()); 
    	gsOut.setP2PreviousMove(gameStateIn.getP2PreviousMove()); 
    	gsOut.setGameEval(gameStateIn.getGameEval()); 
    	gsOut.setGameScore(gameStateIn.getGameScore()); 
    	gsOut.setMoveNum(gameStateIn.getMoveNum()); 
    	gsOut.setMoveQueue(gameStateIn.getMoveQueue()); 
     		
    	return gsOut;
    }
    
    // get/set for boardState
 	public int[][] getBoardState() {
 		return this.boardState;
 	}
 	public void setBoardState(int[][] boardStateIn) {
 		this.boardState= boardStateIn;
 	}
 	// get/set for playerToMove
 	public int getPlayerToMove() {
 		return this.playerToMove;
 	}
 	public void setPlayerToMove(int playerToMoveIn) {
 		this.playerToMove= playerToMoveIn;
 	}
 	// get/set for p1PreviousMove
 	public String getP1PreviousMove() {
 		return this.p1PreviousMove;
 	}
 	public void setP1PreviousMove(String p1PreviousMoveIn) {
 		this.p1PreviousMove= p1PreviousMoveIn;
 	}
 	// get/set for p2PreviousMove
 	public String getP2PreviousMove() {
 		return this.p2PreviousMove;
 	}
 	public void setP2PreviousMove(String p2PreviousMoveIn) {
 		this.p2PreviousMove= p2PreviousMoveIn;
 	}
 	// get/set for gameEval
 	public double getGameEval() {
 		return this.gameEval;
 	}
 	public void setGameEval(double gameEvalIn) {
 		this.gameEval= gameEvalIn;
 	}
 	// get/set for gameScore
 	public double getGameScore() {
 		return this.gameScore;
 	}
 	public void setGameScore(double gameScoreIn) {
 		this.gameScore= gameScoreIn;
 	}
 	// get/set for moveNum
 	public double getMoveNum() {
 		return this.moveNum;
 	}
 	public void setMoveNum(double moveNumIn) {
 		this.moveNum= moveNumIn;
 	}
    // get/set for moveQueue
  	public ArrayList<Integer> getMoveQueue() {
  		return this.moveQueue;
  	}
  	public void setMoveQueue(ArrayList<Integer> moveQueueIn) {
  		this.moveQueue = moveQueueIn;
  	}
}
