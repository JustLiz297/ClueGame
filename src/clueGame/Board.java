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

/**
 * This is the Board class, it is the game board of the Clue Game
 * @author eboyle, annelysebaker
 * @version 1.4
 * 
 *
 */
public class Board{
	private int numRows; //number of rows in the game board
	private int numColumns; //number of columns in the game board
	public final static int MAX_BOARD_SIZE = 51; //max possible board size, either rows or columns 
	private BoardCell[][] board; // game board array
	private Map<Character, String> legend = new HashMap<Character, String>(); //Map of the legend of the game board, initals of the cells and what kind of space the represent
	private Map<BoardCell, Set<BoardCell>> adjMatrix = new HashMap<BoardCell, Set<BoardCell>>(); //Map of the cells and what cells are adjacent
	private Set<BoardCell> targets; //Set of cells a piece can move to in a certain roll
	private Set<BoardCell> visited; //Set of cells used in calculating the targets Set
	private String boardConfigFile; //name of the board file that will be loaded in
	private String roomConfigFile; //name of the legend file that will be loaded in
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	/**
	 * Initializes the board
	 * Calls loadRoomConfig() to load the legend of the game board.
	 * Calls loadBoardConfig() to load the game board layout.
	 * Calls calcAdjacencies() to create the adjacencies map (adjMatrix)
	 * @throws BadConfigFormatException Error when creating the config files
	 */
	public void initialize() throws BadConfigFormatException {
		this.loadRoomConfig(); //always call legend config first
		this.loadBoardConfig(); //set up the game board
		this.calcAdjacencies(); //creates the Map of adjacent cells
	}
	/**
	 * Sets the boardConfigFile variable to the passed in layoutfile and Sets the roomConfigFile variable to the passed in legendfile
	 * @param layoutfile the game board layout file
	 * @param legendfile the legend file
	 */
	public void setConfigFiles(String layoutfile, String legendfile) {
		boardConfigFile = layoutfile;
		roomConfigFile = legendfile;
	}
	/**
	 * Loads in the legend file and makes a map based on it
	 * @throws BadConfigFormatException Error when creating the config files
	 */
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
					throw new BadConfigFormatException("Invalid Legend Format");
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * Reads in the game board layout file and creates the game board with BoardCells
	 * @throws BadConfigFormatException Error when creating the config files
	 */
	public void loadBoardConfig() throws BadConfigFormatException{
		try {
			//Reads in file and counts rows and columns
			FileReader boardReader = new FileReader(boardConfigFile);
			Scanner in = new Scanner(boardReader);
			int rows = 1; //starting the row counter
			numColumns = in.nextLine().split(",").length; //default number of columns
			while (in.hasNext()) {
				String cycling = in.nextLine();
				if (cycling.split(",").length != this.numColumns) { //checks if all the columns are equal
					throw new BadConfigFormatException("Inconsistent Number of Columns in File");
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
				String[] charEntry = entry.split(","); //makes an array of each line of the board
				for (int colm = 0; colm < numColumns; colm++) {
					if (!legend.containsKey(charEntry[colm].charAt(0))) {
						throw new BadConfigFormatException("Invalid Room in Game Board File");
					}
					else {
						board[row][colm].setInitial(charEntry[colm].charAt(0)); //sets the cell type using the first character
						if (charEntry[colm].length() == 2) { //If the cell is a doorway or the name cell
							board[row][colm].setDoorDirection(charEntry[colm].charAt(1)); //Sets doorway direction per character on board
						}
						else { //If cell is not a doorway nor a name cell
							board[row][colm].setDoorDirection('N');	//'N' is just passed in for default case, stands for NONE					
						}
					}
				}
			}
			inEntry.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
	}
	/**
	 * Calculates the adjacent cells of each cell in the game board
	 */
	public void calcAdjacencies() {
		//Calculates the list of adjacent grid cell from a certain point, then stores them in a Set, then
		//puts them in adjSpaces map
		for (int y = 0; y < numColumns; y++) {
			for (int x = 0; x < numRows; x++) {
				Set<BoardCell> adjSet = new HashSet<BoardCell>();
				if (this.getCellAt(x,y).isWalkway() || this.getCellAt(x,y).isDoorway()){
					if (y == 0) { //left column
						if (x == 0) {//if space is on the top left corner
							BoardCell right = this.getCellAt(x, y+1);
							BoardCell down = this.getCellAt(x+1, y);
							
							//Can never be a door, so don't check current space door direction
							if (down.isWalkway()||down.getDoorDirection()==DoorDirection.UP) {adjSet.add(down);}
							if (right.isWalkway()||down.getDoorDirection()==DoorDirection.LEFT) {adjSet.add(right);}
						}
						else if (x == numRows-1) { //if space is bottom left corner
							BoardCell up = this.getCellAt(x-1, y);
							BoardCell right = this.getCellAt(x, y+1);

							//Can never be a door, so don't check current space door direction
							if (up.isWalkway()||up.getDoorDirection()==DoorDirection.DOWN) {adjSet.add(up);}
							if (right.isWalkway()||right.getDoorDirection()==DoorDirection.LEFT) {adjSet.add(right);}
						}
						else { //rest of left column
							BoardCell up = this.getCellAt(x-1, y);
							BoardCell right = this.getCellAt(x, y+1);
							BoardCell down = this.getCellAt(x+1, y);

							if (this.getCellAt(x,y).isDoorway()) {
								if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.DOWN && down.isWalkway()){adjSet.add(down);}
								if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.RIGHT && right.isWalkway()){adjSet.add(right);}
								if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.UP && up.isWalkway()){adjSet.add(up);}
							}
							else {
								if (up.isWalkway()||up.getDoorDirection()==DoorDirection.DOWN) {adjSet.add(up);}
								if (right.isWalkway()||right.getDoorDirection()==DoorDirection.LEFT) {adjSet.add(right);}
								if (down.isWalkway()||down.getDoorDirection()==DoorDirection.UP) {adjSet.add(down);}
							}
						}
					}

					else if (y == numColumns-1) { //right column
						if (x == 0) {//if space is on the top right corner
							BoardCell left = this.getCellAt(x, y-1);						
							BoardCell down = this.getCellAt(x+1, y);
							
							//Can never be a door, so don't check current space door direction
							if (down.isWalkway()||down.getDoorDirection()==DoorDirection.UP) {adjSet.add(down);}
							if (left.isWalkway()||left.getDoorDirection()==DoorDirection.RIGHT) {adjSet.add(left);}					
						}
						else if (x == numRows-1) { //if space is bottom right corner
							BoardCell left = this.getCellAt(x,y-1);
							BoardCell up = this.getCellAt(x-1, y);	
							
							//Can never be a door, so don't check current space door direction
							if (up.isWalkway()||up.getDoorDirection()==DoorDirection.DOWN) {adjSet.add(up);}
							if (left.isWalkway()||left.getDoorDirection()==DoorDirection.RIGHT) {adjSet.add(left);}
						}
						else { //rest of right column
							BoardCell down = this.getCellAt(x+1, y);						
							BoardCell left = this.getCellAt(x,y-1);
							BoardCell up = this.getCellAt(x-1, y);	

							if (this.getCellAt(x,y).isDoorway()) {
								if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.DOWN && down.isWalkway()){adjSet.add(down);}
								if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.LEFT && left.isWalkway()){adjSet.add(left);}
								if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.UP && up.isWalkway()){adjSet.add(up);}
							}
							else {
								if (up.isWalkway()||up.getDoorDirection()==DoorDirection.DOWN) {adjSet.add(up);}
								if (left.isWalkway()||left.getDoorDirection()==DoorDirection.RIGHT) {adjSet.add(left);}
								if (down.isWalkway()||down.getDoorDirection()==DoorDirection.UP) {adjSet.add(down);}
							}
						}
					}
					else if (x == 0 && y != 0 && y != numColumns-1) { //top row except corners
						BoardCell left = this.getCellAt(x,y-1);
						BoardCell right = this.getCellAt(x, y+1);
						BoardCell down = this.getCellAt(x+1, y);					

						if (this.getCellAt(x,y).isDoorway()) {
							if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.DOWN && down.isWalkway()){adjSet.add(down);}
							if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.RIGHT && right.isWalkway()){adjSet.add(right);}
							if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.LEFT && left.isWalkway()){adjSet.add(left);}
						}
						else {
							if (left.isWalkway()||left.getDoorDirection()==DoorDirection.RIGHT) {adjSet.add(left);}
							if (right.isWalkway()||right.getDoorDirection()==DoorDirection.LEFT) {adjSet.add(right);}
							if (down.isWalkway()||down.getDoorDirection()==DoorDirection.UP) {adjSet.add(down);}
						}			
					}
					else if (x == numRows-1 && y != 0 && y != numColumns-1) { //bottom row except corners
						BoardCell up = this.getCellAt(x-1, y);
						BoardCell left = this.getCellAt(x,y-1);
						BoardCell right = this.getCellAt(x, y+1);

						if (this.getCellAt(x,y).isDoorway()) {
							if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.LEFT && left.isWalkway()){adjSet.add(left);}
							if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.RIGHT && right.isWalkway()){adjSet.add(right);}
							if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.UP && up.isWalkway()){adjSet.add(up);}
						}
						else {
							if (up.isWalkway()||up.getDoorDirection()==DoorDirection.DOWN) {adjSet.add(up);}
							if (right.isWalkway()||right.getDoorDirection()==DoorDirection.LEFT) {adjSet.add(right);}
							if (left.isWalkway()||left.getDoorDirection()==DoorDirection.RIGHT) {adjSet.add(left);}
						}
					}
					else { //rest of the board
						BoardCell left = this.getCellAt(x,y-1);
						BoardCell right = this.getCellAt(x, y+1);
						BoardCell down = this.getCellAt(x+1, y);
						BoardCell up = this.getCellAt(x-1, y);

						if (this.getCellAt(x,y).isDoorway()) {
							if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.DOWN && down.isWalkway()){adjSet.add(down);}
							if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.LEFT && left.isWalkway()){adjSet.add(left);}
							if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.RIGHT && right.isWalkway()){adjSet.add(right);}
							if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.UP && up.isWalkway()){adjSet.add(up);}
						}
						else {
							if (up.isWalkway()||up.getDoorDirection()==DoorDirection.DOWN) {adjSet.add(up);}
							if (right.isWalkway()||right.getDoorDirection()==DoorDirection.LEFT) {adjSet.add(right);}
							if (left.isWalkway()||left.getDoorDirection()==DoorDirection.RIGHT) {adjSet.add(left);}
							if (down.isWalkway()||down.getDoorDirection()==DoorDirection.UP) {adjSet.add(down);}
						}
					}
				}
				this.adjMatrix.put(getCellAt(x,y), adjSet); //puts the created adjacent cells Set in a Map with the cell as the key
			}
		}
	}
	/**
	 * Returns the set of adjacent cells to the cells at the passed in coordinates
	 * @param x x-coordinate of a certain cell
	 * @param y y-coordinate of a certain cell
	 * @return List of adjacent cells to the passed in cell
	 */
	public Set<BoardCell> getAdjList(int x, int y) {	//Returns the adjacency list for one cell
		BoardCell space = this.getCellAt(x, y);
		return adjMatrix.get(space);
	}
	/**
	 * This is the function that calculates the cells we can move from a dice roll
	 * @param row the row coordinate of the current cell
	 * @param colm the column coordinate of the current cell
	 * @param pathLength the length of the dice roll
	 */
	public void calcTargets(int row, int colm, int pathLength) {
		//Calculates the targets that are pathLength distance from the startCell. 
		//The list of targets will be stored in an instance variable.
		BoardCell cell = this.getCellAt(row, colm);
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();

		visited.add(cell);//add current space to the visited list
		findAllTargets(cell, pathLength);
		targets.remove(cell);
	}
	/**
	 * This is the recursive function called by calcTargets that calculates the spaces the piece can move to from a dice roll
	 * @param thisSpace the current Board Cell
	 * @param pathLength the dice roll, which determines how many times we call this method 
	 */
	public void findAllTargets(BoardCell thisSpace, int pathLength) {
		if (pathLength == 0) { //return case
			visited.remove(thisSpace); 
			return;
		}
		Set<BoardCell> adjCells = new HashSet<BoardCell>(); //to transfer the set in the map to a readable set, because pointers.****
		for (BoardCell adjSpace : adjMatrix.get(thisSpace)) {
			adjCells.add(getCellAt(adjSpace.getRow(), adjSpace.getColumn()));
		}
		for (BoardCell adjSpace : adjCells) {
			if (!visited.contains(adjSpace)) {
				visited.add(adjSpace);
				if (adjSpace.isDoorway()) { //checks if entered from the correct direction****
					if(adjSpace.getDoorDirection()==DoorDirection.RIGHT){							
						if (thisSpace.getColumn() == adjSpace.getColumn()+1 && thisSpace.getRow()==adjSpace.getRow()) {targets.add(adjSpace);}
					}
					else if (adjSpace.getDoorDirection()==DoorDirection.LEFT && thisSpace.getRow()==adjSpace.getRow()){
						if (thisSpace.getColumn() == adjSpace.getColumn()-1) {targets.add(adjSpace);}
					}
					else if(adjSpace.getDoorDirection()==DoorDirection.UP && thisSpace.getColumn()==adjSpace.getColumn()){
						if (thisSpace.getRow() == adjSpace.getRow()-1) {targets.add(adjSpace);}
					}
					else if (adjSpace.getDoorDirection()==DoorDirection.DOWN && thisSpace.getColumn()==adjSpace.getColumn()){
						if (thisSpace.getRow() == adjSpace.getRow()+1) {targets.add(adjSpace);}
					}
				} 
				else if (pathLength == 1) { //when looking at end of the roll
					if (adjSpace.isWalkway()){targets.add(adjSpace);}
					if (adjSpace.isDoorway()) { //checks if entered from the correct direction****
						if(adjSpace.getDoorDirection()==DoorDirection.RIGHT){							
							if (thisSpace.getColumn() == adjSpace.getColumn()+1 && thisSpace.getRow()==adjSpace.getRow()) {targets.add(adjSpace);}
						}
						else if (adjSpace.getDoorDirection()==DoorDirection.LEFT && thisSpace.getRow()==adjSpace.getRow()){
							if (thisSpace.getColumn() == adjSpace.getColumn()-1) {targets.add(adjSpace);}
						}
						else if(adjSpace.getDoorDirection()==DoorDirection.UP && thisSpace.getColumn()==adjSpace.getColumn()){
							if (thisSpace.getRow() == adjSpace.getRow()-1) {targets.add(adjSpace);}
						}
						else if (adjSpace.getDoorDirection()==DoorDirection.DOWN && thisSpace.getColumn()==adjSpace.getColumn()){
							if (thisSpace.getRow() == adjSpace.getRow()+1) {targets.add(adjSpace);}
						}
					}
				}
				else {
					findAllTargets(adjSpace, pathLength-1);
				}
				visited.remove(adjSpace);
			}
		}
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
