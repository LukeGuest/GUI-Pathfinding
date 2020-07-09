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
	
	JButton buttonElement;
	Status nodeStatus;
	
	public Node() {
		super();
		setBackground(Color.LIGHT_GRAY);
		nodeStatus = Status.WALKABLE;
		
		
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
}
