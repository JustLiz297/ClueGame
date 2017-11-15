/*
 * Authors: Elizabeth (Liz) Boyle, Annelyse Baker
 * Description: BoardCell class, each cell has a y&x coordinate
 */


package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;



/**
 * Class the represents the spaces of the game board
 * @author eboyle, annelysebaker
 * @version 1.2
 *
 */
public class BoardCell extends JPanel{
	private int row; //x-coordinate of the cell
	private int column; //y-coordinate of the cell
	private char initial; //type of space, labeled with a single letter
	private DoorDirection doorDirection; //which way the cell can be entered
	public static final int WIDTH = 40;
	public static final int HEIGHT = 40;
	public static final int SCALE = 40;
	private boolean label = false;
	
	/**
	 * Creates a BoardCell with the passed in row and column
	 * @param xCoord the row of the BoardCell
	 * @param yCoord the column of the BoardCell
	 */
	public BoardCell(int xCoord, int yCoord) {
		this.row = xCoord; //sets the x-coordinate
		this.column = yCoord; //sets the y-coordinate
	}
	/**
	 * Paints the cell according to its type
	 * @param g - drawing board
	 */
	public void draw(Graphics g) {
		super.paintComponent(g);
		switch(this.initial) {
		//if the cell is a walkway
		case 'W':
			g.setColor(new Color(230,235,245));
			g.fillRect(this.column*SCALE, this.row*SCALE, WIDTH, HEIGHT);
			g.setColor(Color.black);
			g.drawRect(this.column*SCALE, this.row*SCALE, WIDTH, HEIGHT);
			break;
		//if the cell is a room
		default:
			g.setColor(new Color(185,138,168));
			g.fillRect(this.column*SCALE, this.row*SCALE, WIDTH, HEIGHT);
			//if the cell is a doorway
			if (this.isDoorway()) {
				g.setColor(new Color(80,61,17));
				switch(this.doorDirection) {
				case UP:
					g.fillRect(column*SCALE, row*SCALE+1, WIDTH, 3);
					break;
				case DOWN:
					g.fillRect(column*SCALE, row*SCALE+HEIGHT-3, WIDTH, 3);
					break;
				case RIGHT:
					g.fillRect(column*SCALE+WIDTH-3, row*SCALE, 3, HEIGHT);
					break;
				case LEFT:
					g.fillRect(column*SCALE+1, row*SCALE, 3, HEIGHT);
					break;
				}
			}
			break;
		}
	}
	/**
	 * Adds the names to the rooms
	 * @param g - drawing board
	 */
	public void addLabels(Graphics g) {
		g.setColor(Color.black);
		switch(this.initial) {
		case 'K':
			g.drawString("Kitchen", column*SCALE, row*SCALE);
			break;
		case 'D':
			g.drawString("Dining Room", column*SCALE, row*SCALE);
			break;
		case 'S':
			g.drawString("Workshop", column*SCALE, row*SCALE);
			break;
		case 'G':
			g.drawString("Green House", column*SCALE, row*SCALE);
			break;
		case 'B':
			g.drawString("Ballroom", column*SCALE, row*SCALE);
			break;
		case 'E':
			g.drawString("Entrance", column*SCALE, row*SCALE);
			break;
		case 'F':
			g.drawString("Family Room", column*SCALE, row*SCALE);
			break;
		case 'L':
			g.drawString("Library", column*SCALE, row*SCALE);
			break;
		case 'O':
			g.drawString("Office", column*SCALE, row*SCALE);
			break;
		}
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
	public void setLabel(boolean label) {
		this.label = label;
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
	 * @return false if DoorDirection is NONE and true else wise
	 */
	public boolean isDoorway() {
		if (this.doorDirection == DoorDirection.NONE) {return false;}
		else {return true;}
	}
	public boolean isName() {
		if (label) {return true;}
		else {return false;}
	}
}
