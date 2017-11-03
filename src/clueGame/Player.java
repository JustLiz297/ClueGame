package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;


public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> myCards = new ArrayList<Card>();
	private ArrayList<Card> seenCards = new ArrayList<Card>();
	
	public Player(String playerName) {
		super();
		this.playerName = playerName;
		String[] name = playerName.split(" ");
		String color = name[1].trim();
		this.color = convertColor(color);
		this.setPosition();
	}

	public Card disproveSuggestion(Solution suggestion) {
		//parameters?
		return null;
	}
	
	/**
	 * Starting positions of each character
	 */
	public void setPosition() {
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
		String transferColor = strColor;
		switch(strColor){ 
		case "Periwinkle":
			//transferColor = "BLUE"; //108,156,239
			this.color = new Color(108,156,239);
			break;
		case "Dandelion":
			//transferColor = "YELLOW"; //240,225,48
			this.color = new Color(240,225,48);
			break;
		case "Lavender":
			//transferColor = "MAGENTA"; //255,245,251
			this.color = new Color(255,245,251);
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
			//transferColor = "WHITE"; //238,220,130
			this.color = new Color(238,220,130);
			break;
		default:
			//transferColor = strColor;
			break;
		}
		//Used this only if we have to use the deafult colors
		/*Color color; 
		try {     // We can use reflection to convert the string to a color
			Field field =Class.forName("java.awt.Color").getField(transferColor.trim());
			color = (Color)field.get(null); 
		} catch (Exception e) {  
			color = null; // Not defined  
		}*/
		return color;
	}

	public void dealCard(Card card) {
		this.myCards.add(card);
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

	public Color getColor() {
		return color;
	}
	public ArrayList<Card> getCards() {
		return myCards;
	}

	@Override
	public String toString() {
		return "Player [playerName=" + playerName + "]";
	}
	
}

