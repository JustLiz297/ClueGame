/*
 * Authors: Elizabeth (Liz) Boyle, Annelyse Baker
 * Description: BoardTests Test if the board loaded correctly
 */

package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class BoardTests {
	private static Board board;
	public static final int NUM_ROWS = 21;
	public static final int NUM_COLS = 25;
	public static final int LEGEND_SIZE = 11;
	
	
	@Before
	public void setUp() throws BadConfigFormatException {
		board = Board.getInstance();
		board.setConfigFiles("Clue Layout.csv", "ClueLegend.txt"); //layout file, legend file
		board.initialize();
	}
	
	/*
	 *  LEGEND TEST
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
		assertEquals("Workshop", legend.get('W'));
		assertEquals("Closet", legend.get('C'));
		assertEquals("Hallways", legend.get('h'));
		
	}
	
	/*
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
	
	/*
	 * DOOR DIRECTION TEST
	 * 		1. Tests (4,21) to be a doorway and left entering
	 * 		2. Tests (18,6) to be a doorway and right entering
	 * 		3. Tests (9,19) to be a doorway and up entering
	 * 		4. Tests (4,10) to be a doorway and down entering
	 * 		5. Tests (11,15) to not be a doorway
	 */
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
	
	/*
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
	
	/*
	 *  GAME BOARD SET UP TEST
	 *  	1. Test that first cell (0,0) is 'K'
	 *  	2. Test that last cell (20,24) is 'O'
	 *  	3. Test that (17,0) is 'F' (First cell of room)
	 *  	4. Test that (12, 12) is 'C' (Closet)
	 *  	5. Test that (14, 18) is 'h' (Hallway)
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
		assertEquals('h', board.getCellAt(14, 18).getInitial());
		//6. 
		assertEquals('L', board.getCellAt(20, 12).getInitial());
		//7.
		assertEquals('E', board.getCellAt(8, 20).getInitial());
		//8.
		assertEquals('D', board.getCellAt(4, 12).getInitial());
	}

}
