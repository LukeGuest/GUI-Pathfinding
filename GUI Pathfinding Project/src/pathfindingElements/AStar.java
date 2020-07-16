package pathfindingElements;

import java.util.ArrayList;

import pathfindingElements.Node.Status;

public class AStar {

	private ArrayList<Node> openList;
	private ArrayList<Node> closedList;
	private ArrayList<Node> pathList;
	
	private Node[][] grid;
	
	private Node startNode;
	private Node endNode;
	
	//private Node currentNode;
	
	private boolean useDiag;
	
	
	public AStar(Node[][] grid, boolean useDiag) {
		openList = new ArrayList<Node>();
		closedList = new ArrayList<Node>();
		pathList = new ArrayList<Node>();
		
		this.grid = grid;
		
		this.useDiag = useDiag;
	}
	
	public void findPath(Node startPos, Node endPos) {
		this.startNode = startPos;
		this.endNode = endPos;
		//closedList.add(startPos);
		openList.add(startPos);
		
		while(!openList.isEmpty()) {
			Node currentNode = null;
			for(int i = 0; i < openList.size(); i++) {
				if(currentNode == null || openList.get(i).getF() < currentNode.getF()) {
					currentNode = openList.get(i);
				}
			}
			openList.remove(currentNode);
			
			addNeighbours(currentNode);
		}
	}
	
	/**
	 * Add neighbour nodes to 
	 * open list.
	 */
	private void addNeighbours(Node current) {
		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y < 1; y++) {
				/*if(!useDiag && i != 0 && j != 0) {
					continue;
				}*/
				int row = current.getRow();
				int column = current.getColumn();
				
				/*
				 * if node is not 'current'
				 * if node is within boundaries of grid
				 * if node is 'walkable'
				 * if node not already checked
				 */
				if(row != 0 && column != 0
						&& row + x >= 0 && row + x < grid.length
						&& column + y >= 0	&& column + y < grid.length
						&& grid[row + x][column + y].checkStatus() == Status.WALKABLE
						&& !grid[row + x][column + y].Visited()) {
					Node successor = grid[current.getRow() + x][current.getColumn() + y];
					
					//ADD HERE OR WHEN CHECKING NEW NODE IN LIST?
					if(successor.checkStatus() == Status.END_NODE) {
						
					}
					successor.setG(current.getG() + 1);
					successor.setH(distance(successor, endNode));
					
					if(openList.contains(successor)) {
						int index = openList.indexOf(successor);
						if(openList.get(index).getF() < successor.getF())
						
					}
				}
				
				
			}
		}
	}
	
	private double distance(Node x, Node y) {
		//ADD HEURISTIC CALCULATION HERE
	}

}
