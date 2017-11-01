/*
 * Authors: Elizabeth (Liz) Boyle, Annelyse Baker
 * Description: Tests for Board class, descriptions of tests below
 */



package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

/**
 * This is the tests for the Board class
 * @author eboyle, annelysebaker
 * @version 1.4
 */
public class BoardTests {
	private static Board board;
	public static final int NUM_ROWS = 21; //number of rows for our board
	public static final int NUM_COLS = 25; //number of columns for our board
	public static final int LEGEND_SIZE = 11; //our correct legend size (correct number of types of spaces)
	
	/**
	 * Tells which files to use for set up, and sets up the board and legend with them
	 * @throws BadConfigFormatException
	 * @throws IOException
	 */
	@BeforeClass
	public static void setUp() throws BadConfigFormatException, IOException {
		board = Board.getInstance();
		board.setConfigFiles("Clue Layout.csv", "ClueLegend.txt"); //layout file, legend file
		board.setCardFiles("Weapons.txt", "Players.txt");
		board.initialize();
	}
	

	/**
	 * 	 LEGEND TEST
	 *  	1. Tests if the loaded legend is the correct length
	 *  	2. Tests if the correct room name is at the correct character key
	 */
	@Test
	public void testLegend() {
		Map<Character, String> legend = board.getLegend();
		//1. Tests if the loaded legend is the correct length
		assertEquals(LEGEND_SIZE, legend.size());
		//2. Tests if the correct room name is at the correct character key
		assertEquals("Kitchen", legend.get('K'));
		assertEquals("Entrance", legend.get('E'));
		assertEquals("Office", legend.get('O'));
		assertEquals("Ballroom", legend.get('B'));
		assertEquals("Dining Room", legend.get('D'));
		assertEquals("Green House", legend.get('G'));
		assertEquals("Family Room", legend.get('F'));
		assertEquals("Library", legend.get('L'));
		assertEquals("Workshop", legend.get('S'));
		assertEquals("Closet", legend.get('C'));
		assertEquals("Hallways", legend.get('W'));
		
	}
	
	/**
	 *  DIMENSION TEST
	 *  	1. Tests if the loaded game board has the correct number of rows
	 *  	2. Tests if the loaded game board has the correct number of columns
	 */
	@Test
	public void testBoardDimensions() {
		//1. Tests if the loaded game board has the correct number of rows
		assertEquals(NUM_ROWS, board.getNumRows());
		//2. Tests if the loaded game board has the correct number of columns
		assertEquals(NUM_COLS, board.getNumColumns());
		
	}
	
	/**
	 * DOOR DIRECTION TEST
	 * 		1. Tests (4,21) to be a doorway and left entering
	 * 		2. Tests (18,6) to be a doorway and right entering
	 * 		3. Tests (9,19) to be a doorway and up entering
	 * 		4. Tests (4,10) to be a doorway and down entering
	 * 		5. Tests (11,15) to not be a doorway
	 */
	@Test
	public void testDoorDirections() {
		//1. Tests (4,21) to be a doorway and left entering
		BoardCell space = board.getCellAt(4, 21);
		assertTrue(space.isDoorway());
		assertEquals(DoorDirection.LEFT, space.getDoorDirection());
		
		//2. Tests (18,6) to be a doorway and right entering
		space = board.getCellAt(18, 6);
		assertTrue(space.isDoorway());
		assertEquals(DoorDirection.RIGHT, space.getDoorDirection());
		
		//3. Tests (9,19) to be a doorway and up entering
		space = board.getCellAt(9, 19);
		assertTrue(space.isDoorway());
		assertEquals(DoorDirection.UP, space.getDoorDirection());
		
		//4. Tests (4,10) to be a doorway and down entering
		space = board.getCellAt(4, 10);
		assertTrue(space.isDoorway());
		assertEquals(DoorDirection.DOWN, space.getDoorDirection());
		
		//5. Tests (11,15) to not be a doorway
		space = board.getCellAt(11, 15);
		assertFalse(space.isDoorway());
		assertEquals(DoorDirection.NONE, space.getDoorDirection());
	}
	
	/**
	 * NUMBER OF DOORS TEST
	 * 		-Tests amount of doors
	 */
	@Test
	public void testNumDoors() {
		int numDoors = 0;
		for (int row = 0; row < board.getNumRows(); row++ ) {
			for (int col = 0; col < board.getNumColumns(); col++) {
				BoardCell space = board.getCellAt(row, col);
				if (space.isDoorway()) {
					numDoors++;
				}
				
			}
		}
		assertEquals(18, numDoors);
	}
	
