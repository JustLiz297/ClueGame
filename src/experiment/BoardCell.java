/*
 * Authors: Elizabeth (Liz) Boyle, Annelyse Baker
 * Description: BoardCell class, each cell has a y&x coordinate
 */

package experiment;

public class BoardCell {
	private int yCoord;
	private int xCoord;
	
	public BoardCell(int yCoord, int xCoord) {
		super();
		this.yCoord = yCoord;
		this.xCoord = xCoord;
	}
	
	public int getyCoord() {
		return yCoord;
	}

	public int getxCoord() {
		return xCoord;
	}
	
	/* For testing purposes */
	@Override
	public String toString() {
		return "BoardCell [yCoord=" + yCoord + ", xCoord=" + xCoord + "]";
	}


	
	

	
}
