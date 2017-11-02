package tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.awt.Color;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.Player;

public class gameSetupTests {
	private static Board board;

	@BeforeClass
	public static void setUp() throws BadConfigFormatException, IOException {
		board = Board.getInstance();
		board.setConfigFiles("Clue Layout.csv", "ClueLegend.txt"); //layout file, legend file
		board.setCardFiles("Weapons.txt", "Players.txt");
		board.initialize();
	}
	@Test
	public void CardTests() {
		int rooms = 0;
		int people = 0;
		int weapons = 0;
		for (Card c : board.getCardDeck()) {
			if (c.isRoom()) {rooms++;}
			else if (c.isPerson()) {people++;}
			else if (c.isWeapon()) {weapons++;}
		}
		assertEquals(9, board.getRoomList().size());
		//System.out.println(board.getCardDeck());
		assertEquals(21,board.getCardDeck().size());
		assertEquals(9, rooms);//checks to make sure there is the correct number of room cards
		assertEquals(6, people);
		assertEquals(6, weapons);

	}

	//player list tests
	//length
	//correct color
	//correct position
	
	@Test
	public void PlayerTests() {
		//checks for the correct number of players
		assertEquals(6, board.getPlayers().size());
		
		//checks starting position of each player & color
		for (Player x : board.getPlayers()) {
			switch(x.getPlayerName()) {
			case "Preacher Perwinkle":
				assertEquals(0, x.getRow());
				assertEquals(14, x.getColumn());
				//assertEquals(Color.BLUE, x.getColor());
				assertEquals(new Color(108,156,239), x.getColor());
				break;
			case "Doctor Dandelion":
				assertEquals(6, x.getRow());
				assertEquals(0, x.getColumn());
				assertEquals(new Color(240,225,48), x.getColor());
				break;
			case "Lawyer Lavender":
				assertEquals(15, x.getRow());
				assertEquals(24, x.getColumn());
				assertEquals(new Color(255,245,251), x.getColor());
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
				assertEquals(new Color(238,220,130), x.getColor());
				break;
			}
		}
	}
	
	//Dealing Tests
	//random shuffle
	//select solution
	//deals out cards
	@Test
	public void DealingTests() {
		//checks that solution room is a valid room
		assertTrue(board.getLegend().containsValue(board.getSolution().room));
		//checks that solution room is not in shuffled deck
		
		//checks that solution person is a valid person
		assertTrue(board.getPlayerList().contains(board.getSolution().person));
		//checks that solution person is not in shuffled deck
		
		//checks that solution weapon is a valid weapon
		assertTrue(board.getWeaponsList().contains(board.getSolution().weapon));
		//checks that weapon is not in shuffled deck
		
		
		
	}
}
