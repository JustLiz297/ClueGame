package tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;

public class gameSetupTests {
	private static Board board;

	@Before
	public void setUp() throws BadConfigFormatException, IOException {
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
		assertEquals(9, rooms);//checks to make sure there is the correct number of room cards
		//assertEquals(6, people);
		assertEquals(6, weapons);
		assertEquals(board.getCardDeck().size(), 21);
	}

		

}
