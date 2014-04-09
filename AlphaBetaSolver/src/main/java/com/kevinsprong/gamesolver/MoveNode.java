package com.kevinsprong.gamesolver;

import java.util.ArrayList;

/*
 * Class to hold move tree over which to search
 */
public class MoveNode {
	// fields
	private int nodeId;
	private double gameEval;
	private int nodeDepth;
	private int nodeParent;
	private ArrayList<Integer> nodeChildren; // map to Id
	
	// methods

	// get/set for nodeId
	public int getNodeId() {
		return this.nodeId;
	}
	public void setNodeId(int nodeIdIn) {
		this.nodeId= nodeIdIn;
	}
	// get/set for gameEval
	public double getGameEval() {
		return this.gameEval;
	}
	public void setGameEval(double gameEvalIn) {
		this.gameEval= gameEvalIn;
	}
	// get/set for nodeDepth
	public int getNodeDepth() {
		return this.nodeDepth;
	}
	public void setNodeDepth(int nodeDepthIn) {
		this.nodeDepth= nodeDepthIn;
	}
	// get/set for nodeParent
	public int getNodeParent() {
		return this.nodeParent;
	}
	public void setNodeParent(int nodeParentIn) {
		this.nodeParent= nodeParentIn;
	}
	// get/set for nodeChildren
	public ArrayList<Integer> getNodeChildren() {
		return this.nodeChildren;
	}
	public void setNodeChildren(ArrayList<Integer> nodeChildrenIn) {
		this.nodeChildren= nodeChildrenIn;
	}
	
}
