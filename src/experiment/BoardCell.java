package experiment;

public class BoardCell {
	private int yCoord;
	private int xCoord;
	
	public BoardCell(int yCoord, int xCoord) {
		super();
		this.yCoord = yCoord;
		this.xCoord = xCoord;
	}

	@Override
	public String toString() {
		return "BoardCell [yCoord=" + yCoord + ", xCoord=" + xCoord + "]";
	}
	
	
}
