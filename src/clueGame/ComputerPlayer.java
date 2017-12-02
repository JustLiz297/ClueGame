package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import clueGUI.ControlPanel;
/**
 * This is the ComputerPlayer class, the class of the computer players of the game
 * @author eboyle, annelysebaker
 * @version 1.2
 * 
 *
 */
public class ComputerPlayer extends Player{
	private BoardCell lastRoom = null;
	private ArrayList<Card> unseenCards = new ArrayList<Card>();
	public static Board board = Board.getInstance();
	private static ControlPanel controls = ControlPanel.getInstance();
	

	public ComputerPlayer(String playerName) {
		super(playerName);
	}
	/**
	 * Select the Computer Player's next movement based on roll, if there is a room and if it has been visited
	 * @param targets - List of possible spaces to go to
	 * @return the space to go to
	 */
	public BoardCell pickLocation(Set<BoardCell> targets) {
		ArrayList<BoardCell> possibleTargets = new ArrayList<BoardCell>();
		for (BoardCell space : targets) {
			if (space.isRoom() && space != lastRoom) {
				lastRoom = space;
				return space;
			}
			possibleTargets.add(space);
		}
		Random rand = new Random();
		int r = rand.nextInt(targets.size());
		return possibleTargets.get(r);
		
	}
	
	public void makeAccusation() {
		String room = "";
		String person = "";
		String weapon = "";
		for (Card c : unseenCards) {
			if (c.isWeapon()) {
				weapon = c.getCardName();
			}
			else if (c.isPerson()) {
				person = c.getCardName();
			}
			else if (c.isRoom()) {
				room = c.getCardName();
			}
		}
		Solution accusation = new Solution(person, room, weapon);
		if (board.checkAccusation(accusation, this)){board.gameWon(this);}
		else {			
			JDialog loser = new JDialog();
			loser.setTitle(this.getPlayerName() + " was Wrong!");
			loser.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
			loser.setSize(400, 150);
			loser.setLocationRelativeTo(null);
			loser.setLayout(new GridLayout(4,1));
			JLabel message = new JLabel(this.getPlayerName() + " accused incorrectly.");
			JLabel message2 = new JLabel("The their accusation was: ");
			JLabel solution = new JLabel(accusation.person + " in the " + accusation.room + " with the " + accusation.weapon);
			solution.setHorizontalAlignment((int) CENTER_ALIGNMENT);
			message2.setHorizontalAlignment((int) CENTER_ALIGNMENT);
			message.setHorizontalAlignment((int) CENTER_ALIGNMENT);
			loser.add(message, BorderLayout.CENTER);
			loser.add(message2, BorderLayout.CENTER);
			loser.add(solution, BorderLayout.CENTER);
			JButton oK = new JButton("OK");
			class ExitListener implements ActionListener{
				public void actionPerformed(ActionEvent e) {
					loser.dispose();}
			}
			oK.addActionListener(new ExitListener());
			loser.add(oK);
			loser.setVisible(true);
		}
	}
	/**
	 * Creates a suggestion based on the room the Computer Player is in a what card they have not seen
	 * @param currentRoom - Current room the Computer Player is in
	 * @return the Computer's generated Suggestion
	 */
	public Solution createSuggestion(String currentRoom) {
		Collections.shuffle(unseenCards);
		Collections.shuffle(seenCards);
		String room = currentRoom;
		String person = "";
		String weapon = "";
		
		//finds a random weapon in the unseen cards to suggest
		for (Card c : unseenCards) {
			if (c.isWeapon()) {
				weapon = c.getCardName();
				break;
			}
		}
		//finds a random person in the unseen cards to suggest
		for (Card c : unseenCards) {
			if (c.isPerson()) {
				person = c.getCardName();
				break;
			}
		}
		return new Solution(person, room, weapon);
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
	 * Takes in a die roll and calls pickLocation to choose a location to move to, then moves
	 */
	@Override
	public void move(int roll, int row, int colm) {
		board.calcTargets(this.row, this.column, roll);
		BoardCell target = this.pickLocation(board.getTargets());
		if (unseenCards.size() == 3) {
			makeAccusation();
		}
		else {
			this.row = target.getRow(); //moves the computer player's location
			this.column = target.getColumn();
			if (target.isRoom()) {
				Card seen = board.handleSuggestion(createSuggestion(board.getLegend().get(target.getInitial())), this);
				this.processResponse(seen);
				controls.updateResponse(seen);
				controls.updateGuess();
			}
		}	
	}
	
	@Override
	public boolean isHuman() {
		return false;
	}
	
	public void setVisited(BoardCell room){
		lastRoom = room;
	}
	public void addUnseenCards(Card card) {
		unseenCards.add(card);
	}
	@Override
	public void dealCard(Card card) {
		this.seenCards.add(card);
		this.unseenCards.remove(card);
		this.myCards.add(card);
	}
	public void clearCards() {
		this.unseenCards.clear();
		this.seenCards.clear();
	}
	public ArrayList<Card> getUnseen() {
		return unseenCards;
	}
	public void processResponse(Card c) {
		this.seenCards.add(c);
		this.unseenCards.remove(c);
	}
	
}
