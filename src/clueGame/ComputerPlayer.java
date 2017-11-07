package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	private BoardCell lastRoom = null;

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

	public Solution createSuggestion() {
		String room = "";
		String person = "";
		String weapon = "";
		
		//Room of the Suggestion must be the current room the player is in?
		
		
		
		return new Solution(person, room, weapon);
	}

	@Override
	public boolean isHuman() {
		return false;
	}
	
	public void setVisited(BoardCell room){
		lastRoom = room;
	}
}
