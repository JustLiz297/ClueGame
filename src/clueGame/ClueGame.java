package clueGame;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JFrame;

import clueGUI.clueGui;

/**
 * ClueGame class that is the game engine
 * @author eboyle, annelyse
 *
 */
public class ClueGame extends JFrame{
	private static Board board;

	/**
	 * Creates the GUI and starts the game
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue Game");
		frame.setSize(1150, 1050);	
		clueGui controlS = new clueGui();
		controlS.controlPanel();
		frame.setJMenuBar(controlS.createFileMenu());
		frame.add(controlS, BorderLayout.SOUTH);
		board = Board.getInstance();
		board.setConfigFiles("Clue Layout.csv", "ClueLegend.txt"); //layout file, legend file
		board.setCardFiles("Weapons.txt", "Players.txt");
		try {
			board.initialize();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		clueGui myCards = new clueGui();
		myCards.myCardsPanel(board.getHumanPlayer());
		frame.setJMenuBar(myCards.createFileMenu());
		frame.add(myCards, BorderLayout.EAST);
		

		frame.add(board);
		frame.setVisible(true);
	}
}
