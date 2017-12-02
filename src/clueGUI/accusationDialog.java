package clueGUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import clueGame.Board;
import clueGame.Solution;


public class accusationDialog extends JDialog{
	public static Board board = Board.getInstance();
	private ArrayList<String> weaponsList= new ArrayList<String>(); //list of the weapons
	private ArrayList<String> playersList= new ArrayList<String>(); //list of the player names
	private ArrayList<String> roomList= new ArrayList<String>(); //list of the player names
	private JButton submit;
	private JButton cancel;
	private String person = "Preacher Periwinkle"; //first name in the list so if not clicked, its the default
	private String weapon = "Icicle"; //first name in the list so if not clicked, its the default
	private String room = "Kitchen";
	
	public accusationDialog() {
		roomList = board.getRoomList();
		weaponsList = board.getWeaponsList();
		playersList = board.getPlayerList();
		setTitle("Make an Accusation");
		setSize(300, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4,1));
		panel.add(roomRow());
		panel.add(personRow());
		panel.add(weaponRow());
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1,2));
		submit = new JButton("Submit");
		class submitGuess implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				dispose();
				Solution accusation = new Solution(person, room, weapon);
				if(board.checkAccusation(accusation, board.getHumanPlayer())) {
					board.gameWon(board.getHumanPlayer());
				}
				else {
					JDialog oops = new JDialog();
					oops.setTitle("Oops");
					oops.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
					oops.setSize(320, 100);
					oops.setLocationRelativeTo(null);
					oops.setLayout(new GridLayout(2,1));
					JLabel message = new JLabel("Oops! Your accusation was wrong, keep playing!");
					message.setHorizontalAlignment((int) CENTER_ALIGNMENT);
					oops.add(message, BorderLayout.CENTER);
					JButton oK = new JButton("OK");
					class ExitListener implements ActionListener{
						public void actionPerformed(ActionEvent e) {
							oops.dispose();}
					}
					oK.addActionListener(new ExitListener());
					oops.add(oK);
					oops.setVisible(true);
					board.madeGuess = true;
					board.moved = true;
				}

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
	
	private JPanel roomRow() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		JLabel label = new JLabel("Room: ");
		label.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		panel.add(label);
		JComboBox<String> room = new JComboBox<String>();
		for (String name : roomList) {
			room.addItem(name);
		}
		class ComboListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				setRoom(room.getSelectedItem().toString());
			}
		}
		room.addActionListener(new ComboListener());
		panel.add(room);
		return panel;
	}
	
	private JPanel personRow() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		JLabel label = new JLabel("Person: ");
		label.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		panel.add(label);
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
		JLabel label = new JLabel("Weapon: ");
		label.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		panel.add(label);
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
	
	private void setRoom(String room) {
		this.room = room;
	}

}
