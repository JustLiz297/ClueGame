package clueGame;

import java.awt.Color;

public class HumanPlayer extends Player{

	public HumanPlayer(String playerName) {
		super(playerName);
	}

	@Override
	public boolean isHuman() {
		return true;
	}
	public void dealCard(Card card) {
		this.seenCards.add(card);
		this.myCards.add(card);
	}
}
