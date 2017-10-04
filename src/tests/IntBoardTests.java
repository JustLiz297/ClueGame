package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import experiment.BoardCell;
import experiment.IntBoard;

public class IntBoardTests {
	IntBoard board;
	
	@Before
	public void setBoard() {
		board = new IntBoard();
	}

	//-----------------------//
	
	/*
	 * ADJACENCY TEST: top left corner (0,0)
	 */
	@Test
	public void testAdjacency0() {
		BoardCell space = board.getCell(0,0);
		Set<BoardCell> testList = board.getAdjList(space);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());
	}
	
	/*
	 * ADJACENCY TEST: bottom right corner (3,3)
	 */
	@Test
	public void testAdjacency1() {
		BoardCell space = board.getCell(3,3);
		Set<BoardCell> testList = board.getAdjList(space);
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertEquals(2, testList.size());
	}
	
	/*
	 * ADJACENCY TEST: a right edge (1,3)
	 */
	@Test
	public void testAdjacency2() {
		BoardCell space = board.getCell(1,3);
		Set<BoardCell> testList = board.getAdjList(space);
		assertTrue(testList.contains(board.getCell(0, 3)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertEquals(3, testList.size());
	}
	
	/*
	 * ADJACENCY TEST: a left edge (2,0)
	 */
	@Test
	public void testAdjacency3() {
		BoardCell space = board.getCell(2,0);
		Set<BoardCell> testList = board.getAdjList(space);
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(3, 0)));
		assertEquals(3, testList.size());
	}
	
	/*
	 * ADJACENCY TEST: second column fully surrounded (1,1)
	 */
	@Test
	public void testAdjacency4() {
		BoardCell space = board.getCell(1,1);
		Set<BoardCell> testList = board.getAdjList(space);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertEquals(4, testList.size());
	}
	
	/*
	 * ADJACENCY TEST: third column fully surrounded (2,2)
	 */
	@Test
	public void testAdjacency5() {
		BoardCell space = board.getCell(2,2);
		Set<BoardCell> testList = board.getAdjList(space);
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertEquals(4, testList.size());
	}
	
	/*
	 * ADJACENCY TEST: a top edge (0,1)
	 */
	@Test
	public void testAdjacency6() {
		BoardCell space = board.getCell(0,1);
		Set<BoardCell> testList = board.getAdjList(space);
		assertTrue(testList.contains(board.getCell(0, 0)));
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 2)));
		assertEquals(3, testList.size());
	}
	
	/*
	 * ADJACENCY TEST: a bottom edge (3,2)
	 */
	@Test
	public void testAdjacency7() {
		BoardCell space = board.getCell(3,2);
		Set<BoardCell> testList = board.getAdjList(space);
		assertTrue(testList.contains(board.getCell(3, 3)));
		assertTrue(testList.contains(board.getCell(3, 1)));
		assertTrue(testList.contains(board.getCell(2, 2)));
		assertEquals(3, testList.size());
	}


	//----------------------//
	
	/*
	 * TARGET TEST: cell: (0,0) distance: (1)
	 */
	@Test
	public void testTargets0_1() {
		BoardCell space = board.getCell(0,0);
		board.calcTargets(space, 1);
		Set<BoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertEquals(2, targets.size());
	}
	
	/*
	 * TARGET TEST: cell: (0,0) distance: (2)
	 */
	@Test
	public void testTargets0_2() {
		BoardCell space = board.getCell(0,0);
		board.calcTargets(space, 2);
		Set<BoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertEquals(3, targets.size());
	}
	
	/*
	 * TARGET TEST: cell: (0,0) distance: (3)
	 */
	@Test
	public void testTargets0_3() {
		BoardCell space = board.getCell(0,0);
		board.calcTargets(space, 3);
		Set<BoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertEquals(6, targets.size());
	}
	
	/*
	 * TARGET TEST: cell: (0,0) distance: (4)
	 */
	@Test
	public void testTargets0_4() {
		BoardCell space = board.getCell(0,0);
		board.calcTargets(space, 4);
		Set<BoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertEquals(5, targets.size());
	}
	
	/*
	 * TARGET TEST: cell: (1,1) distance: (2)
	 */
	@Test
	public void testTargets1_2() {
		BoardCell space = board.getCell(1,1);
		board.calcTargets(space, 2);
		Set<BoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertEquals(6, targets.size());
	}
	
	/*
	 * TARGET TEST: cell: (1,1) distance: (3)
	 */
	@Test
	public void testTargets1_3() {
		BoardCell space = board.getCell(0,0);
		board.calcTargets(space, 3);
		Set<BoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(3, 2)));
		assertEquals(8, targets.size());
	}
}