	/**
	 *  GAME BOARD SET UP TEST
	 *  	1. Test that first cell (0,0) is 'K'
	 *  	2. Test that last cell (20,24) is 'O'
	 *  	3. Test that (17,0) is 'F' (First cell of room)
	 *  	4. Test that (12, 12) is 'C' (Closet)
	 *  	5. Test that (14, 18) is 'W' (Hallway)
	 *  	6. Test that (20,12) is 'L' (Last cell of room)
	 *  	7. Test that (8, 20) is 'E' (First cell of room)
	 *  	8. Test that (4, 12) is 'D" (Last cell of room)
	 */
	@Test
	public void testRoomChar() {
		//1. Test that first cell (0,0) is 'K'
		assertEquals('K', board.getCellAt(0, 0).getInitial());
		//2. Test that last cell (20,24) is 'O'
		assertEquals('O', board.getCellAt(20, 24).getInitial());
		//3. Test that (17,0) is 'F' (First cell of room)
		assertEquals('F', board.getCellAt(17, 0).getInitial());
		//4. Test that (12, 12) is 'C' (Closet)
		assertEquals('C', board.getCellAt(12, 12).getInitial());
		//5. Test that (14, 18) is 'h' (Hallway)
		assertEquals('W', board.getCellAt(14, 18).getInitial());
		//6. Test that (20,12) is 'L' (Last cell of room)
		assertEquals('L', board.getCellAt(20, 12).getInitial());
		//7. Test that (8, 20) is 'E' (First cell of room)
		assertEquals('E', board.getCellAt(8, 20).getInitial());
		//8. Test that (4, 12) is 'D" (Last cell of room)
		assertEquals('D', board.getCellAt(4, 12).getInitial());
	}

