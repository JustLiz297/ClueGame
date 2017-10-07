/*
 * Authors: Elizabeth (Liz) Boyle, Annelyse Baker
 * Description: IntBoard class, game board that handles the functions of the game board, such as where a piece can move
 */

package experiment;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;


public class IntBoard {
	private int boardHeight = 4;
	private int boardWidth = 4;
	private BoardCell[][] gameBoard = new BoardCell[boardWidth][boardHeight];
	public Map<BoardCell, Set<BoardCell>> adjSpaces = new HashMap<BoardCell, Set<BoardCell>>();
	private Set<BoardCell> targetSpaces;
	private Set<BoardCell> visited;
	
	
	public IntBoard() {
		super();
		for (int i = 0; i < boardWidth; i++) {
			for (int j = 0; j < boardHeight; j++) {
				gameBoard[i][j] = new BoardCell(i,j); //fills game board with boardCells
			}
		}
		this.calcAdjacencies();
	}
	
	public BoardCell getCell(int y, int x) {
		return gameBoard[y][x];
	}

	public void calcAdjacencies() {
		//Calculates the list of adjacent grid cell from a certain point, then stores them in a Set, then
		//puts them in adjSpaces map
		for (int y = 0; y < boardWidth; y++) {
			for (int x = 0; x < boardHeight; x++) {
				Set<BoardCell> adjSet = new HashSet<BoardCell>();
				if (y == 0) { //left column
					if (x == 0) {//if space is on the top left corner
						BoardCell right = new BoardCell (y+1,x);
						BoardCell down = new BoardCell (y, x+1);
						
						adjSet.add(down);
						adjSet.add(right);
					}
					else if (x == boardHeight-1) { //if space is bottom left corner
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

				else if (y == boardWidth-1) { //right column
					if (x == 0) {//if space is on the top right corner
						BoardCell left = new BoardCell (y-1,x);						
						BoardCell down = new BoardCell (y, x+1);
						
						adjSet.add(down);
						adjSet.add(left);						
					}
					else if (x == boardHeight-1) { //if space is bottom right corner
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
				else if (x == 0 && y != 0 && y != boardWidth-1) { //top row except corners
					BoardCell left = new BoardCell (y-1,x);
					BoardCell right = new BoardCell (y+1,x);
					BoardCell down = new BoardCell (y, x+1);					

					adjSet.add(left);
					adjSet.add(down);
					adjSet.add(right);					
				}
				else if (x == boardHeight-1 && y != 0 && y != boardWidth-1) { //bottom row except corners
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

	/* Part 1:
	 * public void calcAdjacencies() {
	 * 
	 * }
	 */
	
	public Set<BoardCell> getAdjList(BoardCell space) {	//Returns the adjacency list for one cell
		Set<BoardCell> retn = new HashSet<BoardCell>();
		retn = adjSpaces.get(space);
		return retn;
	}
	
	/* Part 1:
	 * public Set<BoardCell> getAdjList(BoardCell space) {
	 * 	return null;
	 * }
	 */
	
	public void calcTargets(BoardCell startSpace, int pathLength) {
		//Calculates the targets that are pathLength distance from the startCell. 
		//The list of targets will be stored in an instance variable.
		visited = new HashSet<BoardCell>();
		targetSpaces = new HashSet<BoardCell>();

		visited.add(startSpace);//add current space to the visited list
		findAllTargets(startSpace, pathLength);
		targetSpaces.remove(startSpace);
		}
	
	public void findAllTargets(BoardCell thisSpace, int pathLength) {
		if (pathLength == 0) {		
			visited.remove(thisSpace);
			return;
		}
		Set<BoardCell> temp = new HashSet<BoardCell>(); //to transfer the set in the map to a readable set, because pointers.
		for (BoardCell adjSpace : adjSpaces.get(thisSpace)) {
			temp.add(getCell(adjSpace.getyCoord(), adjSpace.getxCoord()));
		}
		for (BoardCell adjSpace : temp) {
			if (!visited.contains(adjSpace)) {
				visited.add(adjSpace);
				if (pathLength == 1) {
					targetSpaces.add(adjSpace);
				}
				else {
					visited.remove(thisSpace);
					findAllTargets(adjSpace, pathLength-1);
				}
			}
			visited.remove(adjSpace);
		}

	}
		

	
	/* Part 1:
	 * public void calcTargets(BoardCell startSpace, int pathLength) {
	 * 	
	 * }
	 */
	
	public Set<BoardCell> getTargets() {//Returns the list of targets.
		return targetSpaces;
	}

	/* Part 1:
	 * public Set<BoardCell> getTargets() {
	 * 		return null;
	 *	}
	 * 
	 */
	
	
}
