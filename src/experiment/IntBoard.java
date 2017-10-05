package experiment;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;


public class IntBoard {
	private BoardCell[][] gameBoard;
	public Map<BoardCell, Set<BoardCell>> adjSpaces = new HashMap<BoardCell, Set<BoardCell>>();
	private int boardHeight = 4;
	private int boardWidth = 4;
	private Set<BoardCell> targetSpaces = new HashSet<BoardCell>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	
	
	public IntBoard() {
		super();
		gameBoard = new BoardCell[boardHeight][boardWidth]; //game board as a 2D Array
		this.calcAdjacencies();
	}
	
	//For testing purposes
	public BoardCell getCell(int y, int x) {
		BoardCell space = gameBoard[y][x];
		return space;
	}

	public void calcAdjacencies() {
		//Calculates the list of adjacent grid cell from a certain point, then stores them in a Set, then
		//puts them in adjSpaces map
		for (int y = 0; y < boardWidth; y++) {
			for (int x = 0; x < boardHeight; x++) {
				//BoardCell center = new BoardCell (y,x);
				Set<BoardCell> adjSet = new HashSet<BoardCell>();
				if (y == 0) { //left column
					if (x == 0) {//if space is on the top left corner
						BoardCell right = new BoardCell (y+1,x);
						BoardCell down = new BoardCell (y, x+1);
						
						adjSet.add(down);
						adjSet.add(right);
					}
					else if (x == boardHeight) { //if space is bottom left corner
						BoardCell up = new BoardCell (y, x-1);
						BoardCell right = new BoardCell (y+1,x);

						adjSet.add(up);
						adjSet.add(right);
						
					}
					else { //rest of left column
						BoardCell up = new BoardCell (y, x-1);
						BoardCell right = new BoardCell (y+1,x);
						BoardCell down = new BoardCell (y, x+1);
						
						adjSet.add(up);
						adjSet.add(right);
						adjSet.add(down);
					}
				}

				else if (y == boardWidth) { //right column
					if (x == 0) {//if space is on the top right corner
						BoardCell left = new BoardCell (y-1,x);						
						BoardCell down = new BoardCell (y+1, x);
						
						adjSet.add(down);
						adjSet.add(left);						
					}
					else if (x == boardHeight) { //if space is bottom right corner
						BoardCell left = new BoardCell (y-1,x);
						BoardCell up = new BoardCell (y, x-1);		
						
						adjSet.add(up);
						adjSet.add(left);
					}
					else { //rest of right column
						BoardCell down = new BoardCell (y, x+1);						
						BoardCell left = new BoardCell (y-1,x);
						BoardCell up = new BoardCell (y, x-1);		
						
						adjSet.add(up);
						adjSet.add(left);						
						adjSet.add(down);
					}
				}
				else if (x == 0 && y != 0 && y != boardWidth) { //top row except corners
					BoardCell left = new BoardCell (y-1,x);
					BoardCell right = new BoardCell (y+1,x);
					BoardCell down = new BoardCell (y, x+1);					

					adjSet.add(left);
					adjSet.add(down);
					adjSet.add(right);					
				}
				else if (x == boardHeight && y != 0 && y != boardWidth) { //bottom row except corners
					BoardCell up = new BoardCell (y, x-1);
					BoardCell left = new BoardCell (y-1,x);
					BoardCell right = new BoardCell (y+1,x);
					
					adjSet.add(up);
					adjSet.add(left);
					adjSet.add(right);
				}
				else { //rest of the board
					BoardCell left = new BoardCell (y-1,x);
					BoardCell right = new BoardCell (y+1,x);
					BoardCell down = new BoardCell (y, x+1);
					BoardCell up = new BoardCell (y, x-1);

					adjSet.add(up);
					adjSet.add(left);
					adjSet.add(down);
					adjSet.add(right);
				}
				this.adjSpaces.put(getCell(y,x), adjSet);
			}
		}
	}
	
	public Set<BoardCell> getAdjList(BoardCell space) {	//Returns the adjacency list for one cell

		Set<BoardCell> retn = new HashSet<BoardCell>();
		System.out.println(adjSpaces);
		System.out.println(adjSpaces.get(space));
		System.out.println(adjSpaces.get(space).toArray());
		retn = adjSpaces.get(space);
		return retn;
	}
	
	public void calcTargets(BoardCell startSpace, int pathLength) {
		//Calculates the targets that are pathLength distance from the startCell. 
		//The list of targets will be stored in an instance variable.
		if (pathLength == 0) {return;}
		this.targetSpaces = new HashSet<BoardCell>();
		visited.add(startSpace); //add current space to the visited list
			for (BoardCell adjSpace : adjSpaces.get(startSpace)) {
				if (!visited.contains(adjSpace)) {
					visited.add(adjSpace);
					if (pathLength == 1) {
						targetSpaces.add(adjSpace);
					}
					else {
						calcTargets(adjSpace, pathLength-1);
					}
				}
			}
			visited.remove(startSpace);
		}
	
	public Set<BoardCell> getTargets() {//Returns the list of targets.
		return targetSpaces;
	}

	
	
}
