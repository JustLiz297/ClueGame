package clueGUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * GUI class for Clue Game
 * @author eboyle, annelysebaker
 *
 */
public class clueGui extends JPanel{
	private DetectiveNotes dialog;
	
	/**
	 * GUI constructor
	 */
	public clueGui() {
		
	}
	//-------------------------------------------------//
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
	//-------------------------------------------------//
	/**
	 * Takes whoseTurnandButtons and rollandGuess panels and makes them one panel
	 * @return panel created
	 */
	public void controlPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,3));
		JPanel row1 = whoseTurnandButtons();
		JPanel row2 = rollandGuess();
		panel.add(row1);
		panel.add(row2);
		add(panel);
	}
	/**
	 * Takes whoseTurn panel and add buttons next to it
	 * @return panel created
	 */
	private JPanel whoseTurnandButtons() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,3));
		JPanel turn = whoseTurn();
		JButton nextPlayer = new JButton("Next player");
		JButton accusation = new JButton("Make an accusation");
		panel.add(turn);
		panel.add(nextPlayer);
		panel.add(accusation);
		return panel;
	}
	/**
	 * Makes the Whose Turn? panel
	 * @return panel created
	 */
	private JPanel whoseTurn() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Whose turn?"));
		JTextField player = new JTextField("Farmer Flux");
		player.setEditable(false);
		panel.add(player);
		return panel;
	}

	/**
	 * Takes die, guessDisplay, and resultDisplay panels and makes them one
	 * @return panel created
	 */
	private JPanel rollandGuess() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,3));
		JPanel roll = die();
		JPanel guess = guessDisplay();
		JPanel result = resultDisplay();
		panel.add(roll);
		panel.add(guess);
		panel.add(result);
		return panel;
	}
	/**
	 * Makes Roll panel
	 * @return panel created
	 */
	private JPanel die() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		JLabel dieLabel = new JLabel("Roll");
		JTextField number = new JTextField("6");
		number.setEditable(false);
		panel.add(dieLabel);
		panel.add(number);
		return panel;
	}
	/**
	 * Makes Guess display panel
	 * @return panel created
	 */
	private JPanel guessDisplay() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		JLabel personLabel = new JLabel("Person:");
		JTextField person = new JTextField("Doctor Dandilion");
		person.setEditable(false);
		JLabel weaponLabel = new JLabel("Weapon:");
		JTextField weapon = new JTextField("Poison");
		weapon.setEditable(false);
		JLabel roomLabel = new JLabel("Room:");
		JTextField room = new JTextField("Office");
		room.setEditable(false);
		panel.add(personLabel);
		panel.add(person);
		panel.add(weaponLabel);
		panel.add(weapon);
		panel.add(roomLabel);
		panel.add(room);
		return panel;
	}
	/**
	 * Makes Response display
	 * @return panel created
	 */
	private JPanel resultDisplay() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Card Returned"));
		JLabel responseLabel = new JLabel("Response:");
		JTextField response = new JTextField("Office");
		response.setEditable(false);
		panel.add(responseLabel);
		panel.add(response);
		return panel;
	}
	//-------------------------------------------------//
	/**
	 * Creates the gameBoard panel
	 * @return
	 */
	private JPanel gameBoard() {
		JPanel panel = new JPanel();
		return panel;
	}
	//-------------------------------------------------//

	public void myCardsPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		panel.setLayout(new GridLayout(3,1));
		panel.add(peopleCards());
		panel.add(roomCards());
		panel.add(weaponCards());
		add(panel);
	}
	
	private JPanel peopleCards() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		JTextField cards = new JTextField("People Cards");
		cards.setEditable(false);
		panel.add(cards);
		return panel;
	}
	
	private JPanel roomCards() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		JTextField cards = new JTextField("Room Cards");
		cards.setEditable(false);
		panel.add(cards);
		return panel;
	}
	
	private JPanel weaponCards() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		JTextField cards = new JTextField("Weapon Cards");
		cards.setEditable(false);
		panel.add(cards);
		return panel;
	}
	
	//-------------------------------------------------//


}
