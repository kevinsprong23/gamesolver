package com.kevinsprong.gamesolver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class to play one (or many) game(s) of 2048 using the TwoZeroFourEight class
 */
public class TimeAnalysis {
	public static void main( String[] args ) throws IOException {
		
		// output file
		String resultsFilePath = "TimeAnalysisThrees.csv";
		// num sims per parameter setting
		int numTrials = 30;

		// sim parameters
		int [] timeRange = {0, 100, 10};

		ArrayList<Integer> timeVec = new ArrayList<Integer>();

		for (int tR = timeRange[0]; tR <= timeRange[1]; tR += timeRange[2]) {
			timeVec.add(tR);
		}

		int totalNumSettings = timeVec.size();

		// variables to hold game info
		Threes game;
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
		File resultsFile = new File(resultsFilePath);
		System.out.println(resultsFile.getCanonicalPath());
		FileWriter writer = new FileWriter(resultsFile);
		// print header
		String newline = System.getProperty("line.separator");
		writer.write("searchTime,avgWinPct,avgBigWinPct,avgHighTile,highTile,avgScore,avgMoveNum" +
				newline);

		// loop to optimize parameters
		long startTime = System.nanoTime();
		int thisSetting = 0;
		for (int tR : timeVec) {

			thisSetting++;

			// reset results vectors
			highestTile = 0;
			winRecord = new int[numTrials];
			bigWinRecord = new int[numTrials];
			highTiles = new int[numTrials];
			scores  = new int[numTrials];
			moveNums = new int[numTrials];

			for (int k = 0; k < numTrials; k++) {

				System.out.println(Double.toString(tR) + ", " +
					" Setting " + thisSetting + 
					" of " + totalNumSettings + 
					", Trial " + Integer.toString(k+1));

				// create a new game
				if (tR == 0) {
					game = new Threes("Random", "DefaultComputer");
				} else {
					game = new Threes("AlphaBeta", "DefaultComputer");
				}
				game.setSearchTime(tR);
				game.setWinCondition(6144);
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
					Double.toString(tR) + "," +
						Double.toString(avgWinPct) + "," +
						Double.toString(bigWinPct) + "," +
						Double.toString(avgHighTile) + "," +
						Integer.toString(highestTile) + "," +
						Double.toString(avgScore) + "," +
						Double.toString(avgMoveNum) + newline
					);
			writer.flush();
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