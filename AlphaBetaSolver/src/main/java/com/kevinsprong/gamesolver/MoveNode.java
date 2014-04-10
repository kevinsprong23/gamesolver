package com.kevinsprong.gamesolver;

import java.util.ArrayList;

/*
 * Class to hold move tree over which to search
 */
public class MoveNode {
	// fields
	private int nodeId;
	private GameState nodeGameState;
	private int nodeDepth;
	private int nodeParent;
	private ArrayList<Integer> nodeChildren; // map to Id of other move nodes
	
	
	// methods
	// get/set for nodeId
	public int getNodeId() {
		return this.nodeId;
	}
	public void setNodeId(int nodeIdIn) {
		this.nodeId= nodeIdIn;
	}
	// get/set for nodeGameState
	public GameState getNodeGameState() {
		return this.nodeGameState;
	}
	public void setNodeGameState(GameState nodeGameStateIn) {
		this.nodeGameState= nodeGameStateIn;
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
