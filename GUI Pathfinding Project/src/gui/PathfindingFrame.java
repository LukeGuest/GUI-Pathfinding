package gui;

import java.awt.Dimension;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;

import pathfindingElements.AStar;

public class PathfindingFrame extends JFrame {

	private static JFrame frame;
	private JPanel panel;
	
	private PathfindingGrid grid;
	private static AStar aStar;
	
	private static final int WINDOW_WIDTH = 1080;
	private static final int WINDOW_HEIGHT = 920;
	
	private static final int GRID_SIZE = 30;
	
	public PathfindingFrame() {
		//Creating new JFrame
		frame = new JFrame("Pathfinding GUI Application");
		frame.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		
		//What happens when frame closes
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setFocusable(false);		
		
		panel = new JPanel(new GridLayout(1,1));
		frame.add(panel);
		
		grid = new PathfindingGrid(GRID_SIZE, panel);
		aStar = new AStar(grid.getGridObject());
		
		jMenuSetup();
		
		frame.setVisible(true);
	}	
	
	/**
	 * All the JMenu setup - each tab and options.
	 */
	private void jMenuSetup() {
		JMenuBar menu = new JMenuBar();
		
		JMenu pathfindingOptions = new JMenu("Pathfinding Options");		
		JMenuItem aStarOption = new JMenuItem("A* Pathfinding");
		JMenuItem breadthFirst = new JMenuItem("Breadth First Search");
		
		aStarOption.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!grid.algoRunningCheck() && !aStar.algoRunningCheck()) {
					if(grid.getStartNode() == null || grid.getEndNode() == null) {
						JOptionPane.showMessageDialog(PathfindingFrame.getFrames()[0], "Start/End Node has not been set.");
					}
					else {
						aStar.findPath(grid.getStartNode(), grid.getEndNode());
						breadthFirst.setEnabled(false);
						aStarOption.setEnabled(false);
					}
				}
			}
		});
		
		breadthFirst.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!grid.algoRunningCheck() && !aStar.algoRunningCheck()) {
					if(grid.getStartNode() == null || grid.getEndNode() == null) {
						JOptionPane.showMessageDialog(PathfindingFrame.getFrames()[0], "Start/End Node has not been set.");
					}
					else {
						grid.breadthFirstSearch();
						breadthFirst.setEnabled(false);
						aStarOption.setEnabled(false);
					}
					
				}
			}
		});
		
		pathfindingOptions.add(aStarOption);
		pathfindingOptions.add(breadthFirst);
		
		JMenu fileOptions = new JMenu("File");
		
		JMenuItem about = new JMenuItem("About");
		JMenuItem resetGrid = new JMenuItem("Reset Grid");
		
		about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(PathfindingFrame.getFrames()[0], 
						"Hotkeys: \n"
						+ "S: Next left click places start node.\n"
						+ "E: Next left click places end node.\n"
						+ "Left Click: Invert current node status (Wall or Open).\n\n"
						+ "The algorithm speed can be adjusted in the 'Speed Options'. \n");
			}
		});
		
		resetGrid.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!grid.algoRunningCheck() && !aStar.algoRunningCheck()) {
					grid.resetGrid();
					breadthFirst.setEnabled(true);
					aStarOption.setEnabled(true);
				}
				else {
					JOptionPane.showMessageDialog(PathfindingFrame.getFrames()[0], "Search algorithm currently being ran!");
				}
			}
		});	
		
		fileOptions.add(about);
		fileOptions.add(resetGrid);
		
		JMenu speedOptions = new JMenu("Speed Options");
		JMenuItem slowSpeed = new JMenuItem("Slow Speed");
		JMenuItem mediumSpeed = new JMenuItem("Medium Speed");
		JMenuItem highSpeed = new JMenuItem("High Speed");
		
		slowSpeed.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				grid.setSearchSpeed(15);
				aStar.setSearchSpeed(15);
			}
		});
		
		mediumSpeed.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				grid.setSearchSpeed(10);
				aStar.setSearchSpeed(10);
			}
		});
		
		highSpeed.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				grid.setSearchSpeed(5);
				aStar.setSearchSpeed(5);
			}
		});
		
		speedOptions.add(slowSpeed);
		speedOptions.add(mediumSpeed);
		speedOptions.add(highSpeed);	
		
		menu.add(fileOptions);
		menu.add(pathfindingOptions);
		menu.add(speedOptions);
		
		frame.setJMenuBar(menu);
	}
	
	public static AStar aStarObj() {
		return aStar;
	}
}
