package clueGUI;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;

/**
 * Custom Dialog class for the Detective Notes
 * @author eboyle, annelysebaker
 * @version 1.1
 *
 */
public class DetectiveNotes extends JDialog {
	private ArrayList<String> roomList= new ArrayList<String>(); //list of all the room names
	private ArrayList<String> weaponsList= new ArrayList<String>(); //list of the weapons
	private ArrayList<String> playersList= new ArrayList<String>(); //list of the player names
	public static Board board = Board.getInstance();
	
	/**
	 * Constructor for the Detective Notes Window
	 */
	public DetectiveNotes() {
		roomList = board.getRoomList();
		weaponsList = board.getWeaponsList();
		playersList = board.getPlayerList();
		setTitle("Detective Notes");
		setSize(610, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		add(notePanels());
	}
	
	/**
	 * Creates the full panel for the notes window
	 * @return created panel
	 */
	private JPanel notePanels() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,2));
		JPanel row1 = rowPeople();
		JPanel row2 = rowRoom();
		JPanel row3 = rowWeapons();
		panel.add(row1);
		panel.add(row2);
		panel.add(row3);
		return panel;
	}
	/**
	 * Creates the row of panels that hold the people category
	 * @return created panel
	 */
	private JPanel rowPeople() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,0));
		JPanel peopleList = peopleList();
		JPanel peopleCombo = peopleCombo();
		panel.add(peopleList);
		panel.add(peopleCombo);
		return panel;
	}
	/**
	 * Creates the people check boxes
	 * @return created panel
	 */
	private JPanel peopleList() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		panel.setLayout(new GridLayout(0,2));
		for (String name : playersList) {
			panel.add(new JCheckBox(name));
		}
		return panel;
	}
	/**
	 * Creates the people suggestion drop-down option
	 * @return created panel
	 */
	private JPanel peopleCombo() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Person Best Guess"));
		JComboBox<String> people = new JComboBox<String>();
		people.addItem("Don't Know");
		for (String name : playersList) {
			people.addItem(name);
		}
		panel.add(people);
		return panel;
	}
	/**
	 * Creates the row of panels that hold the rooms category
	 * @return created panel
	 */
	private JPanel rowRoom() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,0));
		JPanel roomList = roomList();
		JPanel roomCombo = roomCombo();
		panel.add(roomList);
		panel.add(roomCombo);
		return panel;
	}
	/**
	 * Creates the room check boxes
	 * @return created panel
	 */
	private JPanel roomList() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		panel.setLayout(new GridLayout(0,2));
		for (String room : roomList) {
			panel.add(new JCheckBox(room));
		}
		return panel;
	}
	/**
	 * Creates the rooms suggestion drop-down option
	 * @return created panel
	 */
	private JPanel roomCombo() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Room Best Guess"));
		JComboBox<String> rooms = new JComboBox<String>();
		rooms.addItem("Don't Know");
		for (String room : roomList) {
			rooms.addItem(room);
		}
		panel.add(rooms);
		return panel;
	}	
	/**
	 * Creates the row of panels that hold the weapons category
	 * @return created panel
	 */
		private JPanel rowWeapons() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,0));
		JPanel weaponList = weaponList();
		JPanel weaponCombo = weaponCombo();
		panel.add(weaponList);
		panel.add(weaponCombo);
		return panel;
	}
	/**
	 * Creates the weapons check boxes
	 * @return created panel
	 */
	private JPanel weaponList() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		panel.setLayout(new GridLayout(0,2));
		for (String weapon : weaponsList) {
			panel.add(new JCheckBox(weapon));
		}
		return panel;
	}
	/**
	 * Creates the weapon suggestion drop-down option
	 * @return created panel
	 */
	private JPanel weaponCombo() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Best Guess"));
		JComboBox<String> weapons = new JComboBox<String>();
		weapons.addItem("Don't Know");
		for (String weapon : weaponsList) {
			weapons.addItem(weapon);
		}
		panel.add(weapons);
		return panel;
	}
}