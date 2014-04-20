package com.kevinsprong.gamesolver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class to play one (or many) game(s) of 2048 using the TwoZeroFourEight class
 */
public class ParamOptimizer2048 {
    public static void main( String[] args ) throws IOException {
    	// out file location
    	String resultsFilePath = "ParameterSearch2048Initial.csv";
    	
    	// num sims per parameter setting
    	int numTrials = 20;
    	
    	// sim parameters
    	double [] winRange = {500, 500, 1};
    	double [] monoRange =  {2, 3.1, 0.2};
    	double [] smoothRange =  {2.4, 3.81, 0.2};
    	double [] openRange =  {0, 0, 1};
    	ArrayList<Double> winVec = new ArrayList<Double>();
    	ArrayList<Double> monoVec = new ArrayList<Double>();
    	ArrayList<Double> smoothVec = new ArrayList<Double>();
    	ArrayList<Double> openVec = new ArrayList<Double>();
    	for (double wR = winRange[0]; wR <= winRange[1]; wR += winRange[2]) {
    		winVec.add(wR);
    	}
    	for (double mR = monoRange[0]; mR <= monoRange[1]; mR += monoRange[2]) {
    		monoVec.add(mR);
    	}
    	for (double sR = smoothRange[0]; sR <= smoothRange[1]; sR += smoothRange[2]) {
    		smoothVec.add(sR);
    	}
    	for (double oR = openRange[0]; oR <= openRange[1]; oR += openRange[2]) {
    		openVec.add(oR);
    	}
    	int totalNumSettings = winVec.size() * monoVec.size() * 
    			smoothVec.size() * openVec.size();
    	
    	// variables to hold game info
    	TwoZeroFourEight game;
    	int winStatus;
    	int highTile;
    	double score;
    	
    	// variables to hold sim results
    	int highestTile = 0;
    	int[] winRecord = new int[numTrials];
    	int[] highTiles = new int[numTrials];
    	int[] scores  = new int[numTrials];
    	double avgWinPct;
    	double avgHighTile;
    	double avgScore;
    	
    	// file to write to
    	File resultsFile = new File(resultsFilePath);
    	System.out.println(resultsFile.getCanonicalPath());
    	FileWriter writer = new FileWriter(resultsFile);
    	// print header
    	String newline = System.getProperty("line.separator");
    	writer.write("win,mono,smooth,open,avgWinPct,avgHighTile,highTile,avgScore" +
    							newline);

        // loop to optimize parameters
    	long startTime = System.nanoTime();
    	int thisSetting = 0;
    	for (double wR : winVec) {
    		for (double mR : monoVec) {
    			for (double sR : smoothVec) {
    				for (double oR : openVec) {
    					thisSetting++;
    					
    					// prevent all zeros among non-win parameters
    					if (mR == 0 && sR == 0 && oR == 0) {
    						mR = 1;
    						sR = 1;
    						oR = 0;
    					}		
    					
    					highestTile = 0;
    			    	winRecord = new int[numTrials];
    			    	highTiles = new int[numTrials];
    			    	scores  = new int[numTrials];
    					
    					for (int k = 0; k < numTrials; k++) {
    						
    						System.out.println(Double.toString(wR) + ", " +
        							Double.toString(mR) + ", " +
        							Double.toString(sR) + ", " +
        							Double.toString(oR) + ", " +
        							" Setting " + thisSetting + 
        							" of " + totalNumSettings + 
        							", Trial " + Integer.toString(k+1));
        					
    						
    						
    						
    						// create a new game
    						game = new TwoZeroFourEight("AlphaBeta", "DefaultComputer");
    						game.setSearchPly(6);
    						game.setWinCondition(65536);
    						game.setHeuristicWeights(new double[]{wR, mR, sR, oR});
    						game.initializeBoard();

    						// play the game until there is a winner
    						winStatus = game.determineWinner();
    						while (winStatus == 0) {
    							// move player who is currently to move;
    							game.playerMove();
    							game.updateGameState();	
    							winStatus = game.determineWinner();		
    						}
    						
    						// get game results
    						score = game.getGameState().getGameScore();
    						highTile = getHighTile(game.getGameState().getBoardState());
    						
    						// update results vectors
    						if (highTile > highestTile) {
    							highestTile = highTile;
    						}
    						scores[k] = (int) score;
    						highTiles[k] = highTile;
    						if (highTile >= 2048) {
    							winRecord[k] = 1;
    						} else {
    							winRecord[k] = 0;
    						}
    					}
    					
    					// summarize trials and write to file
    					avgWinPct = calculateAverage(winRecord);
    					avgHighTile = calculateAverage(highTiles);
    					avgScore = calculateAverage(scores);
    					
    					writer.write(
    							Double.toString(wR) + "," +
    							Double.toString(mR) + "," +
    							Double.toString(sR) + "," +
    							Double.toString(oR) + "," +
    							Double.toString(avgWinPct) + "," +
    							Double.toString(avgHighTile) + "," +
    							Integer.toString(highestTile) + "," +
    							Double.toString(avgScore) + newline
    							);
    					writer.flush();
    				}
    			}
    		}
    	}
    	double elapsedTime = (double) (System.nanoTime() - startTime) / 1e9;
    	System.out.println("Done!  Time Elapsed: " + 
    			Double.toString(elapsedTime) + " s");
    	
    	// close our writer
    	writer.close();

    }

    public static int getHighTile(int[][] board) {
    	int maxTile = 0;
    	for (int i = 0; i < 4; i++) {
    		for (int j = 0; j < 4; j++) {
    			if (board[i][j] > maxTile) {
    				maxTile = board[i][j];
    			}
    		}
    	}
    	return maxTile;
    }

    public static double calculateAverage(int[] marks) {
    	double sum = 0;
    	if(marks.length > 0) {
    		for (int mark : marks) {
    			sum += mark;
    		}
    		return (double) sum / marks.length;
    	}
    	return sum;
    }
    

}
