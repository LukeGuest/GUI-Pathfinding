package pathfindingElements;

import java.util.ArrayList;

public class AStar {

	private ArrayList<Node> openList;
	private ArrayList<Node> closedList;
	private ArrayList<Node> barrierList;
	
	public AStar() {
		openList = new ArrayList<Node>();
		closedList = new ArrayList<Node>();
		barrierList = new ArrayList<Node>();
	}

}
