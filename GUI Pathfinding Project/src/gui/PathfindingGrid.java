package gui;

import java.awt.Color;
import java.awt.Desktop.Action;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javax.swing.AbstractAction;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;


import pathfindingElements.Node;
import pathfindingElements.Node.Status;

public class PathfindingGrid {
	
	//2D array used to represent pathfinding grid
	private Node[][] grid;
	
	//Keeps track of dimensions of grid
	private int gridSize;
	
	//Start and end points for pathfinding
	private Node startNode = null;
	private Node endNode = null;
	
	//Used with mouseListener to check if key has been pressed
	private boolean sKeyPressed = false;
	private boolean dKeyPressed = false;
	
	private int searchSpeed = 10;
	
	private boolean searchAlgoRunning;
	
	public PathfindingGrid() {

	}

	/**
	 * Constructor taking size as an element
	 * @param size
	 * @param panel
	 */
	public PathfindingGrid(int size, JPanel panel) {
		gridSize = size;
		grid = new Node[size][size];
		
		GridLayout gridLayout = new GridLayout(size, size);
		JPanel gridPanel = new JPanel(gridLayout);
		
		gridPanel.setFocusable(false);
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				Node node = new Node(i, j);
				
				grid[i][j] = node;
				gridPanel.add(node);
				
				keyBindSetup(node);
				
				grid[i][j].addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						Node nodeClicked = (Node)e.getSource();
						
						//Only allow wall placement while no algorithm is running
						if(!searchAlgoRunning) {
							if(SwingUtilities.isLeftMouseButton(e)) {
								if(startNode == null && sKeyPressed) {
									nodeClicked.setBackground(Color.green);
									nodeClicked.setStatus(Status.START_NODE);
									startNode = nodeClicked;
								}
								else if(endNode == null && dKeyPressed) {
									nodeClicked.setBackground(Color.red);
									nodeClicked.setStatus(Status.END_NODE);
									endNode = nodeClicked;
								}
								else {
									if(nodeClicked.checkStatus() != Status.START_NODE && nodeClicked.checkStatus() != Status.END_NODE) {
										if(nodeClicked.checkStatus() == Status.WALL) {
											nodeClicked.setStatus(Status.WALKABLE);
											nodeClicked.setBackground(Color.LIGHT_GRAY);
										}
										else {
											nodeClicked.setBackground(Color.black);
											nodeClicked.setStatus(Status.WALL);
										}
										
										//APPEND BUTTON/NODE TO PATHFINDING WALLS LIST
									}
								}
							}
						}					
					}
					
					public void mouseEntered(MouseEvent e) {
						if(!searchAlgoRunning) {
							if(SwingUtilities.isLeftMouseButton(e)) {
								Node nodeClicked = (Node)e.getSource();
								
								nodeClicked.setBackground(Color.black);
								nodeClicked.setStatus(Status.WALL);
							}
						}
					}
				});
				
			}
		}
		
		panel.add(gridPanel);
	}
	
	/**
	 * Function called to setup KeyBinds.
	 * Used for setting Start and End nodes.
	 */
	private void keyBindSetup(Node node) {
		node.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "S");
		node.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "D");
		
		AbstractAction sKeyAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(startNode == null) {
					sKeyPressed = true;
				}
				
			}
		};
		AbstractAction dKeyAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(endNode == null) {
					dKeyPressed = true;
				}
			}
		};
		
		node.getActionMap().put("S", sKeyAction);
		node.getActionMap().put("D", dKeyAction);
	}
	
	public void resetGrid() {
		startNode = null;
		endNode = null;
		
		sKeyPressed = false;
		dKeyPressed = false;
		
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid.length; j++) {
				grid[i][j].setBackground(Color.LIGHT_GRAY);
				grid[i][j].setStatus(Status.WALKABLE);
				grid[i][j].setVisited(false);
			}
		}
	}
	
	public void setSearchSpeed(int speed) {
		searchSpeed = speed;
	}
	
	public void breadthFirstSearch() {
		Thread thread = new Thread(() -> {
			searchAlgoRunning = true;
			
			//Storing all nodes to be 'visited'
			Queue<Node> nodeQueue = new LinkedList<Node>();
			
			//Add starting position to queue if not null
			if(startNode != null) {
				nodeQueue.add(startNode);
			}
			else {
				JOptionPane.showMessageDialog(PathfindingFrame.getFrames()[0], "Start Node has not been set.");
			}
			
			
			while(nodeQueue.size() != 0) {
				//If no end node set, break the loop before it begins
				if(endNode == null) {
					JOptionPane.showMessageDialog(PathfindingFrame.getFrames()[0], "End Node has not been set.");
					break;
				}
				
				//Get node from front of queue
				Node currentNode = nodeQueue.remove();
				
				//Stop loop once end node found
				if(currentNode.checkStatus() == Status.END_NODE) {
					currentNode.setVisited(true);
					gridColourChange(true);
					break;
				}
				//Don't traverse through wall nodes
				else if(currentNode.checkStatus() == Status.WALL) {
					currentNode.setVisited(true);
					continue;
				}
				
				//Temp variables to store current node's row and column
				int row = currentNode.getRow();
				int column = currentNode.getColumn();
				
				//Stopping thread for specific time set (in milliseconds)
				try {
					Thread.sleep(searchSpeed);
				}
				catch (InterruptedException e){
					
				}
				
				//Don't change colour of start node
				if(currentNode.checkStatus() != Status.START_NODE) {
					currentNode.setBackground(Color.blue);
				}				
				
				//Ignore any nodes added to list which have been traversed
				if(currentNode.Visited()) {
					continue;
				}
				else {
					currentNode.setVisited(true);
				}
				
				//Append new nodes to the queue, as long as they fall within the grid bounds
				//Node Left
				if(column -1 >= 0) {
					nodeQueue.add(grid[row][column - 1]);
				}
				//Node Right
				if(column + 1 < gridSize) {
					nodeQueue.add(grid[row][column + 1]);
				}
				//Node Above
				if(row - 1 >= 0) {
					nodeQueue.add(grid[row - 1][column]);
				}
				//Node Below
				if(row + 1 < gridSize) {
					nodeQueue.add(grid[row + 1][column]);
				}
			}	
			
			//When the end node can't be found
			if(endNode != null && !endNode.Visited()) {
				gridColourChange(false);
				JOptionPane.showMessageDialog(PathfindingFrame.getFrames()[0], "No clear path to end node");
			}
			
			//Reset boolean so walls can be added
			searchAlgoRunning = false;
		});
		
		thread.start();
	}
	
	/**
	 * Changes colour of traversed nodes, depending on whether parameter set to true or false
	 * @param found
	 */
	private void gridColourChange(boolean found) {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid.length; j++) {
				if(grid[i][j].checkStatus() != Status.WALL && grid[i][j].checkStatus() != Status.START_NODE 
						&& grid[i][j].checkStatus() != Status.END_NODE && grid[i][j].Visited()) {
					if(found) {
						grid[i][j].setBackground(Color.green.darker());
					}
					else {
						grid[i][j].setBackground(Color.red.darker());
					}
					 
				}
			}
		}
	}
	
	public Node[][] getGridObject(){
		return grid;
	}
	public Node getStartNode() {
		return startNode;
	}
	public Node getEndNode() {
		return endNode;
	}
}