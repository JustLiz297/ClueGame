/*
 * Authors: Elizabeth (Liz) Boyle, Annelyse Baker
 * Description: BoardCell class, each cell has a y&x coordinate
 */

package clueGame;

public class BoardCell {
	private int row;
	private int column; 
	private char initial; //type of space, labeled with a single letter
	private DoorDirection doorDirection;
	
	public BoardCell(int yCoord, int xCoord) {
		super();
		this.row = yCoord;
		this.column = xCoord;
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

	public boolean isWalkway() {
		if (this.initial == 'h') {return true;}
		//'h' for our game, 'W' for sample files
		else {return false;}
	}
	
	public boolean isRoom() {
		if (this.initial != 'C' && this.initial != 'h') {return true;}
		//'h' for our game, 'W' for sample files
		else {return false;}

	}
	public boolean isDoorway() {
		if (this.doorDirection == DoorDirection.NONE) {return false;}
		else {return true;}
	}
	

}