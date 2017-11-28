package clueGUI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card;
import clueGame.Player;


public class CardsPanel extends JPanel{

	/**
	 * Pulls the card category panels together and reads in the Human Player's cards
	 * @param Human Player
	 */
	public void myCardsPanel(Player p) {

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		panel.setLayout(new GridLayout(3,1));
		panel.setPreferredSize(new Dimension(150,300));
		ArrayList<Card> people = new ArrayList<Card>();
		ArrayList<Card> weapons = new ArrayList<Card>();
		ArrayList<Card> rooms = new ArrayList<Card>();
		
		for (Card c : p.getCards()) {
			if (c.isPerson()) {
				people.add(c);
			}
			else if (c.isRoom()) {
				rooms.add(c);
			}
			else if (c.isWeapon()) {
				weapons.add(c);
			}
		}
		panel.add(peopleCards(people));
		panel.add(roomCards(rooms));
		panel.add(weaponCards(weapons));
		add(panel);
	}
	/**
	 * Creates the Person Cards panel
	 * @param array of Person Cards
	 * @return created panel
	 */
	private JPanel peopleCards(ArrayList<Card> people) {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		int i = 1;
		if (people.isEmpty()) {
			return panel;
		}
		for (Card c : people) {
			panel.setLayout(new GridLayout(i,1));
			JTextField cards = new JTextField(c.getCardName());
			cards.setEditable(false);
			panel.add(cards);
			i++;
		}
		return panel;
	}
	/**
	 * Creates the Room Cards panel
	 * @param array of Room Cards
	 * @return created panel
	 */
	private JPanel roomCards(ArrayList<Card> rooms) {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		int i = 1;
		if (rooms.isEmpty()) {
			return panel;
		}
		for (Card c : rooms) {
			panel.setLayout(new GridLayout(i,1));
			JTextField cards = new JTextField(c.getCardName());
			cards.setEditable(false);
			panel.add(cards);
			i++;
		}
		return panel;
	}
	/**
	 * Creates the Weapon Cards panel
	 * @param array of Weapon Cards
	 * @return created panel
	 */
	private JPanel weaponCards(ArrayList<Card> weapons) {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		int i = 1;
		if (weapons.isEmpty()) {
			return panel;
		}
		for (Card c : weapons) {
			panel.setLayout(new GridLayout(i,1));
			JTextField cards = new JTextField(c.getCardName());
			cards.setEditable(false);
			panel.add(cards);
			i++;
		}
		return panel;
	}
	
}
