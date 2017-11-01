package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player{

	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName);
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		return null;
	}
	
	public void makeAccusation() {
		
	}

	public void createSuggestion () {
		
	}
}
