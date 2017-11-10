import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
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
	 * handleAccustion: BOARD TESTS
	 * 
	 * 1. Checks that the board disproves a false accusation with a wrong person
	 * 2. Checks that the board disproves a false accusation with a wrong weapon
	 * 3. Checks that the board disproves a false accusation with a wrong room
	 * 4. Checks that the board approves a correct accusation
	 * 
	 */
	@Test
	public void handleAccusationTests() {
		ComputerPlayer testPlayer = new ComputerPlayer("Educator Emerald");
		//1. Checks that the board disproves a false accusation with a wrong person
		assertTrue(!board.checkAccusation(new Solution("person", board.getSolution().room, board.getSolution().weapon), testPlayer));
		//2. Checks that the board disproves a false accusation with a wrong weapon
		assertTrue(!board.checkAccusation(new Solution(board.getSolution().person, board.getSolution().room, "weapon"), testPlayer));
		//3. Checks that the board disproves a false accusation with a wrong weapon
		assertTrue(!board.checkAccusation(new Solution(board.getSolution().person, "room", board.getSolution().weapon), testPlayer));
		//4. Checks that the board approves a correct accusation
		assertTrue(board.checkAccusation(board.getSolution(), testPlayer));
	}
	
	/**
	 * disproveSuggestion: COMPUTER TESTS
	 * 
	 * 1. Check it disproves the suggestion with a weapon
	 * 2. Check it disproves the suggestion with a room
	 * 3. Check it disproves the suggestion with a person
	 * 4. Check it disproves the suggestion with multiple cards
	 * 5. Does not disprove the suggestion when doesn't have the cards
	 */
	@Test
	public void disproveSuggestionComputerTests() {
		ComputerPlayer testPlayer = new ComputerPlayer("Educator Emerald");
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
		Card shownCard = testPlayer.disproveSuggestion(new Solution ("Lawyer Lavender", "Fork", "Ballroom"));
		assertTrue(shownCard == room || shownCard == weapon || shownCard == person);
		
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
	public void pickingTargetsComputerTests() {
		ComputerPlayer testComputer = new ComputerPlayer("Captain Cardinal");
		testComputer.setRow(5);
		testComputer.setColumn(10);
		board.calcTargets(5, 10, 1);
		
		//1. Tests that it chooses a room that hasn't been visited
		assertEquals(board.getCellAt(4, 10), testComputer.pickLocation(board.getTargets()));
		
		//2. Tests that it randomly chooses target when there is a room (will sometimes failed if randomly choose the same target in the second call)
		board.calcTargets(5, 10, 4);
		testComputer.setVisited(board.getCellAt(4,10));
		assertTrue(testComputer.pickLocation(board.getTargets())!=testComputer.pickLocation(board.getTargets()));

		//3.Tests that it randomly chooses target when there isn't a room (will sometimes failed if randomly choose the same target in the second call)
		board.calcTargets(9, 15, 3);
		assertTrue(testComputer.pickLocation(board.getTargets())!=testComputer.pickLocation(board.getTargets()));		
	}
	
	/**
	 * createSuggestion: COMPUTER TESTS
	 * 
	 * 1. Tests that created suggestion is in the room the computer is currently in
	 * 2. Tests that created suggestion is not based on cards already seen
	 * 3. Tests that created suggestion is based on card not already seen
	 * 
	 */
	@Test
	public void createSuggestionComputerTests() {
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
		Card seenWeapon1 = new Card("Fork", CardType.WEAPON);
		Card seenPerson1 = new Card("Lawyer Lavender", CardType.PERSON);
		Card seenWeapon2 = new Card("Lava Lamp", CardType.WEAPON);
		Card seenPerson2 = new Card("Farmer Flax", CardType.PERSON);
		//Tests using cards that are currently in the computer's hand and cards that have been "seen" from others
		testComputer.dealCard(seenWeapon1);
		testComputer.dealCard(seenPerson1);
		testComputer.addSeenCards(seenWeapon2);
		testComputer.addSeenCards(seenPerson2);
		//These are the cards the computer can suggest
		Card unseenWeapon1 = new Card("Poison", CardType.WEAPON);
		Card unseenWeapon2 = new Card("Taser", CardType.WEAPON);
		Card unseenPerson1 = new Card("Doctor Dandelion", CardType.PERSON);
		Card unseenPerson2 = new Card("Educator Emerald", CardType.PERSON);
		testComputer.addUnseenCards(unseenWeapon1);
		testComputer.addUnseenCards(unseenPerson1);

		//Tests that the seen cards are not used in the suggestion
		Solution testSolution = testComputer.createSuggestion(room);
		assertTrue(testSolution.weapon != seenWeapon1.getCardName());
		assertTrue(testSolution.person != seenPerson1.getCardName());
		assertTrue(testSolution.weapon != seenWeapon2.getCardName());
		assertTrue(testSolution.person != seenPerson2.getCardName());
		
		

		//3. Tests that created suggestion is based on card not already seen
		testSolution = testComputer.createSuggestion(room);
		assertTrue(testComputer.createSuggestion(room).weapon == unseenWeapon1.getCardName());
		assertTrue(testComputer.createSuggestion(room).person == unseenPerson1.getCardName());
		testComputer.addUnseenCards(unseenPerson2);
		testComputer.addUnseenCards(unseenWeapon2);
		
		//Tests that the unseen cards are randomly used in the suggestion
		testSolution = testComputer.createSuggestion(room);
		assertTrue((testSolution.weapon == unseenWeapon1.getCardName()) || (testSolution.weapon == unseenWeapon2.getCardName()));
		assertTrue((testSolution.person == unseenPerson1.getCardName()) || (testSolution.person == unseenPerson2.getCardName()));
		
		

	}
	
	/**
	 * handleSuggestion: BOARD TESTS
	 * 
	 * 1. Tests a suggestion no one can disprove
	 * 2. Tests a suggestion only the current player can disprove
	 * 3. Tests a suggestion only the human player can disprove
	 * 4. Tests a suggestion only the human player can disprove, who is also the current player
	 * 5. Tests a suggestion, disproved by next player in list, not someone further
	 * 6. Tests a suggestion, disproved by next computer player not human
	 */
	@Test
	public void handleSuggestionBoardTests() {
		Solution testSuggestion = new Solution("Preacher Periwinkle", "Icicle", "Workshop");
		Card suggestPerson = new Card("Preacher Periwinkle", CardType.PERSON);
		Card suggestWeapon = new Card("Icicle", CardType.WEAPON);
		Card suggestRoom = new Card("Workshop", CardType.ROOM);
		//creates a smaller group of players to test with
		HumanPlayer human = new HumanPlayer("Preacher Periwinkle");
		ComputerPlayer com1 = new ComputerPlayer("Farmer Flax");
		ComputerPlayer com2 = new ComputerPlayer("Lawyer Lavender");
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(human);
		players.add(com1);
		players.add(com2);
		/*players.add(new ComputerPlayer("Doctor Dandelion"));
		players.add(new ComputerPlayer("Educator Emerald"));
		players.add(new ComputerPlayer("Captain Cardinal"));*/
		board.setPlayers(players);
		//1. Tests a suggestion no one can disprove
		assertEquals(null, board.handleSuggestion(testSuggestion, com1));
		
		//2. Tests a suggestion only the current player can disprove
		com1.dealCard(suggestPerson);
		assertEquals(null, board.handleSuggestion(testSuggestion, com1));
		
		//3. Tests a suggestion only the human player can disprove
		com1.clearHand();
		human.dealCard(suggestWeapon);
		assertEquals(suggestWeapon, board.handleSuggestion(testSuggestion, com1));
		
		//4. Tests a suggestion only the human player can disprove, who is also the current player
		assertEquals(null, board.handleSuggestion(testSuggestion, human));
		
		//5. Tests a suggestion, disproved by next player in list, not someone further
		human.clearHand();
		com1.dealCard(suggestRoom);
		com2.dealCard(suggestWeapon);
		assertEquals(suggestRoom, board.handleSuggestion(testSuggestion, human));
		
		//6. Tests a suggestion, disproved by next computer player not human
		com2.clearHand();
		com1.clearHand();
		human.dealCard(suggestPerson);
		com2.dealCard(suggestWeapon);
		assertEquals(suggestWeapon, board.handleSuggestion(testSuggestion, com1));
		
		
		
	}
}
