package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	
	
	
	public Card(String cardName, CardType type) {
		super();
		this.cardName = cardName;
		this.type = type;
	}

	public boolean equals() {
		return false;
	}
	
	public boolean isRoom() { //Returns true if the Card is a room
		if (this.type == CardType.ROOM) {return true;}
		else {return false;}
	}
	public boolean isWeapon() { //Returns true if the Card is a weapon
		if (this.type == CardType.WEAPON) {return true;}
		else {return false;}
	}
	public boolean isPerson() { //Returns true if the Card is a Person
		if (this.type == CardType.PERSON) {return true;}
		else {return false;}
	}

	//For testing purposes
	@Override
	public String toString() {
		return type + ":" + cardName;
	}
	public String getCardName() {return cardName;}

}
