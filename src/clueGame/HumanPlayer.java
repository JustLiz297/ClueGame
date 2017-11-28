package clueGame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.Set;
/**
 * This is the HumanPlayer class, the class of the human player of the game
 * @author eboyle, annelysebaker
 * @version 1.2
 * 
 *
 */
public class HumanPlayer extends Player{
	public static Board board = Board.getInstance();

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


	/**
	 * Translates mouse position from listener to move human player
	 * @param row
	 * @param colm
	 */
	public void move(int roll, int row, int colm) {
		this.row = row;
		this.column = colm;
	}
}
