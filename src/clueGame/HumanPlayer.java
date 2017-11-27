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
		addMouseListener(new clickListener());
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
	 * Human player move function
	 */
	@Override
	public void move(int roll) {
		board.calcTargets(this.row, this.column, roll);
		Set<BoardCell> targets = board.getTargets();
	}
	/**
	 * Listener for click for human player movement
	 * @author eboyle
	 *
	 */
	class clickListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent event) {
			int row =(int) (event.getPoint().getY()/34);
			int column =(int) (event.getPoint().getX()/34);
			System.out.println(row);
			System.out.println(column);
			moveTo(row, column);
			repaint();			
		}
		public void mouseExited(MouseEvent event) {}
		public void mousePressed (MouseEvent event) {}
		public void mouseReleased (MouseEvent event) {}
		public void mouseEntered (MouseEvent event) {}
		
	}
	/**
	 * Translates mouse position from listener to move human player
	 * @param row
	 * @param colm
	 */
	public void moveTo(int row, int colm) {
		this.row = row;
		this.column = colm;
		System.out.println(this.row);
		System.out.println(this.column);
	}
}
