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
	
	/**
	 * Sets the door direction per passed in character
	 * @param d passed in character from board layout
	 */
	public void setDoorDirection(char d) {
		switch(d) {
		case 'U'://If the second character in the cell is U
			this.doorDirection = DoorDirection.UP;
			break;
		case 'D'://If the second character in the cell is D
			this.doorDirection = DoorDirection.DOWN;
			break;
		case 'L'://If the second character in the cell is L
			this.doorDirection = DoorDirection.LEFT;
			break;		
		case 'R': //If the second character in the cell is R
			this.doorDirection = DoorDirection.RIGHT;
			break;
		default: //Any cell that doesn't have two characters gets set to none as it isn't a door
			this.doorDirection = DoorDirection.NONE;
			break;
		}
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
