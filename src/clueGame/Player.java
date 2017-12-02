package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;
/**
 * This is the Player class, it is parent class of the Computer and Human Players
 * @author eboyle, annelysebaker
 * @version 1.2
 * 
 *
 */

public abstract class Player extends JPanel{
	private String playerName;
	protected int row;
	protected int column;
	private Color color;
	public static final int WIDTH = 34;
	public static final int HEIGHT = 34;
	public static final int SCALE = 34;
	public ArrayList<Card> myCards = new ArrayList<Card>();
	public ArrayList<Card> seenCards = new ArrayList<Card>();
	public static Board board = Board.getInstance();
	
	public Player(String playerName) {
		super();
		this.playerName = playerName.trim();
		String[] name = playerName.split(" ");
		String color = name[1].trim();
		this.color = convertColor(color);
		this.setStartingPosition();
	}
	/**
	 * Starting positions of each character
	 */
	public void setStartingPosition() {
		switch(this.playerName) {
		case "Preacher Periwinkle":
			this.row = 0;
			this.column = 14;
			break;
		case "Doctor Dandelion":
			this.row = 6;
			this.column = 0;
			break;
		case "Lawyer Lavender":
			this.row = 15;
			this.column = 24;
			break;
		case "Educator Emerald":
			this.row = 20;
			this.column = 7;
			break;
		case "Captain Cardinal":
			this.row = 20;
			this.column = 19;
			break;
		case "Farmer Flax":
			this.row = 16;
			this.column = 0;
			break;
		default:
		}
	}
	/**
	 * Creates the color based on the Player's name
	 * @param strColor color name from Player's name
	 * @return created Color from Player's name
	 */
	public Color convertColor(String strColor) {
		switch(strColor){ 
		case "Periwinkle":
			//transferColor = "BLUE"; //108,156,239
			this.color = new Color(108,156,239);
			break;
		case "Dandelion":
			//transferColor = "YELLOW"; //238,220,130
			this.color = new Color(221,179,8);
			break;
		case "Lavender":
			//transferColor = "MAGENTA"; //216,206,242
			this.color = new Color(216,206,242);
			break;
		case "Emerald":
			//transferColor = "GREEN"; //9,69,60
			this.color = new Color(9,69,60);
			break;
		case "Cardinal":
			//transferColor = "RED"; //189,32,49
			this.color = new Color(189,32,49);
			break;
		case "Flax":
			//transferColor = "WHITE"; //241,235,190
			this.color = new Color(241,235,190);
			break;
		default:
			//transferColor = strColor;
			break;
		}
		//Used this only if we have to use the default colors
		/*Color color; 
		try {     // We can use reflection to convert the string to a color
			Field field =Class.forName("java.awt.Color").getField(transferColor.trim());
			color = (Color)field.get(null); 
		} catch (Exception e) {  
			color = null; // Not defined  
		}*/
		return color;
	}
	public void draw(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		g.drawOval(this.column*SCALE, this.row*SCALE, WIDTH, HEIGHT);
		g.setColor(this.color);
		g.fillOval(this.column*SCALE, this.row*SCALE, WIDTH, HEIGHT);
	}
	
	public abstract boolean isHuman();
	public abstract Card disproveSuggestion(Solution suggestion);
	public abstract void dealCard(Card card);
	public abstract void move(int roll, int row, int colm);
	
	/**
	 * Human player move function
	 */
	public void humanTargets(int roll) {
		board.calcTargets(this.row, this.column, roll);
	}
	
	public String getPlayerName() {
		return playerName;
	}
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public Color getColor() {
		return color;
	}
	public ArrayList<Card> getCards() {
		return myCards;
	}
	@Override
	public String toString() {
		return playerName;
	}
	public void clearHand() {
		myCards.clear();
	}
	public void addSeenCards(Card c) {
		this.seenCards.add(c);
	}
	
}

