package clueGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;
import clueGame.Card;
import clueGame.Player;
import clueGame.Solution;

/**
 * GUI class for Clue Game
 * @author eboyle, annelysebaker
 * @version 1.3
 *
 */
public class ControlPanel extends JPanel{
	private JButton nextPlayer;
	private JButton accusation;
	private JTextField currentPlayer = new JTextField("Name");
	private JTextField currentRoll = new JTextField(Integer.toString(0));
	private Player current = null;
	JTextField person = new JTextField("Person");
	JTextField weapon = new JTextField("Weapon");
	JTextField room = new JTextField("Room");
	JTextField response = new JTextField("Response");
	public static Board board = Board.getInstance();
	
	private static ControlPanel theInstance = new ControlPanel();
	public static ControlPanel getInstance() {
		return theInstance;
	}

	/**
	 * Takes whoseTurnandButtons and rollandGuess panels and makes them one panel
	 *
	 */
	private ControlPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,3));
		panel.setPreferredSize(new Dimension(1060,150));
		JPanel row1 = whoseTurnandButtons();
		JPanel row2 = rollandGuess();
		panel.add(row1);
		panel.add(row2);
		add(panel);
	}
	/**
	 * Updated the current player field
	 * @param p
	 */
	public void updatePlayer(Player p) {
		this.current = p;
		this.currentPlayer.setText(p.toString());
	}

	/**
	 * Takes whoseTurn panel and add buttons next to it
	 * @return panel created
	 */
	private JPanel whoseTurnandButtons() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,3));
		JPanel turn = new JPanel();
		turn.setBorder(new TitledBorder (new EtchedBorder(), "Whose turn?"));
		this.currentPlayer.setEditable(false);
		turn.add(currentPlayer);
		nextPlayer = new JButton("Next player");
		class endTurn implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				if (board.movedYet()) {//change player
					changePlayer(current);
				}
				else {
					JDialog oops = new JDialog();
					oops.setTitle("Oops");
					oops.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
					oops.setSize(320, 100);
					oops.setLocationRelativeTo(null);
					oops.setLayout(new GridLayout(2,1));
					JLabel message = new JLabel("  Oops! You must move before you finishing your turn.");
					oops.add(message, BorderLayout.CENTER);
					JButton oK = new JButton("OK");
					class ExitListener implements ActionListener{
						public void actionPerformed(ActionEvent e) {
							oops.dispose();}
					}
					oK.addActionListener(new ExitListener());
					oops.add(oK);
					oops.setVisible(true);
				}
			}
		}
		nextPlayer.addActionListener(new endTurn());
		accusation = new JButton("Make an accusation");
		class makeAccusation implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				
			}
		}
		//accusation.addActionListener(new makeAccusation());
		panel.add(turn);
		panel.add(nextPlayer);
		panel.add(accusation);
		return panel;
	}
	
	/**
	 * Changes player (in this class so that the field can be updated easily)
	 * @param player
	 */
	public void changePlayer(Player player) {
		Player current = null;
		for (Player p : board.getPlayers()) {
			if (p == player) {
				current = p;
			}
		}
		int nextP = (board.getPlayers().indexOf(current)+1);
		if (nextP == 6) {
			nextP = 0;
		}
		int roll = board.rollDie();
		this.updatePlayer(board.getPlayers().get(nextP));
		this.updateRoll(roll);
		board.turnControl(board.getPlayers().get(nextP), roll);
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
	 * Updates Roll text
	 * @param roll
	 */
	public void updateRoll(int roll) {
		this.currentRoll.setText(Integer.toString(roll));
	}
	/**
	 * Makes Roll panel
	 * @return panel created
	 */
	private JPanel die() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		JLabel dieLabel = new JLabel("Roll");
		currentRoll.setEditable(false);
		panel.add(dieLabel);
		panel.add(currentRoll);
		return panel;
	}
	
	/**
	 * Makes Guess display panel
	 * @return panel created
	 */
	private JPanel guessDisplay() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		panel.setLayout(new GridLayout(3,2));
		JLabel personLabel = new JLabel("Person:");
		personLabel.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		person.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		person.setEditable(false);
		JLabel weaponLabel = new JLabel("Weapon:");
		weaponLabel.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		weapon.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		weapon.setEditable(false);
		JLabel roomLabel = new JLabel("Room:");
		roomLabel.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		room.setHorizontalAlignment((int) CENTER_ALIGNMENT);
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
		panel.setLayout(new GridLayout(1,2));
		JLabel responseLabel = new JLabel("Response:");
		responseLabel.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		response.setEditable(false);
		panel.add(responseLabel);
		panel.add(response);
		return panel;
	}
	
	public void updateGuess() {
		this.person.setText(board.currentSuggestion.person);
		this.weapon.setText(board.currentSuggestion.weapon);
		this.room.setText(board.currentSuggestion.room);
	}
	
	public void updateResponse(Card card) {
		if (card == null) {
			this.response.setText("None");
		}
		else {this.response.setText(card.getCardName());}
	}
}

