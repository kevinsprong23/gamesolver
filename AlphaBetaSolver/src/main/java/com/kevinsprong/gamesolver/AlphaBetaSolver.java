package com.kevinsprong.gamesolver;

import java.util.ArrayList;

/**
 * Module to perform Alpha Beta Pruning to find the next move in a TwoPlayerGame
 */
public class AlphaBetaSolver {
	private double alpha = Double.NEGATIVE_INFINITY;
	private double beta = Double.POSITIVE_INFINITY;
	
	private ArrayList<MoveNode> moveTree;
	
	// get/set for alpha
	public double getAlpha() {
		return this.alpha;
	}
	public void setAlpha(double alphaIn) {
		this.alpha= alphaIn;
	}
	// get/set for beta
	public double getBeta() {
		return this.beta;
	}
	public void setBeta(double betaIn) {
		this.beta= betaIn;
	}
	// get/set for moveTree
	public ArrayList<MoveNode> getMoveTree() {
		return this.moveTree;
	}
	public void setMoveTree(ArrayList<MoveNode> moveTreeIn) {
		this.moveTree= moveTreeIn;
	}


}
