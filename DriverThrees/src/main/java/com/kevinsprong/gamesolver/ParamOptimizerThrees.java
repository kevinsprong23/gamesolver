package com.kevinsprong.gamesolver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class to play one (or many) game(s) of 2048 using the TwoZeroFourEight class
 */
public class ParamOptimizerThrees {
    public static void main( String[] args ) throws IOException {
    	// out file location
    	String resultsFilePath = "ParameterSearch2048Initial.csv";
    	
    	// num sims per parameter setting
    	int numTrials = 30;
    	
    	// sim parameters
    	double [] monoRange =  {0, 3, 1};
    	double [] smoothRange =  {0, 3, 1};
    	double [] checkerRange = {0, 3, 1};
    	double [] openRange =  {0, 3, 1};
    	ArrayList<Double> monoVec = new ArrayList<Double>();
    	ArrayList<Double> smoothVec = new ArrayList<Double>();
    	ArrayList<Double> checkerVec = new ArrayList<Double>();
    	ArrayList<Double> openVec = new ArrayList<Double>();
    	
    	for (double mR = monoRange[0]; mR <= monoRange[1]; mR += monoRange[2]) {
    		monoVec.add(mR);
    	}
    	for (double sR = smoothRange[0]; sR <= smoothRange[1]; sR += smoothRange[2]) {
    		smoothVec.add(sR);
    	}
    	for (double cR = checkerRange[0]; cR <= checkerRange[1]; cR += checkerRange[2]) {
    		checkerVec.add(cR);
    	}
    	for (double oR = openRange[0]; oR <= openRange[1]; oR += openRange[2]) {
    		openVec.add(oR);
    	}
    	int totalNumSettings = monoVec.size() * 
    			smoothVec.size() * checkerVec.size() * openVec.size();
    	
    	// variables to hold game info
    	Threes game;
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
    	writer.write("mono,smooth,checker,open,avgWinPct,avgHighTile,highTile,avgScore" +
    							newline);

        // loop to optimize parameters
    	long startTime = System.nanoTime();
    	int thisSetting = 0;
    	for (double mR : monoVec) {
    		for (double sR : smoothVec) {
    			for (double cR : checkerVec) {
    				for (double oR : openVec) {
    					thisSetting++;
    					
    					// prevent all zeros among non-win parameters
    					if (mR == 0 && sR == 0 && cR == 0 && oR == 0) {
    						mR = 1;
    						sR = 1;
    						cR = 1;
    						oR = 1;
    					}		
    					
    					highestTile = 0;
    			    	winRecord = new int[numTrials];
    			    	highTiles = new int[numTrials];
    			    	scores  = new int[numTrials];
    					
    					for (int k = 0; k < numTrials; k++) {
    						
    						System.out.println(Double.toString(mR) + ", " +
        							Double.toString(sR) + ", " +
        							Double.toString(cR) + ", " +
        							Double.toString(oR) + ", " +
        							" Setting " + thisSetting + 
        							" of " + totalNumSettings + 
        							", Trial " + Integer.toString(k+1));
        					
    						
    						
    						
    						// create a new game
    						game = new Threes("AlphaBeta", "DefaultComputer");
    						game.setSearchPly(7);
    						game.setWinCondition(65536);
    						game.setHeuristicWeights(new double[]{500, mR, sR, cR, oR});
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
    						if (highTile >= 6144) {
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
    							Double.toString(mR) + "," +
    							Double.toString(sR) + "," +
    							Double.toString(cR) + "," +
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
