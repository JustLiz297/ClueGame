package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import java.awt.Color;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.Player;
import clueGame.Solution;


/**
 * These are the tests for the game setup
 * @author eboyle, annelysebaker
 *
 */
public class gameSetupTests {
	private static Board board;

	@BeforeClass
	public static void setUp() throws BadConfigFormatException, IOException {
		board = Board.getInstance();
		board.setConfigFiles("Clue Layout.csv", "ClueLegend.txt"); //layout file, legend file
		board.setCardFiles("Weapons.txt", "Players.txt");
		board.initialize();
	}
	
	/**
	 *  CARD TESTS
	 *  -Tests the initial deck set up
	 *  
	 *  1. Tests that there are 9 rooms in the Room List
	 *  2. Tests that there are 21 cards in the deck
	 *  3. Tests that there are 9 ROOM cards in the deck
	 *  4. Tests that there are 6 PERSON cards in the deck
	 *  5. Tests that there are 6 WEAPON cards in the deck
	 */
	@Test
	public void CardTests() {
		int rooms = 0;
		int people = 0;
		int weapons = 0;
		//goes through the card deck, counting each type of card
		for (Card c : board.getCardDeck()) {
			if (c.isRoom()) {rooms++;}
			else if (c.isPerson()) {people++;}
			else if (c.isWeapon()) {weapons++;}
		}
		//1. Tests that there are 9 rooms in the Room List
		assertEquals(9, board.getRoomList().size());
		//2. Tests that there are 21 cards in the deck
		assertEquals(21,board.getCardDeck().size());
		//3. Tests that there are 9 ROOM cards in the deck
		assertEquals(9, rooms);
		//4. Tests that there are 6 PERSON cards in the deck
		assertEquals(6, people);
		//5. Tests that there are 6 WEAPON cards in the deck
		assertEquals(6, weapons);

	}

	/**
	 * PLAYER TESTS
	 * -Tests that the Players are set up correctly
	 * 
	 * 1. Tests that there are 6 loaded players
	 * 2. Tests that the players are initialized in the correct position on the board
	 * 3. Tests that the players are loaded with their correct color
	 * 4. Tests that there are only 1 Human Player and the rest are Computer
	 */
	@Test
	public void PlayerTests() {
		//1. Tests that there are 6 loaded players
		assertEquals(6, board.getPlayers().size());
		
		//2. Tests that the players are initialized in the correct position on the board
		//3. Tests that the players are loaded with their correct color
		for (Player x : board.getPlayers()) {
			switch(x.getPlayerName()) {
			case "Preacher Periwinkle":
				assertEquals(0, x.getRow());
				assertEquals(14, x.getColumn());
				//assertEquals(Color.BLUE, x.getColor()); for if we can't use our own colors
				assertEquals(new Color(108,156,239), x.getColor());
				break;
			case "Doctor Dandelion":
				assertEquals(6, x.getRow());
				assertEquals(0, x.getColumn());
				assertEquals(new Color(221,179,8), x.getColor());
				break;
			case "Lawyer Lavender":
				assertEquals(15, x.getRow());
				assertEquals(24, x.getColumn());
				assertEquals(new Color(216,206,242), x.getColor());
				break;
			case "Educator Emerald":
				assertEquals(20, x.getRow());
				assertEquals(7, x.getColumn());
				assertEquals(new Color(9,69,60), x.getColor());
				break;
			case "Captain Cardinal":
				assertEquals(20, x.getRow());
				assertEquals(19, x.getColumn());
				assertEquals(new Color(189,32,49), x.getColor());
				break;
			case "Farmer Flax":
				assertEquals(16, x.getRow());
				assertEquals(0, x.getColumn());
				assertEquals(new Color(241,235,190), x.getColor());
				break;
			}
		}
		
		//4. Tests that there are only 1 Human Player and the rest are Computer
		int h = 0;
		int c = 0;
		for (Player p : board.getPlayers()) {
			if (p.isHuman()) {
				h++;
			}
			else {
				c++;
			}
		}
		assertEquals(5, c);
		assertEquals(1, h);
	}
	/**
	 * SOLUTION TESTS
	 * -Tests the solution set-up
	 * 
	 * 1. Tests that solution room is a valid room in the legend
	 * 2. Tests that solution room is not in the shuffled deck
	 * 3. Tests that solution person is a valid person in the player list
	 * 4. Tests that solution person is not in the shuffled deck
	 * 5. Tests that solution weapon is a valid weapon in the weapons list
	 * 6. Tests that solution weapon is not in shuffled deck
	 */

