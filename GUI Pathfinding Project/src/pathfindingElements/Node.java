package pathfindingElements;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import gui.PathfindingFrame;
import gui.PathfindingGrid;

/**
 * Node.java represents each individual unit
 * on the pathfinding grid.
 * It extends the JButton class, to handle the 
 * visual aspect of the square.
 * @author lukeg
 *
 */
public class Node extends JButton {
	
	public enum Status {
		WALKABLE, 
		WALL,
		START_NODE,
		END_NODE}
	
	private JButton buttonElement;
	private Status nodeStatus;
	
	private boolean visited;
	
	private int row, column;
	
	/**
	 * Used to find node previously 'attached' to when backtracking to find shortest path.
	 */
	private Node parentNode = null;
	
	/**
	 * g - movement cost from starting point to a given node.
	 * h - estimated cost to move from node to destination.
	 * f - g + h.
	 */
	private double g,h,f;
	
	public Node(int rowValue, int columnValue) {
		super();
		setBackground(Color.LIGHT_GRAY);
		this.nodeStatus = Status.WALKABLE;
		
		this.row = rowValue;
		this.column = columnValue;
		
		this.g = 0;
		this.h = 0;
		
	}
	
	public JButton getButton() {
		return buttonElement;
	}
	
	public void setStatus(Status statusValue) {
		nodeStatus = statusValue;
	}
	
	public Status checkStatus() {
		return nodeStatus;
	}
	
	public boolean Visited() {
		return visited;
	}
	
	public void setVisited(boolean visitedValue) {
		visited = visitedValue;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public void setG(double value) {
		if(this.g != value) {
			this.g = value;
			setF(this.g, this.h);
		}
		
	}
	public double getG() {
		return g;
	}
	
	public void setH(double value) {
		if(this.h != value) {
			this.h = value;
			setF(this.g, this.h);
		}
	}
	
	public double getH() {
		return h;
	}
	
	public double getF() {
		return f;
	}
	
	public Node getParentNode() {
		return parentNode;
	}
	
	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}
	
	private void setF(double g, double h) {
		f = g + h;
	}
	
	public void resetAStarValues() {
		h = 0;
		g = 0;
		f = 0;
	}
	
}
