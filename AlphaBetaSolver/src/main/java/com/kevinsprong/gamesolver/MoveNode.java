package com.kevinsprong.gamesolver;

import java.util.ArrayList;

/*
 * Class to hold move tree over which to search
 */
public class MoveNode {
	// fields
	private int nodeId = 0;
	private String moveName;
	private boolean nodeVisited = false;
	private double alpha = Double.NEGATIVE_INFINITY;
	private double beta = Double.POSITIVE_INFINITY;
	private GameState nodeGameState;
	private int nodeDepth;
	private ArrayList<MoveNode> nodeChildren; // array list of children nodes

	// constructor
	public MoveNode(GameState nodeGameState, int nodeDepth) {
		this.setNodeGameState(nodeGameState);
		this.setNodeDepth(nodeDepth);
		this.setNodeChildren(new ArrayList<MoveNode>());
		this.setMoveName(null);
	}
	public MoveNode(GameState nodeGameState, int nodeDepth, String moveName) {
		this.setNodeGameState(nodeGameState);
		this.setNodeDepth(nodeDepth);
		this.setNodeChildren(new ArrayList<MoveNode>());
		this.setMoveName(moveName);
	}

	// methods
	// get/set for nodeId
	public int getNodeId() {
		return this.nodeId;
	}
	public void setNodeId(int nodeIdIn) {
		this.nodeId = nodeIdIn;
	}
	// get/set for moveName
	public String getMoveName() {
		return this.moveName;
	}
	public void setMoveName(String moveNameIn) {
		this.moveName = moveNameIn;
	}
	// get/set for nodeVisited
	public boolean getNodeVisited() {
		return this.nodeVisited;
	}
	public void setNodeVisited(boolean nodeVisitedIn) {
		this.nodeVisited = nodeVisitedIn;
	}
	// get/set for alpha
	public double getAlpha() {
		return this.alpha;
	}
	public void setAlpha(double alphaIn) {
		this.alpha = alphaIn;
	}
	// get/set for beta
	public double getBeta() {
		return this.beta;
	}
	public void setBeta(double betaIn) {
		this.beta = betaIn;
	}
	// get/set for nodeGameState
	public GameState getNodeGameState() {
		return this.nodeGameState;
	}
	public void setNodeGameState(GameState nodeGameStateIn) {
		this.nodeGameState = nodeGameStateIn;
	}
	// get/set for nodeDepth
	public int getNodeDepth() {
		return this.nodeDepth;
	}
	public void setNodeDepth(int nodeDepthIn) {
		this.nodeDepth = nodeDepthIn;
	}
	// get/set for nodeChildren
	public ArrayList<MoveNode> getNodeChildren() {
		return this.nodeChildren;
	}
	public void setNodeChildren(ArrayList<MoveNode> nodeChildrenIn) {
		this.nodeChildren = nodeChildrenIn;
	}

}
