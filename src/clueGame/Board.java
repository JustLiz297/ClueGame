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

public class Board extends IntBoard{
	private int numRows;
	private int numColumns;
	public final static int MAX_BOARD_SIZE = 51;
	private BoardCell[][] board;
	private Map<Character, String> legend = new HashMap<Character, String>();
	private Map<BoardCell, Set<BoardCell>> adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
	private Set<BoardCell> targets;
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
		this.calcAdjacencies();
		this.loadRoomConfig(); //always call legend config first
		this.loadBoardConfig();

		
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
		
	}
	
	public void calcTargets(BoardCell cell, int pathLength) {
		
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
	
}
