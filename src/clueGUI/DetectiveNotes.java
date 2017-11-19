package clueGUI;

import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.BadConfigFormatException;
import clueGame.Card;
import clueGame.CardType;

/**
 * Custom Dialog class for the Detective Notes
 * @author eboyle, annelysebaker
 *
 */
public class DetectiveNotes extends JDialog {
	private ArrayList<String> roomList= new ArrayList<String>(); //list of all the room names
	private ArrayList<String> weaponsList= new ArrayList<String>(); //list of the weapons
	private ArrayList<String> playersList= new ArrayList<String>(); //list of the player names
	
	/**
	 * Constructor for the Detective Notes Window
	 */
	public DetectiveNotes() {
		try {
			this.configNames();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		setTitle("Detective Notes");
		setSize(600, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		add(notePanels());
	}
	
	/**
	 * Loads in the names of players, weapons, and rooms to have in the notes
	 * @throws BadConfigFormatException
	 */
	public void configNames() throws BadConfigFormatException {
		try {
			FileReader roomReader = new FileReader("ClueLegend.txt");
			Scanner in = new Scanner(roomReader);
			while (in.hasNext()) {
				String[] entry = in.nextLine().split(", ");
				//Check if the room type is valid in the file
				if (entry[2].equals("Card")  || entry[2].equals("Other")) {
					if (entry[2].equals("Card")) {
						roomList.add(entry[1]); //If the room type is card, add it to the room list to be made into a card
					}
				}
				else{ 
					throw new BadConfigFormatException("Invalid Legend Format");
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			FileReader weaponReader = new FileReader("Weapons.txt");
			Scanner in = new Scanner(weaponReader);
			while (in.hasNext()) {
				String entry = in.nextLine();
				weaponsList.add(entry);
			}
			in.close();
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			FileReader playerReader = new FileReader("Players.txt");
			Scanner in = new Scanner(playerReader);
			while (in.hasNext()) {
				String entry = in.nextLine();
				playersList.add(entry.trim());
			}
			in.close();
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
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
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
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
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
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
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
		JComboBox<String> weapons = new JComboBox<String>();
		weapons.addItem("Don't Know");
		for (String weapon : weaponsList) {
			weapons.addItem(weapon);
		}
		panel.add(weapons);
		return panel;
	}
}