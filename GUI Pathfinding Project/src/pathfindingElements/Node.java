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
	
	public Node(int rowValue, int columnValue) {
		super();
		setBackground(Color.LIGHT_GRAY);
		nodeStatus = Status.WALKABLE;
		
		row = rowValue;
		column = columnValue;
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
}
