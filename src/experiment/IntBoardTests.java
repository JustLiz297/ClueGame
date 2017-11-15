/*
 * Authors: Elizabeth (Liz) Boyle, Annelyse Baker
 * Description: Tests for InBoard, making sure the adjacencies lists are made properly and the target lists are made properly
 */
/**
 * @author eboyle, annelysebaker
 *
 */
package experiment;

import static org.junit.Assert.*;

import java.awt.Graphics;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;

public class IntBoardTests {
	private static IntBoard intBoard;
	private static Board board;
	
	@Before
	public void setBoard() throws BadConfigFormatException {
		//board = new IntBoard();
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("Clue Layout.csv", "ClueLegend.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}

	//-----------------------//
	
	/*
	 * ADJACENCY TEST: top left corner (0,0)
	 * Should contain: (1,0),(0,1)
	 * Should been size: 2
	 */
	@Test
	public void testAdjacency0() {
		//setBoard();
		BoardCell space = board.getCellAt(0,0);
		Set<BoardCell> testList = board.getAdjList(0,0);
		
		//(1,0)
		boolean case1_0 = false;
		for (BoardCell x: testList) {
			if (x.getColumn() == 0) {
				if (x.getRow() == 1) {
					case1_0 = true;
				}
			}
		}
		assertTrue(case1_0);
		//(0,1)
		boolean case0_1 = false;
		for (BoardCell x: testList) {
			if (x.getColumn() == 1) {
				if (x.getRow() == 0) {
					case0_1 = true;
				}
			}
		}
		assertTrue(case0_1);
		
		assertEquals(2, testList.size());
	}
	
	/*
	 * ADJACENCY TEST: bottom right corner (3,3)
	 * Should contain: (2,3),(3,2)
	 * Should been size: 2
	 */
	@Test
	public void testAdjacency1() {
		BoardCell space = board.getCellAt(3,3);
		Set<BoardCell> testList = board.getAdjList(3,3);
		
		//(2,3)
		boolean case2_3 = false;
		for (BoardCell x: testList) {
			if (x.getColumn() == 3) {
				if (x.getRow() == 2) {
					case2_3 = true;
				}
			}
		}
		assertTrue(case2_3);
		//(3,2)
		boolean case3_2 = false;
		for (BoardCell x: testList) {
			if (x.getColumn() == 2) {
				if (x.getRow() == 3) {
					case3_2 = true;
				}
			}
		}
		assertTrue(case3_2);
		assertEquals(2, testList.size());
	}
	
	/*
	 * ADJACENCY TEST: a right edge (1,3)
	 * Should contain: (0,3),(2,3), (1,2)
	 * Should been size: 3
	 */
	@Test
	public void testAdjacency2() {
		BoardCell space = board.getCellAt(1,3);
		Set<BoardCell> testList = board.getAdjList(1,3);
		
		//(0,3)
		boolean case0_3 = false;
		for (BoardCell x: testList) {
			if (x.getColumn() == 3) {
				if (x.getRow() == 0) {
					case0_3 = true;
				}
			}
		}
		assertTrue(case0_3);
		
		//(2,3)
		boolean case2_3 = false;
		for (BoardCell x: testList) {
			if (x.getColumn() == 3) {
				if (x.getRow() == 2) {
					case2_3 = true;
				}
			}
		}
		assertTrue(case2_3);
		
		//(1,2)
		boolean case1_2 = false;
		for (BoardCell x: testList) {
			if (x.getColumn() == 2) {
				if (x.getRow() == 1) {
					case1_2 = true;
				}
			}
		}
		assertTrue(case1_2);
		assertEquals(3, testList.size());
	}
	
	/*
	 * ADJACENCY TEST: a left edge (2,0)
	 * Should contain: (2,1),(1,0), (3,0)
	 * Should been size: 3
	 */
	@Test
	public void testAdjacency3() {
		BoardCell space = board.getCellAt(2,0);
		Set<BoardCell> testList = board.getAdjList(2,0);
		//(2,1)
		boolean case2_1 = false;
		for (BoardCell x: testList) {
			if (x.getColumn() == 1) {
				if (x.getRow() == 2) {
					case2_1 = true;
				}
			}
		}
		assertTrue(case2_1);
		//(1,0)
		boolean case1_0 = false;
		for (BoardCell x: testList) {
			if (x.getColumn() == 0) {
				if (x.getRow() == 1) {
					case1_0 = true;
				}
			}
		}
		assertTrue(case1_0);
		//(3,0)
		boolean case3_0 = false;
		for (BoardCell x: testList) {
			if (x.getColumn() == 0) {
				if (x.getRow() == 3) {
					case3_0 = true;
				}
			}
		}
		assertTrue(case3_0);
		assertEquals(3, testList.size());
	}
	
	/*
	 * ADJACENCY TEST: second column fully surrounded (1,1)
	 * Should contain: (0,1),(1,0),(2,1),(1,2)
	 * Should been size: 4
	 */
	@Test
	public void testAdjacency4() {
		BoardCell space = board.getCellAt(1,1);
		Set<BoardCell> testList = board.getAdjList(1,1);
		//(0,1)
		boolean case0_1 = false;
		for (BoardCell x: testList) {
			if (x.getColumn() == 1) {
				if (x.getRow() == 0) {
					case0_1 = true;
				}
			}
		}
		assertTrue(case0_1);
		//(1,0)
		boolean case1_0 = false;
		for (BoardCell x: testList) {
			if (x.getColumn() == 0) {
				if (x.getRow() == 1) {
					case1_0 = true;
				}
			}
		}
		assertTrue(case1_0);
		//(2,1)
		boolean case2_1 = false;
		for (BoardCell x: testList) {
			if (x.getColumn() == 1) {
				if (x.getRow() == 2) {
					case2_1 = true;
				}
			}
		}
		assertTrue(case2_1);
		//(1,2)
		boolean case1_2 = false;
		for (BoardCell x: testList) {
			if (x.getColumn() == 2) {
				if (x.getRow() == 1) {
					case1_2 = true;
				}
			}
		}
		assertTrue(case1_2);

		assertEquals(4, testList.size());
	}
	
	/*
	 * ADJACENCY TEST: third column fully surrounded (2,2)
	 * Should contain: (2,3),(3,2),(2,1),(1,2)
	 * Should been size: 4
	 */
	@Test
	public void testAdjacency5() {
		BoardCell space = board.getCellAt(2,2);
		Set<BoardCell> testList = board.getAdjList(2,2);
		//(2,1)
		boolean case2_1 = false;
		for (BoardCell x: testList) {
			if (x.getColumn() == 1) {
				if (x.getRow() == 2) {
					case2_1 = true;
				}
			}
		}
		assertTrue(case2_1);
		//(1,2)
		boolean case1_2 = false;
		for (BoardCell x: testList) {
			if (x.getColumn() == 2) {
				if (x.getRow() == 1) {
					case1_2 = true;
				}
			}
		}
		assertTrue(case1_2);
		//(2,3)
		boolean case2_3 = false;
		for (BoardCell x: testList) {
			if (x.getColumn() == 3) {
				if (x.getRow() == 2) {
					case2_3 = true;
				}
			}
		}
		assertTrue(case2_3);
		//(3,2)
		boolean case3_2 = false;
		for (BoardCell x: testList) {
			if (x.getColumn() == 2) {
				if (x.getRow() == 3) {
					case3_2 = true;
				}
			}
		}
		assertTrue(case3_2);
		assertEquals(4, testList.size());
	}
	
	/*
	 * ADJACENCY TEST: a top edge (0,1)
	 * Should contain: (0,0),(1,1),(0,2)
	 * Should been size: 3
	 */
	@Test
	public void testAdjacency6() {
		BoardCell space = board.getCellAt(0,1);
		Set<BoardCell> testList = board.getAdjList(0,1);
		//(0,0)
		boolean case0_0 = false;
		for (BoardCell x: testList) {
			if (x.getColumn() == 0) {
				if (x.getRow() == 0) {
					case0_0 = true;
				}
			}
		}
		assertTrue(case0_0);
		//(1,1)
		boolean case1_1 = false;
		for (BoardCell x: testList) {
			if (x.getColumn() == 1) {
				if (x.getRow() == 1) {
					case1_1 = true;
				}
			}
		}
		assertTrue(case1_1);
		//(0,2)
		boolean case0_2 = false;
		for (BoardCell x: testList) {
			if (x.getColumn() == 2) {
				if (x.getRow() == 0) {
					case0_2 = true;
				}
			}
		}
		assertTrue(case0_2);
		assertEquals(3, testList.size());
	}
	
	/*
	 * ADJACENCY TEST: a bottom edge (3,2)
	 * Should contain: (3,3),(3,1),(2,2)
	 * Should been size: 3
	 */
	@Test
	public void testAdjacency7() {
		BoardCell space = board.getCellAt(3,2);
		Set<BoardCell> testList = board.getAdjList(3,2);
		//(3,3)
		boolean case3_3 = false;
		for (BoardCell x: testList) {
			if (x.getColumn() == 3) {
				if (x.getRow() == 3) {
					case3_3 = true;
				}
			}
		}
		assertTrue(case3_3);
		//(3,1)
		boolean case3_1 = false;
		for (BoardCell x: testList) {
			if (x.getColumn() == 1) {
				if (x.getRow() == 3) {
					case3_1 = true;
				}
			}
		}
		assertTrue(case3_1);
		//(2,2)
		boolean case2_2 = false;
		for (BoardCell x: testList) {
			if (x.getColumn() == 2) {
				if (x.getRow() == 2) {
					case2_2 = true;
				}
			}
		}
		assertTrue(case2_2);
		assertEquals(3, testList.size());
	}


	//----------------------//
	
	/*
	 * TARGET TEST: cell: (0,0) distance: (1)
	 * Should contain: (1,0),(0,1)
	 * Should been size: 2
	 */
	@Test
	public void testTargets0_1() {
		BoardCell space = board.getCellAt(0,0);
		board.calcTargets(0, 0, 1);
		Set<BoardCell> targets = board.getTargets();
		//(0,1)
		boolean case0_1 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 1) {
				if (x.getRow() == 0) {
					case0_1 = true;
				}
			}
		}
		assertTrue(case0_1);
		//(1,0)
		boolean case1_0 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 0) {
				if (x.getRow() == 1) {
					case1_0 = true;
				}
			}
		}
		assertTrue(case1_0);
		assertEquals(2, targets.size());
	}
	
	/*
	 * TARGET TEST: cell: (0,0) distance: (2)
	 * Should contain: (2,0),(1,1),(0,2)
	 * Should been size: 3
	 */
	@Test
	public void testTargets0_2() {
		BoardCell space = board.getCellAt(0,0);
		board.calcTargets(0, 0, 2);
		Set<BoardCell> targets = board.getTargets();
		//(2,0)
		boolean case2_0 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 0) {
				if (x.getRow() == 2) {
					case2_0 = true;
				}
			}
		}
		assertTrue(case2_0);
		//(1,1)
		boolean case1_1 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 1) {
				if (x.getRow() == 1) {
					case1_1 = true;
				}
			}
		}
		assertTrue(case1_1);
		//(0,2)
		boolean case0_2 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 2) {
				if (x.getRow() == 0) {
					case0_2 = true;
				}
			}
		}
		assertTrue(case0_2);
		assertEquals(3, targets.size());
	}
	
	/*
	 * TARGET TEST: cell: (0,0) distance: (3)
	 * Should contain: (3,0),(2,1),(0,1),(1,2),(0,3),(1,0)
	 * Should been size: 6
	 */
	@Test
	public void testTargets0_3() {
		BoardCell space = board.getCellAt(0,0);
		board.calcTargets(0, 0, 3);
		Set<BoardCell> targets = board.getTargets();
		//(3,0)
		boolean case3_0 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 0) {
				if (x.getRow() == 3) {
					case3_0 = true;
				}
			}
		}
		assertTrue(case3_0);
		//(2,1)
		boolean case2_1 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 1) {
				if (x.getRow() == 2) {
					case2_1 = true;
				}
			}
		}
		assertTrue(case2_1);
		//(0,1)
		boolean case0_1 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 1) {
				if (x.getRow() == 0) {
					case0_1 = true;
				}
			}
		}
		assertTrue(case0_1);
		//(1,2)
		boolean case1_2 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 2) {
				if (x.getRow() == 1) {
					case1_2 = true;
				}
			}
		}
		assertTrue(case1_2);
		//(0,3)
		boolean case0_3 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 3) {
				if (x.getRow() == 0) {
					case0_3 = true;
				}
			}
		}
		assertTrue(case0_3);
		//(1,0)
		boolean case1_0 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 0) {
				if (x.getRow() == 1) {
					case1_0 = true;
				}
			}
		}
		assertTrue(case1_0);
		assertEquals(6, targets.size());
	}
	
	/*
	 * TARGET TEST: cell: (0,0) distance: (4)
	 * Should contain: (0,2),(1,1),(1,3),(2,2),(3,1)
	 * Should been size: 6
	 */
	@Test
	public void testTargets0_4() {
		BoardCell space = board.getCellAt(0,0);
		board.calcTargets(0, 0, 4);
		Set<BoardCell> targets = board.getTargets();
		//(0,2)
		boolean case0_2 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 2) {
				if (x.getRow() == 0) {
					case0_2 = true;
				}
			}
		}
		assertTrue(case0_2);
		//(2,0)
		boolean case2_0 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 0) {
				if (x.getRow() == 2) {
					case2_0 = true;
				}
			}
		}
		assertTrue(case2_0);
		//(1,1)
		boolean case1_1 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 1) {
				if (x.getRow() == 1) {
					case1_1 = true;
				}
			}
		}
		assertTrue(case1_1);
		//(1,3)
		boolean case1_3 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 3) {
				if (x.getRow() == 1) {
					case1_3 = true;
				}
			}
		}
		assertTrue(case1_3);
		//(2,2)
		boolean case2_2 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 2) {
				if (x.getRow() == 2) {
					case2_2 = true;
				}
			}
		}
		assertTrue(case2_2);
		//(3,1)
		boolean case3_1 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 1) {
				if (x.getRow() == 3) {
					case3_1 = true;
				}
			}
		}
		assertTrue(case3_1);
		assertEquals(6, targets.size());
	}
	
	/*
	 * TARGET TEST: cell: (1,1) distance: (2)
	 * Should contain: (0,0),(0,2),(2,0),(1,3),(2,2),(3,1)
	 * Should been size: 6
	 */
	@Test
	public void testTargets1_2() {
		BoardCell space = board.getCellAt(1,1);
		board.calcTargets(1, 1, 2);
		Set<BoardCell> targets = board.getTargets();
		//(0,0)
		boolean case0_0 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 0) {
				if (x.getRow() == 0) {
					case0_0 = true;
				}
			}
		}
		assertTrue(case0_0);
		//(0,2)
		boolean case0_2 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 2) {
				if (x.getRow() == 0) {
					case0_2 = true;
				}
			}
		}
		assertTrue(case0_2);
		//(1,3)
		boolean case1_3 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 3) {
				if (x.getRow() == 1) {
					case1_3 = true;
				}
			}
		}
		assertTrue(case1_3);
		//(0,2)
		boolean case2_0 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 0) {
				if (x.getRow() == 2) {
					case2_0 = true;
				}
			}
		}
		assertTrue(case2_0);
		//(2,2)
		boolean case2_2 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 2) {
				if (x.getRow() == 2) {
					case2_2 = true;
				}
			}
		}
		assertTrue(case2_2);
		//(3,1)
		boolean case3_1 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 1) {
				if (x.getRow() == 3) {
					case3_1 = true;
				}
			}
		}
		assertTrue(case3_1);
		assertEquals(6, targets.size());
	}
	
	/*
	 * TARGET TEST: cell: (1,1) distance: (3)
	 * Should contain: (0,1),(0,3),(1,0),(1,2),(2,1),(2,3),(3,0),(3,2)
	 * Should been size: 8
	 */
	@Test
	public void testTargets1_3() {
		BoardCell space = board.getCellAt(1,1);
		board.calcTargets(1, 1, 3);
		Set<BoardCell> targets = board.getTargets();
		//(0,1)
		boolean case0_1 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 1) {
				if (x.getRow() == 0) {
					case0_1 = true;
				}
			}
		}
		assertTrue(case0_1);
		//(0,3)
		boolean case0_3 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 3) {
				if (x.getRow() == 0) {
					case0_3 = true;
				}
			}
		}
		assertTrue(case0_3);
		//(1,0)
		boolean case1_0 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 0) {
				if (x.getRow() == 1) {
					case1_0 = true;
				}
			}
		}
		assertTrue(case1_0);
		//(1,2)
		boolean case1_2 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 2) {
				if (x.getRow() == 1) {
					case1_2 = true;
				}
			}
		}
		assertTrue(case1_2);
		//(2,1)
		boolean case2_1 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 1) {
				if (x.getRow() == 2) {
					case2_1 = true;
				}
			}
		}
		assertTrue(case2_1);
		//(2,3)
		boolean case2_3 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 3) {
				if (x.getRow() == 2) {
					case2_3 = true;
				}
			}
		}
		assertTrue(case2_3);
		//(3,0)
		boolean case3_0 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 0) {
				if (x.getRow() == 3) {
					case3_0 = true;
				}
			}
		}
		assertTrue(case3_0);
		//(3,2)
		boolean case3_2 = false;
		for (BoardCell x: targets) {
			if (x.getColumn() == 2) {
				if (x.getRow() == 3) {
					case3_2 = true;
				}
			}
		}
		assertTrue(case3_2);
		assertEquals(8, targets.size());
	}
}