	/**
	 * ADJACENCY TESTS
	 * 		ORANGE:
	 * 		1. Cell (6,0) has adjacency list of [(6,1)] 
	 * 			(Note: edge of the board)
	 *	 	2. Cell (16, 12) has adjacency list of [(15,12), (16,13)]
	 *		3. Cell (9, 15) has adjacency list of [(9,16), (9,14), (8,15), (10,15)]
	 *		GREEN:
	 * 		4. Cell (11, 4) should have empty adjacency list 
	 * 			(Note: is a room)
	 * 		TEAL:
	 * 		5. Cell (4, 16) has adjacency list of [(5, 16)] 
	 * 			(Note: is a doorway)
	 * 		6. Cell (17, 14) has adjacency list of [(16,14)]
	 * 			(Note: is a doorway)
	 * 		PINK:
	 * 		7. Cell (14, 21) has adjacency list of [(15,21), (13,21), (14,20), (14,22)] 
	 * 			(Note: (15, 21) is an UP doorway)
	 * 		8. Cell (10, 18) has adjacency list of [(9,18), (11,18), (10,19), (10,17)]
	 * 			(Note: (10, 19) is a LEFT doorway)
	 * 		9. Cell (5, 10) has adjacency list of [(5,9), (5,11), (4, 10), (6,10)]
	 * 			(Note: (4,10) is a DOWN doorway)
	 * 		10. Cell (18, 7) has adjacency list of [(19,7), (17,7), (18,6), (18,8)]
	 * 			(Note: (18,6) is a RIGHT doorway)
	 * 		RED:
	 * 		11. Cell (6, 21) has adjacency list of [(6,20), (7,21), (6,22)]
	 * 			(Note: Should not include (5,21) since it is a LEFT doorway)
	 * 		12. Cell (9, 18) has adjacency list of [(10,18), (9,17), (8,18)]
	 * 			(Note: Should not include (9,19) since it is an UP doorway)
	 */
	@Test
	public void testAdjacencyOrange() {
		//ORANGE TESTS: WALKWAYS
		//1. Cell (6,0) has adjacency list of [(6,1)] 
		//			(Note: edge of the board)
		Set<BoardCell> testList = board.getAdjList(6,0);
		assertTrue(testList.contains(board.getCellAt(6, 1)));
		assertEquals(1, testList.size());
		testList.clear();
		
		//2. Cell (16, 12) has adjacency list of [(15,12), (16,13)]
		testList = board.getAdjList(16,12);
		assertTrue(testList.contains(board.getCellAt(15, 12)));
		assertTrue(testList.contains(board.getCellAt(16, 13)));
		assertEquals(2, testList.size());
		testList.clear();
		
		//3. Cell (9, 15) has adjacency list of [(9,16), (9,14), (8,15), (10,15)]
		testList = board.getAdjList(9,15);
		assertTrue(testList.contains(board.getCellAt(9, 16)));
		assertTrue(testList.contains(board.getCellAt(9, 14)));
		assertTrue(testList.contains(board.getCellAt(8, 15)));
		assertTrue(testList.contains(board.getCellAt(10, 15)));
		assertEquals(4, testList.size());
	}
	@Test
	public void testAdjacencyGreen() {
		//GREEN TESTS: INSIDE ROOMS
		//4. Cell (11, 4) should have empty adjacency list 
		// 			(Note: is a room)
		Set<BoardCell> testList = board.getAdjList(11,4);
		assertEquals(0, testList.size());
	}
	@Test
	public void testAdjacencyTeal() {
		//TEAL TESTS: DOORWAYS
		//5. Cell (4, 16) has adjacency list of [(5, 16)] 
		//			(Note: is a doorway)
		Set<BoardCell> testList = board.getAdjList(4,16);
		assertTrue(testList.contains(board.getCellAt(5, 16)));
		assertEquals(1, testList.size());
		testList.clear();
		//6. Cell (17, 14) has adjacency list of [(16,14)]
		//			(Note: is a doorway)
		testList = board.getAdjList(17,14);
		assertTrue(testList.contains(board.getCellAt(16, 14)));
		assertEquals(1, testList.size());
	}
	@Test
	public void testAdjacencyPink() {
		//PINK TESTS: SPACE OUTSIDE OF DOOR (CAN ENTER)
		//7. Cell (14, 21) has adjacency list of [(15,21), (13,21), (14,20), (14,22)] 
		// 			(Note: (15, 21) is an UP doorway)
		Set<BoardCell> testList = board.getAdjList(14,21);
		assertTrue(testList.contains(board.getCellAt(15, 21)));
		assertTrue(testList.contains(board.getCellAt(13, 21)));
		assertTrue(testList.contains(board.getCellAt(14, 20)));
		assertTrue(testList.contains(board.getCellAt(14, 22)));
		assertEquals(4, testList.size());
		testList.clear();
		
		//8. Cell (10, 18) has adjacency list of [(9,18), (11,18), (10,19), (10,17)]
		// 			(Note: (10, 19) is a LEFT doorway)
		testList = board.getAdjList(10,18);
		assertTrue(testList.contains(board.getCellAt(9, 18)));
		assertTrue(testList.contains(board.getCellAt(11, 18)));
		assertTrue(testList.contains(board.getCellAt(10, 19)));
		assertTrue(testList.contains(board.getCellAt(10, 17)));
		assertEquals(4, testList.size());
		testList.clear();
		//9. Cell (5, 10) has adjacency list of [(5,9), (5,11), (4, 10), (6,10)]
		// 			(Note: (4,10) is a DOWN doorway)
		testList = board.getAdjList(5,10);
		assertTrue(testList.contains(board.getCellAt(5, 9)));
		assertTrue(testList.contains(board.getCellAt(5, 11)));
		assertTrue(testList.contains(board.getCellAt(4, 10)));
		assertTrue(testList.contains(board.getCellAt(6, 10)));
		assertEquals(4, testList.size());
		testList.clear();
		//10. Cell (18, 7) has adjacency list of [(19,7), (17,7), (18,6), (18,8)]
		// 			(Note: (18,6) is a RIGHT doorway)
		testList = board.getAdjList(18,7);
		assertTrue(testList.contains(board.getCellAt(19, 7)));
		assertTrue(testList.contains(board.getCellAt(17, 7)));
		assertTrue(testList.contains(board.getCellAt(18, 6)));
		assertTrue(testList.contains(board.getCellAt(18, 8)));
		assertEquals(4, testList.size());
	}
	@Test
	public void testAdjacencyRed() {
		//RED TESTS: SPACE OUTSIDE OF DOOR (NO ENTRY)
		//11. Cell (6, 21) has adjacency list of [(6,20), (7,21), (6,22)]
		//			(Note: Should not include (5,21) since it is a LEFT doorway)
		Set<BoardCell> testList = board.getAdjList(6,21);
		assertTrue(testList.contains(board.getCellAt(6, 20)));
		assertTrue(testList.contains(board.getCellAt(7, 21)));
		assertTrue(testList.contains(board.getCellAt(6, 22)));
		assertEquals(3, testList.size());
		testList.clear();
		//12. Cell (9, 18) has adjacency list of [(10,18), (9,17), (8,18)]
		//			(Note: Should not include (9,19) since it is an UP doorway)
		testList = board.getAdjList(9,18);
		assertTrue(testList.contains(board.getCellAt(10, 18)));
		assertTrue(testList.contains(board.getCellAt(9, 17)));
		assertTrue(testList.contains(board.getCellAt(8, 18)));
		assertEquals(3, testList.size());
	}
	
