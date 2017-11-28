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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import clueGUI.DetectiveNotes;
import clueGUI.CardsPanel;
import clueGUI.ControlPanel;

/**
 * ClueGame class that is the game engine
 * @author eboyle, annelysebaker
 * @version 1.3
 */
public class ClueGame extends JFrame{
	private static Board board; //game board
	private JDialog startScreen; //JDialog game start message
	private DetectiveNotes dialog; //JDialog window for notes
	private static ControlPanel controls = ControlPanel.getInstance(); //control panel JPanel
	private CardsPanel myCards = new CardsPanel(); //cards panel JPanel
	private String boardConfigFile = "Clue Layout.csv"; //name of the board file that will be loaded in
	private String roomConfigFile = "ClueLegend.txt"; //name of the legend file that will be loaded in
	private String weaponConfigFile = "Weapons.txt"; //name of the weapons file that will be loaded in
	private String playerConfigFile = "Players.txt"; //name of the player file that will be loaded in

	/**
	 * Constructor that calls the Welcome Screen
	 * @throws HeadlessException
	 */
	public ClueGame() throws HeadlessException {
		super();
		this.startScreen = WelcomeClue();
		startScreen.setVisible(true);
	}

	/**
	 * Makes the Welcome Screen
	 * @return JDialog Welcome Screen
	 */
	public JDialog WelcomeClue() {
		JDialog welcome = new JDialog();
		welcome.setTitle("Welcome to Clue");
		welcome.setSize(320, 100);
		welcome.setLocationRelativeTo(null);
		welcome.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		welcome.setLayout(new GridLayout(2,1));
		JLabel introMessage = new JLabel("     You are Preacher Periwinkle! Press OK to begin.");
		welcome.add(introMessage, BorderLayout.CENTER);
		JButton oK = new JButton("OK");
		class ExitListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				theGameSetUp(); //Opens game board
				welcome.dispose();}
		}
		oK.addActionListener(new ExitListener());
		welcome.add(oK);
		return welcome;
	}
	/**
	 * Creates the Menu Bar
	 * @return
	 */
	public JMenuBar createFileMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem exit = new JMenuItem("Exit");
		class MenuExitListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {System.exit(0);}
		}
		exit.addActionListener(new MenuExitListener());
		JMenuItem notes = new JMenuItem("Show Notes");
		class MenuNotesListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				dialog = new DetectiveNotes();
				dialog.setVisible(true);				
			}
		}
		notes.addActionListener(new MenuNotesListener());
		menu.add(notes);
		menu.add(exit);
		menuBar.add(menu);
		return menuBar;
	}
	/**
	 * Function that creates the game board
	 */
	public void theGameSetUp() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue Game");
		frame.setSize(1100, 1000);
		frame.setLocationRelativeTo(null);
		frame.setJMenuBar(createFileMenu());
		frame.add(controls, BorderLayout.SOUTH);
		board = Board.getInstance();
		board.setConfigFiles(boardConfigFile, roomConfigFile); //layout file, legend file
		board.setCardFiles(weaponConfigFile, playerConfigFile);
		try {
			board.initialize();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		myCards.myCardsPanel(board.getHumanPlayer());
		frame.add(myCards, BorderLayout.EAST);
		frame.add(board);
		frame.setVisible(true);
		startGame();	
	}

	/**
	 * First turn of the game
	 */
	public void startGame() {
		Player startingPlayer = board.getPlayers().get(0);
		int roll = board.rollDie();
		controls.updatePlayer(startingPlayer);
		controls.updateRoll(roll);
		board.turnControl(startingPlayer, roll);
	}
	
	/**
	 * Starts the game
	 * @param args
	 */
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
	}
}
