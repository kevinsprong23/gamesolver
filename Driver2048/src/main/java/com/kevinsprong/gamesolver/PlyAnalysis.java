package com.kevinsprong.gamesolver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class to play one (or many) game(s) of 2048 using the TwoZeroFourEight class
 */
public class PlyAnalysis {
	public static void main( String[] args ) throws IOException {
		// num sims per parameter setting
		int numTrials = 20;

		// sim parameters
		double [] plyRange = {1, 7, 1};

		ArrayList<Double> plyVec = new ArrayList<Double>();

		for (double pR = plyRange[0]; pR <= plyRange[1]; pR += plyRange[2]) {
			plyVec.add(pR);
		}

		int totalNumSettings = plyVec.size();

		// variables to hold game info
		TwoZeroFourEight game;
		int winStatus;
		int highTile;
		double score;
		double moveNum;

		// variables to hold sim results
		int highestTile = 0;
		int[] winRecord = new int[numTrials];
		int[] bigWinRecord = new int[numTrials];
		int[] highTiles = new int[numTrials];
		int[] scores  = new int[numTrials];
		int[] moveNums = new int[numTrials];
		double avgWinPct;
		double bigWinPct;
		double avgHighTile;
		double avgScore;
        double avgMoveNum;
        
		// file to write to
		BufferedWriter writer = null;
		String resultsFilePath = "PlyAnalysis2048.csv";
		File resultsFile = new File(resultsFilePath);
		String newline = System.getProperty("line.separator");
		System.out.println(resultsFile.getCanonicalPath());
		writer = new BufferedWriter(new FileWriter(resultsFile));
		writer.write("ply,avgWinPct,avgBigWinPct, avgHighTile,highTile,avgScore,avgMoveNum" +
				newline);

		// loop to optimize parameters
		long startTime = System.nanoTime();
		int thisSetting = 0;
		for (double pR : plyVec) {

			thisSetting++;

			// reset results vectors
			highestTile = 0;
			winRecord = new int[numTrials];
			bigWinRecord = new int[numTrials];
			highTiles = new int[numTrials];
			scores  = new int[numTrials];
			moveNums = new int[numTrials];

			for (int k = 0; k < numTrials; k++) {

				System.out.println(Double.toString(pR) + ", " +
					" Setting " + thisSetting + 
					" of " + totalNumSettings + 
					", Trial " + Integer.toString(k+1));

				// create a new game
				game = new TwoZeroFourEight("AlphaBeta", "DefaultComputer");
				game.setSearchPly(7);
				game.setWinCondition(65536);
				game.setHeuristicWeights(new double[]{500, 2.2, 4.6, 0});
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
				moveNum = game.getGameState().getMoveNum();

				// update results vectors
				if (highTile > highestTile) {
					highestTile = highTile;
				}
				scores[k] = (int) score;
				highTiles[k] = highTile;
				moveNums[k] = (int) moveNum;
				if (highTile >= 4096) {
					bigWinRecord[k] = 1;
					winRecord[k] = 1;
				} else if (highTile >= 2048) {
					bigWinRecord[k] = 0;
					winRecord[k] = 1;
				} else {
					bigWinRecord[k] = 0;
					winRecord[k] = 0;
				}
				
			}

			// summarize trials and write to file
			avgWinPct = calculateAverage(winRecord);
			bigWinPct = calculateAverage(bigWinRecord);
			avgHighTile = calculateAverage(highTiles);
			avgScore = calculateAverage(scores);
			avgMoveNum = calculateAverage(moveNums);

			writer.write(
					Double.toString(pR) + "," +
						Double.toString(avgWinPct) + "," +
						Double.toString(bigWinPct) + "," +
						Double.toString(avgHighTile) + "," +
						Integer.toString(highestTile) + "," +
						Double.toString(avgScore) + "," +
						Double.toString(avgMoveNum) + newline
					);
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