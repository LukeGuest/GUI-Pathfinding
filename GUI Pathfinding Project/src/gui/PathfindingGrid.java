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

import javax.swing.AbstractAction;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;


import pathfindingElements.Node;
import pathfindingElements.Node.Status;

public class PathfindingGrid {
	
	//2D array used to represent pathfinding grid
	private Node[][] grid;
	
	//Start and end points for pathfinding
	private Node startNode = null;
	private Node endNode = null;
	
	//Used with mouseListener to check if key has been pressed
	private boolean sKeyPressed = false;
	private boolean dKeyPressed = false;
	
	public PathfindingGrid() {

	}

	/**
	 * Constructor taking size as an element
	 * @param size
	 * @param panel
	 */
	public PathfindingGrid(int size, JPanel panel) {
		grid = new Node[size][size];
		
		GridLayout gridLayout = new GridLayout(size, size);
		JPanel gridPanel = new JPanel(gridLayout);
		
		gridPanel.setFocusable(false);
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				Node node = new Node();
				
				grid[i][j] = node;
				gridPanel.add(node);
				
				keyBindSetup(node);
				
				grid[i][j].addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						Node nodeClicked = (Node)e.getSource();
						
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
					
					public void mouseEntered(MouseEvent e) {
						if(SwingUtilities.isLeftMouseButton(e)) {
							Node nodeClicked = (Node)e.getSource();
							
							nodeClicked.setBackground(Color.black);
							nodeClicked.setStatus(Status.WALL);
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
	
	public void resetGrid(int size) {
		startNode = null;
		endNode = null;
		
		sKeyPressed = false;
		dKeyPressed = false;
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				grid[i][j].setBackground(Color.LIGHT_GRAY);
				grid[i][j].setStatus(Status.WALKABLE);
			}
		}
		
	}
}