package clueGUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;
import clueGame.Solution;


/**
 * JDialog class of the Suggestion window for the Human Player
 * @author eboyle, annelysebaker
 *
 */
public class SuggestionDialog extends JDialog{
	public static Board board = Board.getInstance();
	private static ControlPanel controls = ControlPanel.getInstance();
	private ArrayList<String> weaponsList= new ArrayList<String>(); //list of the weapons
	private ArrayList<String> playersList= new ArrayList<String>(); //list of the player names
	private JButton submit;
	private JButton cancel;
	private String person = "Preacher Periwinkle"; //first name in the list so if not clicked, its the default
	private String weapon = "Icicle"; //first name in the list so if not clicked, its the default
	private String room = "";
	
	
	public SuggestionDialog(String room) {
		this.room = room;
		weaponsList = board.getWeaponsList();
		playersList = board.getPlayerList();
		setTitle("Make a Suggestion");
		setSize(300, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4,1));
		panel.add(roomRow(room));
		panel.add(personRow());
		panel.add(weaponRow());
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1,2));
		submit = new JButton("Submit");
		class submitGuess implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				Solution suggestion = new Solution(person, room, weapon);
				controls.updateResponse(board.handleSuggestion(suggestion, board.getHumanPlayer()));
				controls.updateGuess();
				dispose();
			}
		}
		submit.addActionListener(new submitGuess());
		cancel = new JButton("Cancel");
		class cancelGuess implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		}
		cancel.addActionListener(new cancelGuess());
		buttons.add(submit);
		buttons.add(cancel);
		panel.add(buttons);
		add(panel);
	}
	
	private JPanel roomRow(String room) {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Current Room:"));
		JLabel currentRoom = new JLabel(room);
		panel.add(currentRoom);
		return panel;
	}
	
	private JPanel personRow() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		panel.add(new JLabel("Person: "));
		JComboBox<String> people = new JComboBox<String>();
		for (String name : playersList) {
			people.addItem(name);
		}
		class ComboListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				setPerson(people.getSelectedItem().toString());
			}
		}
		people.addActionListener(new ComboListener());
		panel.add(people);
		return panel;
	}
	
	private JPanel weaponRow() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		panel.add(new JLabel("Weapon: "));
		JComboBox<String> weapons = new JComboBox<String>();
		for (String name : weaponsList) {
			weapons.addItem(name);
		}
		class ComboListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				setWeapon(weapons.getSelectedItem().toString());
			}
		}
		weapons.addActionListener(new ComboListener());
		panel.add(weapons);
		return panel;
	}
	
	private void setPerson(String person) {
		this.person = person;
	}
	
	private void setWeapon(String weapon) {
		this.weapon = weapon;
	}
}
