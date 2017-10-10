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
	private BoardCell[][] board = new BoardCell[numRows][numColumns];
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
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				board[i][j] = new BoardCell(i,j); //fills game board with boardCells
			}
		}
		this.calcAdjacencies();
		this.loadBoardConfig();
		this.loadRoomConfig();
		
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
				legend.put(entry[0].charAt(0), entry[1]);
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		
	}
	
	public void loadBoardConfig() throws BadConfigFormatException{
		try {
			FileReader boardReader = new FileReader(boardConfigFile);
			Scanner in = new Scanner(boardReader);
			int rows = 0;
			while (in.hasNext()) {
				String[] entry = in.nextLine().split(", ");
				for (int colm = 0; colm < entry.length-1; colm++) {
					board[rows][colm].setInitial(entry[colm].charAt(0));
				}
				rows++;

			}
			//figure out how to assign numColumns
			numRows = rows;
			in.close();
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
		return numRows;
	}
	public int getNumColumns() {
		return numColumns;
	}
	
	public BoardCell getCellAt(int row, int colm) {
		return board[row][colm];
	}
	
}
