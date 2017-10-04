package experiment;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;


public class IntBoard {
	private BoardCell[][] gameBoard;
	public Map<BoardCell, Set<BoardCell>> adjSpaces;
	private int boardHeight = 4;
	private int boardWidth = 4;
	private BoardCell currentCell;
	private Set<BoardCell> targetSpaces;
	private Set<BoardCell> visited;
	
	
	public IntBoard() {
		super();
		gameBoard = new BoardCell[4][4]; //game board as a 2D Array
	}
	
	//For testing purposes
	public BoardCell getCell(int y, int x) {
		BoardCell space = new BoardCell(y,x);
		return space;
	}
	
	
	//called in constuctor?
	public void calcAdjacencies() {
		//Calculates the list of adjacent grid cell from a certain point, then stores them in a Set, then
		//puts them in adjSpaces map
	}
	public void calcTargets(BoardCell startSpace, int pathLength) {
		//Calculates the targets that are pathLength distance from the startCell. 
		//The list of targets will be stored in an instance variable.
	}
	public Set<BoardCell> getTargets() {
		//Returns the list of targets.
		return null;
	}
	public Set<BoardCell> getAdjList(BoardCell space) {
		//Returns the adjacency list for one cell
		return null;
	}
	
	
}
