/*
 * Authors: Elizabeth (Liz) Boyle, Annelyse Baker
 * Description: BoardCell class, each cell has a y&x coordinate
 */


package clueGame;
/**
 * Class the represents the spaces of the game board
 * @author eboyle, annelysebaker
 * @version 1.2
 *
 */
public class BoardCell {
	private int row;
	private int column; 
	private char initial; //type of space, labeled with a single letter
	private DoorDirection doorDirection;
	
	/**
	 * Creates a BoardCell with the passed in row and column
	 * @param xCoord the row of the BoardCell
	 * @param yCoord the column of the BoardCell
	 */
	public BoardCell(int xCoord, int yCoord) {
		super();
		this.row = xCoord;
		this.column = yCoord;
	}
	
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public char getInitial() {
		return initial;
	}
	
	public DoorDirection getDoorDirection() {
		return this.doorDirection;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public void setInitial(char initial) {
		this.initial = initial;
	}
	
	public void setDoorDirection(DoorDirection d) {
		this.doorDirection = d;
	}

	/* For testing purposes */
	@Override
	public String toString() {
		return "BoardCell [yCoord=" + row + ", xCoord=" + column + "]";
	}
	/**
	 * 
	 * @return true if the BoardCell is a walkway, false if not
	 */
	public boolean isWalkway() {
		if (this.initial == 'W') {return true;}
		//'h' for our game, 'W' for sample files
		else {return false;}
	}
	/**
	 * 
	 * @return true if the BoardCell is a room, false if not
	 */
	public boolean isRoom() {
		if (this.initial != 'C' && this.initial != 'W') {return true;}
		//'W' for sample files
		else {return false;}

	}
	/**
	 * 
	 * @return false if DoorDirection is NONE and true elsewise
	 */
	public boolean isDoorway() {
		if (this.doorDirection == DoorDirection.NONE) {return false;}
		else {return true;}
	}
	

}
