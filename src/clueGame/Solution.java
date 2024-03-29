package clueGame;

/**
 * This is the Solution class, the class of that defines the accusations, suggestions, and the solution of the game
 * @author eboyle, annelysebaker
 * @version 1.1
 * 
 *
 */
public class Solution {
	public String person;
	public String room;
	public String weapon;
	
	public Solution(String person, String room, String weapon) {
		super();
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}

	@Override
	public String toString() {
		return "Solution: Person: " + person + ", Room: " + room + ", Weapon: " + weapon;
	}
	
}
