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
	private int row; //x-coordinate of the cell
	private int column; //y-coordinate of the cell
	private char initial; //type of space, labeled with a single letter
	private DoorDirection doorDirection; //which way the cell can be entered
	
	/**
	 * Creates a BoardCell with the passed in row and column
	 * @param xCoord the row of the BoardCell
	 * @param yCoord the column of the BoardCell
	 */
	public BoardCell(int xCoord, int yCoord) {
		this.row = xCoord; //sets the x-coordinate
		this.column = yCoord; //sets the y-coordinate
	}
	
	public int getRow() {
		return row; //returns the x-coordinate
	}

	public int getColumn() {
		return column; //returns the y-coordinate
	}

	public char getInitial() {
		return initial; //returns the cell type, by its initial
	}
	
	public DoorDirection getDoorDirection() {
		return this.doorDirection; //returns the Door Direction of the cell
	}
	
	public void setInitial(char initial) {
		this.initial = initial; //sets the initial of the cell
	}
	
	public void setDoorDirection(DoorDirection d) {
		this.doorDirection = d; //sets the Door Direction of the cell
	}

	/**
	 * 
	 * @return true if the BoardCell is a walkway, false if not
	 */
	public boolean isWalkway() {
		if (this.initial == 'W') {return true;} //'W' = walkway
		else {return false;}
	}
	/**
	 * 
	 * @return true if the BoardCell is a room, false if not
	 */
	public boolean isRoom() {
		if (this.initial != 'C' && this.initial != 'W') {return true;} //'W' = walkway, 'C' = closet
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
