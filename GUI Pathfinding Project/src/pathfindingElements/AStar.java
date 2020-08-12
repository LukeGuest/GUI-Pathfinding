package pathfindingElements;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import gui.PathfindingFrame;
import pathfindingElements.Node.Status;

public class AStar {

	private ArrayList<Node> openList;
	private ArrayList<Node> closedList;
	private ArrayList<Node> pathList;
	
	private Node[][] grid;
	private Node endNode;
	
	private boolean pathFound;
	
	private boolean isRunning;
	
	private int searchSpeed = 10;
	
	public AStar(Node[][] grid) {
		openList = new ArrayList<Node>();
		closedList = new ArrayList<Node>();
		pathList = new ArrayList<Node>();
		
		this.grid = grid;
	}
	
	public void findPath(Node startPos, Node endPos) {
		Thread thread = new Thread(() -> {
			isRunning = true;
			pathFound = false;
			
			openList.clear();
			closedList.clear();
			pathList.clear();
			
			this.endNode = endPos;
			
			openList.add(startPos);
			
			while(!openList.isEmpty()) {
				
				//Get node from list with lowest F value
				Node currentNode = null;
				for(int i = 0; i < openList.size(); i++) {
					if(currentNode == null || openList.get(i).getF() < currentNode.getF()) {
						currentNode = openList.get(i);
					}
				}
				
				openList.remove(currentNode);
				closedList.add(currentNode);
				
				//Conditions depending on node type
				if(currentNode.checkStatus() == Status.END_NODE) {
					currentNode.setBackground(Color.red);
					pathFound = true;
					break;
				}
				else if(currentNode.checkStatus() != Status.END_NODE && currentNode.checkStatus() != Status.START_NODE){					
					currentNode.setBackground(Color.pink.darker());
						currentNode.setVisited(true);
				}
				
				//Find neighbours of currentNode
				if(currentNode.checkStatus() != Status.WALL || currentNode.checkStatus() != Status.END_NODE) {
					
					addNeighbours(currentNode);
				}
				
				try {
					Thread.sleep(searchSpeed);
				}
				catch(InterruptedException e) {
					
				}			
			}
			
			//Once pathfinding has finished.....
			if(pathFound) {
				joinPath();
				for(int i = 0; i < pathList.size(); i++) {
					if(pathList.get(i).checkStatus() != Status.START_NODE) {
						pathList.get(i).setBackground(Color.blue);
					}
				}
				JOptionPane.showMessageDialog(PathfindingFrame.getFrames()[0], "Path found");
			}
			else {
				JOptionPane.showMessageDialog(PathfindingFrame.getFrames()[0], "No Path found");
			}
			isRunning = false;
		});
		
		thread.start();
	}
	
	/**
	 * Add neighbour nodes to open list.
	 * Check whether to skip neighbours 
	 * if other side of wall.
	 */
	private void addNeighbours(Node current) {
		int row = current.getRow();
		int column = current.getColumn();
		
		boolean skipSouthEast = false;
		boolean skipNorthEast = false;
		boolean skipNorthWest = false;
		boolean skipSouthWest = false;
		
		skipSouthEast = checkDiag(current, 1, 1);
		skipNorthEast = checkDiag(current, 1, -1);
		skipNorthWest = checkDiag(current, -1, -1);
		skipSouthWest = checkDiag(current, -1, 1);

		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y <= 1; y++) {
				
				if(skipSouthEast &&  (x == 1 && y == 1)){
					continue;
				}
				if(skipNorthEast && (x == 1 && y == -1)) {
					continue;
				}
				if(skipNorthWest && (x == -1 && y == -1)) {
					continue;
				}
				if(skipSouthWest && (x == -1 && y == 1)) {
					continue;
				}

				/*
				 * if node is not 'current'
				 * if node is within boundaries of grid
				 * if node is 'walkable'
				 * if node not already checked
				 */
				if(row + x >= 0 && row + x < grid.length
						&& column + y >= 0	&& column + y < grid.length
						&& (grid[row + x][column + y].checkStatus() == Status.WALKABLE || grid[row + x][column + y].checkStatus() == Status.END_NODE)
						&& !grid[row + x][column + y].Visited()) {
					
					Node successor = grid[row + x][column + y];
					
					successor.setG(current.getG() + 1);
					successor.setH(diagDistance(successor, endNode));
					
					successor.setParentNode(current);
					
					double updatedFValue = successor.getG() + successor.getH();
					
					/**
					 * If successor already stored in openList
					 * with a lower F value, skip this node.
					 */
					if(openList.contains(successor)) {
						
						int index = openList.indexOf(successor);
						if(updatedFValue > openList.get(index).getF()) {
							successor.setVisited(true);
							continue;
						}
						
					}
					else if(closedList.contains(successor)) {
						if(closedList.get(closedList.indexOf(successor)).getF() < updatedFValue) {
							continue;
						}
					}
					openList.add(successor);
					if(successor.checkStatus() != Status.WALL && successor.checkStatus() != Status.START_NODE) {
						successor.setBackground(Color.green.darker());
					}
					successor.setVisited(true);
				}
			}
		}
	}
	
	//Evaluate distance between two nodes
	private double diagDistance(Node currentNode, Node endNode) {
		return Math.hypot(currentNode.getRow() - endNode.getRow(), currentNode.getColumn() - endNode.getColumn());
	}
	
	/**
	 * Checks to see if need to skip neighbours
	 * by checking if current node is next to a wall.
	 * @param current
	 * @param rowOffset
	 * @param columnOffset
	 * @return
	 */
	private boolean checkDiag(Node current, int rowOffset, int columnOffset) {
		if(current.getRow() + rowOffset < 0 || current.getColumn() + columnOffset < 0 || current.getRow() + rowOffset >= grid.length || current.getColumn() + columnOffset >= grid.length) {
			return false;
		}
		if(grid[current.getRow()][current.getColumn() + columnOffset].checkStatus() == Status.WALL && grid[current.getRow() + rowOffset][current.getColumn()].checkStatus() == Status.WALL) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Backtrack to get final path.
	 * Add each node to pathList.
	 */
	private void joinPath() {
		Node currentParent = endNode.getParentNode();
		
		while(currentParent != null) {
			pathList.add(currentParent);
			currentParent = currentParent.getParentNode();
		}
	}	
	
	public void setSearchSpeed(int speed) {
		searchSpeed = speed;
	}
	
	public boolean algoRunningCheck() {
		return isRunning;
	}
	
	public void resetAlgo() {
		isRunning = false;
	}
}
