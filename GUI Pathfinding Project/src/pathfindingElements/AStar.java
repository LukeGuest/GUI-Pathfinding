package pathfindingElements;

import java.util.ArrayList;

public class AStar {

	private ArrayList<Grid> openList;
	private ArrayList<Grid> closedList;
	private ArrayList<Grid> barrierList;
	
	public AStar() {
		openList = new ArrayList<Grid>();
		closedList = new ArrayList<Grid>();
		barrierList = new ArrayList<Grid>();
	}

}
