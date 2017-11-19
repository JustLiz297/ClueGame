package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import clueGUI.clueGui;

/**
 * ClueGame class that is the game engine
 * @author eboyle, annelyse
 *
 */
public class ClueGame extends JFrame{
	private static Board board;
	JDialog startScreen;

	public ClueGame() throws HeadlessException {
		super();
		this.startScreen = WelcomeClue();
		startScreen.setVisible(true);
	}

	public JDialog WelcomeClue() {
		JDialog welcome = new JDialog();
		welcome.setTitle("Welcome to Clue");
		welcome.setSize(343, 100);
		welcome.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		welcome.setLayout(new GridLayout(2,1));
		JLabel introMessage = new JLabel(" You are Preacher Periwinkle! Press Next Player to begin.");
		welcome.add(introMessage, BorderLayout.CENTER);
		JButton oK = new JButton("OK");
		class ExitListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				theGameSetUp();
				welcome.dispose();}
		}
		oK.addActionListener(new ExitListener());
		welcome.add(oK);
		return welcome;
	}
	
	public void theGameSetUp() {
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
	
	
	/**
	 * Creates the GUI and starts the game
	 * @param args
	 */
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
	}
}