	@Test
	public void SolutionTests() {
		//1. Tests that solution room is a valid room in the legend
		assertTrue(board.getLegend().containsValue(board.getSolution().room));
		//2. Tests that solution room is not in the shuffled deck
		assertTrue(!board.getShuffledDeck().contains(board.getSolution().room));
		
		//3. Tests that solution person is a valid person in the player list
		assertTrue(board.getPlayerList().contains(board.getSolution().person));
		//4. Tests that solution person is not in the shuffled deck
		assertTrue(!board.getShuffledDeck().contains(board.getSolution().person));
		
		//5. Tests that solution weapon is a valid weapon in the weapons list
		assertTrue(board.getWeaponsList().contains(board.getSolution().weapon));
		//6. Tests that solution weapon is not in shuffled deck
		assertTrue(!board.getShuffledDeck().contains(board.getSolution().weapon));
		

	}
	
	/**
	 * DEALING TESTS
	 * -Tests that the cards were dealt properly
	 * 
	 * 1. Tests that the total number of cards were dealt and there aren't doubles
	 * 2. Tests that the shuffled deck is full of cards that were in the initial deck
	 * 3. Tests if one player has more than 2 more cards than another, to make sure each player got roughly the same amount of cards
	 */
	@Test
	public void DealingTests() {
		//1. Tests that the total number of cards were dealt and there aren't doubles
		int temp = 0;
		for (Player x : board.getPlayers()) {
			temp += x.getCards().size();
		}
		assertEquals(18, temp);

		//2. Tests that the shuffled deck is full of cards that were in the initial deck
		assertTrue(board.getCardDeck().containsAll(board.getShuffledDeck()));
		
		//3. Tests if one player has more than 2 more cards than another, to make sure each player got roughly the same amount of cards
		int current = 0;
		for (int i = 0; i < 6; i++) {
			if (i == 0) {
				current = board.getPlayers().get(0).getCards().size();
			}
			assertFalse(Math.abs(board.getPlayers().get(i).getCards().size()-current) > 1);
		}
	}
	/**
	 * RANDOMNESS TESTS
	 * 
	 * 1. Tests that the Players are in a random order each initialization
	 * 2. Tests that the Solution is random each initialization
	 * 3. Tests that the Cards are dealt randomly each initialization (by comparing a certain player each call)
	 */
	@Test
	public void RandomnessTest() {
		ArrayList<String> firstPlayers = board.getPlayerList();
		Solution firstSolution = board.getSolution();
		Player firstPlayer = board.getPlayers().get(0);
		ArrayList<Card> firstPlayerDealt = firstPlayer.getCards();
		
		//re-initialize
		try {
			board.initialize();
		}catch(BadConfigFormatException b) {System.out.println(b.getMessage());}
		
		ArrayList<String> secondPlayers = board.getPlayerList();
		Solution secondSolution = board.getSolution();
		Player firstPlayer2 = board.getPlayers().get(0);
		for (int i = 0; i < 6; i++) {
			if (board.getPlayers().get(i).getPlayerName() == firstPlayer.getPlayerName()) {
				firstPlayer2 = board.getPlayers().get(i); 
			}
		}
		ArrayList<Card> secondPlayerDealt = firstPlayer2.getCards();
		//1. Tests that the Players are in a random order each initialization
		assertTrue(secondPlayers != firstPlayers);
		//2. Tests that the Solution is random each initialization
		assertTrue(firstSolution != secondSolution);
		//3. Tests that the Cards are dealt randomly each initialization (by comparing a certain player each call)
		assertTrue(firstPlayerDealt != secondPlayerDealt);
	}
	
}
