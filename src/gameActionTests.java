import static org.junit.Assert.*;

import java.io.IOException;


import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

public class gameActionTests {
	private static Board board;

	@BeforeClass
	public static void setUp() throws BadConfigFormatException, IOException {
		board = Board.getInstance();
		board.setConfigFiles("Clue Layout.csv", "ClueLegend.txt"); //layout file, legend file
		board.setCardFiles("Weapons.txt", "Players.txt");
		board.initialize();
	}
	
	/**
	 * handleAccustion: Board TESTS
	 * 
	 * 1. Checks that the board disproves a false accusation
	 * 2. Checks that the board approves a correct accusation
	 * 
	 */
	@Test
	public void handleAccusationTests() {
		//1. Checks that the board disproves a false accusation
		assertTrue(!board.checkAccusation(new Solution("person", "room", "weapon")));
		//2. Checks that the board approves a correct accusation
		assertTrue(board.checkAccusation(board.getSolution()));
	}
	
	/**
	 * handleSuggestion: Player TESTS
	 * 
	 * 1. Check it disproves the suggestion with a weapon
	 * 2. Check it disproves the suggestion with a room
	 * 3. Check it disproves the suggestion with a person
	 * 4. Check it disproves the suggestion with multiple cards
	 * 5. Does not disprove the suggestion when doesn't have the cards
	 */
	@Test
	public void handleSuggestionPlayerTests() {
		Player testPlayer = board.getPlayers().get(0);
		//Solution testSuggestion = new Solution("Lawyer Lavender", "Fork", "Ballroom");
		testPlayer.clearHand();
		Card person = new Card("Lawyer Lavender", CardType.PERSON);
		Card room = new Card("Ballroom", CardType.ROOM);
		Card weapon = new Card("Fork", CardType.WEAPON);
		testPlayer.dealCard(person);
		testPlayer.dealCard(room);
		testPlayer.dealCard(weapon);
		
		//1. Check it disproves the suggestion with a weapon
		assertEquals(weapon, testPlayer.disproveSuggestion(new Solution ("Farmer Flax", "Fork", "Kitchen")));
		
		//2. Check it disproves the suggestion with a room
		assertEquals(room, testPlayer.disproveSuggestion(new Solution ("Farmer Flax", "Rubber Mallet", "Ballroom")));
		
		//3. Check it disproves the suggestion with a person
		assertEquals(person, testPlayer.disproveSuggestion(new Solution ("Lawyer Lavender", "Rubber Mallet", "Kitchen")));
		
		//4. Check it disproves the suggestion with multiple cards (any card will do)
		assertTrue(testPlayer.disproveSuggestion(new Solution ("Lawyer Lavender", "Fork", "Ballroom"))== room || testPlayer.disproveSuggestion(new Solution ("Lawyer Lavender", "Fork", "Ballroom"))== weapon || testPlayer.disproveSuggestion(new Solution ("Lawyer Lavender", "Fork", "Ballroom"))== person);
		
		//5. Does not disprove the suggestion when doesn't have the cards
		assertEquals(null, testPlayer.disproveSuggestion(new Solution ("Farmer Flax", "Rubber Mallet", "Kitchen")));
		
	}
	
	/**
	 * pickingTargets: COMPUTER TESTS
	 * 
	 * 1. Tests that it chooses a room that hasn't been visited
	 * 2. Tests that it randomly chooses target when there is a room
	 * 3. Tests that it randomly chooses target when there isn't a room
	 */
	@Test
	public void pickingTargets() {
		ComputerPlayer testComputer = new ComputerPlayer("Captain Cardinal");
		testComputer.setRow(5);
		testComputer.setColumn(10);
		board.calcTargets(5, 10, 1);
		
		//1. Tests that it chooses a room that hasn't been visited
		assertEquals(board.getCellAt(4, 10), testComputer.pickLocation(board.getTargets()));
		
		//2. Tests that it randomly chooses target when there is a room (will sometimes failed if randomly choose the same room in the second call)
		testComputer.setVisited(board.getCellAt(4,10));
		assertTrue(testComputer.pickLocation(board.getTargets())!=testComputer.pickLocation(board.getTargets()));

		//3.Tests that it randomly chooses target when there isn't a room (will sometimes failed if randomly choose the same room in the second call)
		board.calcTargets(9, 15, 2);
		assertTrue(testComputer.pickLocation(board.getTargets())!=testComputer.pickLocation(board.getTargets()));		
	}
	

}
