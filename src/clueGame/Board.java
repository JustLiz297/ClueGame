/*
 * Authors: Elizabeth (Liz) Boyle, Annelyse Baker
 * Description: Board class
 */

package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import clueGame.BoardCell;

public class Board {
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
	
	public void initialize() {
		
	}
	
	public void setConfigFiles(String layoutfile, String legendfile) {
		this.boardConfigFile = layoutfile;
		this.roomConfigFile = legendfile;
	}
	
	public void loadRoomConfig() throws BadConfigFormatException{
		try {
			FileReader roomReader = new FileReader(roomConfigFile);
			Scanner in = new Scanner(roomReader);
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		
	}
	
	public void loadBoardConfig() {
		
	}
	
	public void calcAdjacencies() {
		
	}
	
	public void calcTargets(BoardCell cell, int pathLength) {
		
	}
	
	public Map<Character, String> getLegend() throws BadConfigFormatException{
		try {
			FileReader reader = new FileReader("Legend.txt");
			Scanner in = new Scanner(reader);
			
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		return null;
	}
	public int getNumRows() {
		return 0;
	}
	
	public int getNumColumns() {
		return 0;
	}
	
	public BoardCell getCellAt(int row, int colm) {
		return null;
	}
	
}
