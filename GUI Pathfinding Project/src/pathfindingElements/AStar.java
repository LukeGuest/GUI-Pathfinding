package pathfindingElements;

import java.awt.Color;
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
		Thread thread = new Thread(() -> {
			
			this.startNode = startPos;
			this.endNode = endPos;
			//closedList.add(startPos);
			openList.add(startPos);
			
			while(!openList.isEmpty()) {
				
				//Get node from list with lowest F value
				Node currentNode = null;
				for(int i = 0; i < openList.size(); i++) {
					if(currentNode == null || openList.get(i).getF() < currentNode.getF()) {
						currentNode = openList.get(i);
					}
				}
				
				//Conditions depending on node type
				if(currentNode.checkStatus() == Status.END_NODE) {
					System.out.println("END NODE FOUND");
					break;
				}
				else if(currentNode.checkStatus() == Status.WALL) {
					System.out.println("WALL NODE FOUND");
					continue;
				}
				else {
					currentNode.setBackground(Color.blue);
				}
				
				openList.remove(currentNode);
				
				try {
					Thread.sleep(30);
				}
				catch(InterruptedException e) {
					
				}
				
				//Find neighbours of currentNode
				addNeighbours(currentNode);
				
				closedList.add(currentNode);
			}
		});
		
		thread.start();
	}
	
	/**
	 * Add neighbour nodes to 
	 * open list.
	 */
	private void addNeighbours(Node current) {
		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y <= 1; y++) {
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
					
					successor.setG(current.getG() + 1);
					successor.setH(distance(successor, endNode));
					System.out.println(current.getG());
					double updatedFValue = successor.getG() + successor.getH();
					
					/**
					 * If successor already stored in openList
					 * with a lower F value, skip this node.
					 */
					if(openList.contains(successor)) {
						
						int index = openList.indexOf(successor);
						if(updatedFValue > openList.get(index).getF()) {
							System.out.println("F Value greater than current");
							continue;
						}
						
					}
					if(closedList.contains(successor)) {
						if(closedList.get(closedList.indexOf(successor)).getF() < updatedFValue) {
							System.out.println("F Value greater than current in closed");
							continue;
						}
					}
					openList.add(successor);
				}
			}
		}
	}
	
	private double distance(Node currentNode, Node endNode) {
		return Math.max(Math.abs(currentNode.getX() - endNode.getX()), Math.abs(currentNode.getY() - endNode.getY()));
	}

}
