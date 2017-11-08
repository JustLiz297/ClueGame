package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	private BoardCell lastRoom = null;
	private ArrayList<Card> unseenCards = new ArrayList<Card>();

	public ComputerPlayer(String playerName) {
		super(playerName);
	}
	
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
		
	}

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

	@Override
	public boolean isHuman() {
		return false;
	}
	
	public void setVisited(BoardCell room){
		lastRoom = room;
	}
	public void setUnseenCards(ArrayList<Card> deck) {
		unseenCards = deck;
	}
	public void addUnseenCards(Card card) {
		unseenCards.add(card);
	}
	public void dealCard(Card card) {
		this.seenCards.add(card);
		this.unseenCards.remove(card);
		this.myCards.add(card);
	}
	public void clearCards() {
		this.unseenCards.clear();
		this.seenCards.clear();
	}
	
}
