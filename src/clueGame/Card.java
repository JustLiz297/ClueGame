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
	
	public boolean isRoom() {
		if (this.type == CardType.ROOM) {return true;}
		else {return false;}
	}
	public boolean isWeapon() {
		if (this.type == CardType.WEAPON) {return true;}
		else {return false;}
	}
	public boolean isPerson() {
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