	/**
	 * TARGET TESTS
	 * 		BLUE: WALKWAYS
	 * 		1. Cell: (12, 16) Distance: 2
	 * 		List: [(10,16), (11,17), (11,15), (12,18), (12,14), (13,17), (13,15), (14,16)]
	 * 		Size: 8
	 * 
	 * 		2. Cell: (15, 4) Distance: 3
	 * 		List: [(14,2), (14,4), (14,6), (15,1), (15,3), (15,5), (15,7), (16,2), (16,4), (16,6), (17,3)] 
	 * 		Size: 11
	 * 		Note: (17,3) is an UP doorway
	 * 
	 * 		3. Cell: (0, 14) Distance: 4
	 * 		List: [(3,13)]
	 * 		Size: 1
	 * 		
	 * 		PURPLE: ENTERING ROOM
	 * 		4. Cell: (3, 19) Distance: 4
	 * 		List: [(4,20), (4,21), (5,17), (5,19), (5,21), (6,18), (6,20), (7,19)]
	 * 		Size: 8
	 * 		Note: (4,21) & (5,21) are LEFT doorways, (4,21) is shorter than the total roll
	 * 		
	 * 		5. Cell: (16, 18) Distance: 1
	 * 		List: [(15,18), (16,17), (17,18), (16,19)]
	 * 		Size: 4
	 * 		Note: (16,19) is a LEFT doorway
	 * 
	 *  	MAROON: EXITING ROOM
	 *  	6. Cell: (4, 5) Distance: 3
	 *  	List: [(4,8), (5,7), (6,6)]
	 *  	Size: 3
	 *  
	 */	
	@Test
	public void testTargetBlue() {
		//BLUE TESTS: WALKWAYS
		//1. Cell: (12, 16) Distance: 2
		board.calcTargets(12, 16, 2);
		Set<BoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCellAt(10, 16)));
		assertTrue(targets.contains(board.getCellAt(11, 17)));
		assertTrue(targets.contains(board.getCellAt(11, 15)));
		assertTrue(targets.contains(board.getCellAt(12, 18)));
		assertTrue(targets.contains(board.getCellAt(12, 14)));
		assertTrue(targets.contains(board.getCellAt(13, 17)));
		assertTrue(targets.contains(board.getCellAt(13, 15)));
		assertTrue(targets.contains(board.getCellAt(14, 16)));
		assertEquals(8, targets.size());
		targets.clear();
		//2. Cell: (15, 4) Distance: 3
		board.calcTargets(15, 4, 3);
		targets = board.getTargets();
		assertTrue(targets.contains(board.getCellAt(14, 2)));
		assertTrue(targets.contains(board.getCellAt(14, 4)));
		assertTrue(targets.contains(board.getCellAt(14, 6)));
		assertTrue(targets.contains(board.getCellAt(15, 1)));
		assertTrue(targets.contains(board.getCellAt(15, 3)));
		assertTrue(targets.contains(board.getCellAt(15, 5)));
		assertTrue(targets.contains(board.getCellAt(15, 7)));
		assertTrue(targets.contains(board.getCellAt(16, 2)));
		assertTrue(targets.contains(board.getCellAt(16, 4)));
		assertTrue(targets.contains(board.getCellAt(16, 6)));
		assertTrue(targets.contains(board.getCellAt(17, 3)));
		assertEquals(11, targets.size());
		targets.clear();
		//3. Cell: (0, 14) Distance: 4
		board.calcTargets(0, 14, 4);
		targets = board.getTargets();
		assertTrue(targets.contains(board.getCellAt(3, 13)));
		assertEquals(1, targets.size());
	}
	@Test
	public void testTargetPurple() {
		//PURPLE: ENTERING ROOM
		//4. Cell: (3, 19) Distance: 4
		board.calcTargets(3, 19, 4);
		Set<BoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCellAt(4, 20)));
		assertTrue(targets.contains(board.getCellAt(4, 21)));
		assertTrue(targets.contains(board.getCellAt(5, 17)));
		assertTrue(targets.contains(board.getCellAt(5, 19)));
		assertTrue(targets.contains(board.getCellAt(5, 21)));
		assertTrue(targets.contains(board.getCellAt(6, 18)));
		assertTrue(targets.contains(board.getCellAt(6, 20)));
		assertTrue(targets.contains(board.getCellAt(7, 19)));
		assertEquals(8, targets.size());
		targets.clear();
		//5. Cell: (16, 18) Distance: 1
		board.calcTargets(16, 18, 1);
		targets = board.getTargets();
		assertTrue(targets.contains(board.getCellAt(15, 18)));
		assertTrue(targets.contains(board.getCellAt(16, 17)));
		assertTrue(targets.contains(board.getCellAt(17, 18)));
		assertTrue(targets.contains(board.getCellAt(16, 19)));
		assertEquals(4, targets.size());
	}
	@Test
	public void testTargetMaroon() {
		//MAROON: EXITING ROOM
		//6. Cell: (4, 5) Distance: 3
		board.calcTargets(4, 5, 3);
		Set<BoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCellAt(4, 8)));
		assertTrue(targets.contains(board.getCellAt(5, 7)));
		assertTrue(targets.contains(board.getCellAt(6, 6)));
		assertEquals(3, targets.size());
	}
}
