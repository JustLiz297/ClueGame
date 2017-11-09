package clueGame;

import java.awt.Color;
import java.util.Collections;

public class HumanPlayer extends Player{

	public HumanPlayer(String playerName) {
		super(playerName);
	}

	@Override
	public boolean isHuman() {
		return true;
	}
	@Override
	public void dealCard(Card card) {
		this.seenCards.add(card);
		this.myCards.add(card);
	}
    /**
     * If the player has the card that is in the suggestion, the function returns the card, if not, it returns null
     * @param suggestion
     * @return A Card if they have it, null if not
     */
	@Override
	public Card disproveSuggestion(Solution suggestion) {
		Collections.shuffle(myCards);
		for (Card card : myCards) {
			if (suggestion.person == card.getCardName() || suggestion.weapon == card.getCardName() || suggestion.room == card.getCardName()) {
				return card;
			}
		}
		return null;
	}
}
