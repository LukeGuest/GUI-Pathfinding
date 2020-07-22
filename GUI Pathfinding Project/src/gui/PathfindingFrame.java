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
	private AStar aStar;
	
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
		aStar = new AStar(grid.getGridObject(), true);
		
		jMenuSetup();
		
		frame.setVisible(true);
	}	
	
	private void jMenuSetup() {
		JMenuBar menu = new JMenuBar();
		
		JMenu fileOptions = new JMenu("File");
		JMenuItem resetGrid = new JMenuItem("Reset Grid");
		
		resetGrid.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				grid.resetGrid();
				System.out.println("Reset Grid");
			}
		});
		
		fileOptions.add(resetGrid);
		
		JMenu pathfindingOptions = new JMenu("Pathfinding Options");
		JMenuItem aStarOption = new JMenuItem("A* Pathfinding");
		JMenuItem breadthFirst = new JMenuItem("Breadth First Search");
		JMenuItem depthFirst = new JMenuItem("Depth First Search");
		
		aStarOption.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				aStar.findPath(grid.getStartNode(), grid.getEndNode());
			}
		});
		
		breadthFirst.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				grid.breadthFirstSearch();
			}
		});
		
		depthFirst.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//grid.depthFirstSearch();
			}
		});
		
		pathfindingOptions.add(aStarOption);
		pathfindingOptions.add(breadthFirst);
		pathfindingOptions.add(depthFirst);
		
		JMenu speedOptions = new JMenu("Speed Options");
		JMenuItem slowSpeed = new JMenuItem("Slow Speed");
		JMenuItem mediumSpeed = new JMenuItem("Medium Speed");
		JMenuItem highSpeed = new JMenuItem("High Speed");
		
		slowSpeed.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				grid.setSearchSpeed(15);
			}
		});
		
		mediumSpeed.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				grid.setSearchSpeed(10);
			}
		});
		
		highSpeed.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				grid.setSearchSpeed(5);
			}
		});
		
		speedOptions.add(slowSpeed);
		speedOptions.add(mediumSpeed);
		speedOptions.add(highSpeed);
		
		JMenu about = new JMenu("About");
		
		menu.add(fileOptions);
		menu.add(pathfindingOptions);
		menu.add(speedOptions);
		menu.add(about);
		
		frame.setJMenuBar(menu);
	}
}
