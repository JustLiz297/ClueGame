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
	public void pickingTargetsComputerTest() {
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
	
	/**
	 * createSuggestion: COMPUTER TESTS
	 * 
	 * 1. Tests that created suggestion is in the room the computer is currently in
	 * 2. Tests that created suggestion is based on cards the computer has not seen
	 * 
	 */
	@Test
	public void createSuggestionComputerTest() {
		ComputerPlayer testComputer = new ComputerPlayer("Educator Emerald");
		
		//1. Tests that created suggestion is in the room the computer is currently in
		//Tests BoardCell 16,19 which is the Office
		testComputer.setRow(16);
		testComputer.setColumn(19);
		assertEquals(board.getLegend().get(board.getCellAt(16, 19).getInitial()),testComputer.createSuggestion(board.getLegend().get(board.getCellAt(16, 19).getInitial())).room);
		
		//Tests BoardCell 1,7 which is the Kitchen
		testComputer.setRow(1);
		testComputer.setColumn(7);
		assertEquals(board.getLegend().get(board.getCellAt(1, 7).getInitial()),testComputer.createSuggestion(board.getLegend().get(board.getCellAt(1, 7).getInitial())).room);
		
		//Tests BoardCell 16,19 which is the Family Room
		testComputer.setRow(18);
		testComputer.setColumn(6);
		assertEquals(board.getLegend().get(board.getCellAt(18, 6).getInitial()),testComputer.createSuggestion(board.getLegend().get(board.getCellAt(18, 6).getInitial())).room);
		
		//2. Tests that created suggestion is based on cards the computer has not seen
		String room = board.getLegend().get(board.getCellAt(18, 6).getInitial());
		testComputer.clearCards();
		testComputer.clearHand();
		//These are the cards the computer should NOT suggest
		Card seenWeapon = new Card("Fork", CardType.WEAPON);
		Card seenPerson = new Card("Lawyer Lavender", CardType.PERSON);
		testComputer.dealCard(seenWeapon);
		testComputer.dealCard(seenPerson);
		//These are the cards the computer can suggest
		Card unseenWeapon1 = new Card("Poison", CardType.WEAPON);
		Card unseenWeapon2 = new Card("Taser", CardType.WEAPON);
		Card unseenPerson1 = new Card("Doctor Dandelion", CardType.PERSON);
		Card unseenPerson2 = new Card("Educator Emerald", CardType.PERSON);
		testComputer.addUnseenCards(unseenWeapon1);
		testComputer.addUnseenCards(unseenWeapon2);
		testComputer.addUnseenCards(unseenPerson1);
		testComputer.addUnseenCards(unseenPerson2);
		
		//Tests that the seen cards are not used in the suggestion
		assertTrue(testComputer.createSuggestion(room).weapon != seenWeapon.getCardName());
		assertTrue(testComputer.createSuggestion(room).person != seenPerson.getCardName());
		
		//Tests that the unseen cards are used in the suggestion
		assertTrue(testComputer.createSuggestion(room).weapon == unseenWeapon1.getCardName() || testComputer.createSuggestion(room).weapon == unseenWeapon2.getCardName());
		assertTrue(testComputer.createSuggestion(room).person == unseenPerson1.getCardName() || testComputer.createSuggestion(room).person == unseenPerson2.getCardName());
		
		

	}
	
	//set the computer's card to certain seen and unseen ones, make sure the unseen are in the suggest, and test the seen are not in the suggest
}
