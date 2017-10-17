/*
 * Authors: Elizabeth (Liz) Boyle, Annelyse Baker
 * Description: Board Class that handles the management of the Game board
 */


package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import clueGame.BoardCell;
import experiment.IntBoard;

public class Board{
	private int numRows;
	private int numColumns;
	public final static int MAX_BOARD_SIZE = 51;
	private BoardCell[][] board;
	private Map<Character, String> legend = new HashMap<Character, String>();
	private Map<BoardCell, Set<BoardCell>> adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private String boardConfigFile;
	private String roomConfigFile;
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() throws BadConfigFormatException {
		this.loadRoomConfig(); //always call legend config first
		this.loadBoardConfig();
		this.calcAdjacencies();

		
	}
	public void setConfigFiles(String layoutfile, String legendfile) {
		boardConfigFile = layoutfile;
		roomConfigFile = legendfile;
	}

	public void loadRoomConfig() throws BadConfigFormatException{
		try {
			FileReader roomReader = new FileReader(roomConfigFile);
			Scanner in = new Scanner(roomReader);
			while (in.hasNext()) {
				String[] entry = in.nextLine().split(", ");
				//Check if the room type is valid in the file
				if (entry[2].equals("Card")  || entry[2].equals("Other")) {
					legend.put(entry[0].charAt(0), entry[1]);
				}
				else{ 
					throw new BadConfigFormatException();
				}
				
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		
	}
	
	public void loadBoardConfig() throws BadConfigFormatException{
		try {
			
			//Reads in file, counts rows and columns, and checks if all the columns are equal
			FileReader boardReader = new FileReader(boardConfigFile);
			Scanner in = new Scanner(boardReader);
			int rows = 1;
			numColumns = in.nextLine().split(",").length;
			while (in.hasNext()) {
				String cycling = in.nextLine();
				if (cycling.split(",").length != this.numColumns) {
					throw new BadConfigFormatException();
				}
				rows++;
			}
			numRows = rows;
			in.close();
			in = null;
			//Initializes board with BoardCells per the read in dimensions
			board = new BoardCell[numRows][numColumns];
			for (int i = 0; i < numRows; i++) {
				for (int j = 0; j < numColumns; j++) {
					board[i][j] = new BoardCell(i,j); //fills game board with boardCells
				}
			}
			
			//Resets the filereader to read in the characters of the cells, sets the door directions 
			FileReader entryFile = new FileReader(boardConfigFile);
			Scanner inEntry = new Scanner(entryFile);
			for (int row = 0; row < numRows; row++) {
				String entry = inEntry.nextLine();
				String[] charEntry = entry.split(",");
				//System.out.println(entry);
				for (int colm = 0; colm < numColumns; colm++) {
					if (!legend.containsKey(charEntry[colm].charAt(0))) {
						throw new BadConfigFormatException();
					}
					else {
						board[row][colm].setInitial(charEntry[colm].charAt(0));
						if (charEntry[colm].length() == 2) {
							if (charEntry[colm].charAt(1) == 'N') {
								board[row][colm].setDoorDirection(DoorDirection.NONE);
							}
							else if (charEntry[colm].charAt(1) == 'U') {
								board[row][colm].setDoorDirection(DoorDirection.UP);							
							}
							else if (charEntry[colm].charAt(1) == 'D') {
								board[row][colm].setDoorDirection(DoorDirection.DOWN);							
							}
							else if (charEntry[colm].charAt(1) == 'L') {
								board[row][colm].setDoorDirection(DoorDirection.LEFT);
							}
							else if (charEntry[colm].charAt(1) == 'R') {
								board[row][colm].setDoorDirection(DoorDirection.RIGHT);							
							}
						}
						else {
							board[row][colm].setDoorDirection(DoorDirection.NONE);						
						}
					}
				}
			}
			inEntry.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
	}
	
	public void calcAdjacencies() {
		//Calculates the list of adjacent grid cell from a certain point, then stores them in a Set, then
		//puts them in adjSpaces map
		/*for (int y = 0; y < numColumns; y++) {
			for (int x = 0; x < numRows; x++) {
				Set<BoardCell> adjSet = new HashSet<BoardCell>();
				if (y == 0) { //left column
					if (x == 0) {//if space is on the top left corner
						BoardCell right = this.getCellAt(x, y+1);
						BoardCell down = this.getCellAt(x+1, y);
						
						adjSet.add(down);
						adjSet.add(right);
					}
					else if (x == numRows-1) { //if space is bottom left corner
						BoardCell up = this.getCellAt(x-1, y);
						BoardCell right = this.getCellAt(x, y+1);

						adjSet.add(up);
						adjSet.add(right);
						
					}
					else { //rest of left column
						BoardCell up = this.getCellAt(x-1, y);
						BoardCell right = this.getCellAt(x, y+1);
						BoardCell down = this.getCellAt(x+1, y);
						
						adjSet.add(up);
						adjSet.add(right);
						adjSet.add(down);
					}
				}

				else if (y == numColumns-1) { //right column
					if (x == 0) {//if space is on the top right corner
						BoardCell left = this.getCellAt(x, y-1);						
						BoardCell down = this.getCellAt(x+1, y);
						
						adjSet.add(down);
						adjSet.add(left);						
					}
					else if (x == numRows-1) { //if space is bottom right corner
						BoardCell left = this.getCellAt(x,y-1);
						BoardCell up = this.getCellAt(x-1, y);	
						
						adjSet.add(up);
						adjSet.add(left);
					}
					else { //rest of right column
						BoardCell down = this.getCellAt(x+1, y);						
						BoardCell left = this.getCellAt(x,y-1);
						BoardCell up = this.getCellAt(x-1, y);	
						
						adjSet.add(up);
						adjSet.add(left);						
						adjSet.add(down);
					}
				}
				else if (x == 0 && y != 0 && y != numColumns-1) { //top row except corners
					BoardCell left = this.getCellAt(x,y-1);
					BoardCell right = this.getCellAt(x, y+1);
					BoardCell down = this.getCellAt(x+1, y);					

					adjSet.add(left);
					adjSet.add(down);
					adjSet.add(right);					
				}
				else if (x == numRows-1 && y != 0 && y != numColumns-1) { //bottom row except corners
					BoardCell up = this.getCellAt(x-1, y);
					BoardCell left = this.getCellAt(x,y-1);
					BoardCell right = this.getCellAt(x, y+1);
					
					adjSet.add(up);
					adjSet.add(left);
					adjSet.add(right);
				}
				else { //rest of the board
					BoardCell left = this.getCellAt(x,y-1);
					BoardCell right = this.getCellAt(x, y+1);
					BoardCell down = this.getCellAt(x+1, y);
					BoardCell up = this.getCellAt(x-1, y);

					adjSet.add(up);
					adjSet.add(left);
					adjSet.add(down);
					adjSet.add(right);
				}
				this.adjMatrix.put(getCellAt(x,y), adjSet);
			}
		}*/
	}
	
	public Set<BoardCell> getAdjList(int y, int x) {	//Returns the adjacency list for one cell
		/*BoardCell space = this.getCellAt(y, x);
		Set<BoardCell> retn = new HashSet<BoardCell>();
		retn = adjMatrix.get(space);
		return retn;*/
		return null;
	}
	
	
	
	public void calcTargets(int row, int colm, int pathLength) {
		//Calculates the targets that are pathLength distance from the startCell. 
		//The list of targets will be stored in an instance variable.
		/*BoardCell cell = this.getCellAt(row, colm);
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();

		visited.add(cell);//add current space to the visited list
		findAllTargets(cell, pathLength);
		targets.remove(cell);*/
	}
	
	public void findAllTargets(BoardCell thisSpace, int pathLength) {
		/*if (pathLength == 0) {		
			visited.remove(thisSpace);
			return;
		}
		Set<BoardCell> temp = new HashSet<BoardCell>(); //to transfer the set in the map to a readable set, because pointers.
		for (BoardCell adjSpace : adjMatrix.get(thisSpace)) {
			temp.add(getCellAt(adjSpace.getRow(), adjSpace.getColumn()));
		}
		for (BoardCell adjSpace : temp) {
			if (!visited.contains(adjSpace)) {
				visited.add(adjSpace);
				if (pathLength == 1 && (adjSpace.isDoorway()||adjSpace.getInitial() == 'W')) {
					targets.add(adjSpace);
				}
				else {
					visited.remove(thisSpace);
					findAllTargets(adjSpace, pathLength-1);
				}
			}
			visited.remove(adjSpace);
		}*/

	}
	
	
	public Map<Character, String> getLegend() {
		return legend;
	}
	
	public int getNumRows() {
		return board.length;
	}
	
	public int getNumColumns() {
		return board[0].length;
	}
	
	public BoardCell getCellAt(int row, int colm) {
		return board[row][colm];
	}
	
	public Set<BoardCell> getTargets() {//Returns the list of targets.
		return this.targets;
	}
	
}
