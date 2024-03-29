/*
 * Authors: Elizabeth (Liz) Boyle, Annelyse Baker
 * Description: Board Class that handles the management of the Game board
 */


package clueGame;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import clueGUI.SuggestionDialog;

import clueGame.BoardCell;

/**
 * This is the Board class, it is the game board of the Clue Game
 * @author eboyle, annelysebaker
 * @version 1.10
 * 
 *
 */
public class Board extends JPanel{
	private int numRows; //number of rows in the game board
	private int numColumns; //number of columns in the game board
	public final static int MAX_BOARD_SIZE = 51; //max possible board size, either rows or columns 
	private BoardCell[][] board; // game board array
	private Map<Character, String> legend = new HashMap<Character, String>(); //Map of the legend of the game board, initals of the cells and what kind of space the represent
	private Map<BoardCell, Set<BoardCell>> adjMatrix = new HashMap<BoardCell, Set<BoardCell>>(); //Map of the cells and what cells are adjacent
	private Set<BoardCell> targets; //Set of cells a piece can move to in a certain roll
	private Set<BoardCell> visited; //Set of cells used in calculating the targets Set
	private String boardConfigFile; //name of the board file that will be loaded in
	private String roomConfigFile; //name of the legend file that will be loaded in
	private String weaponConfigFile; //name of the weapons file that will be loaded in
	private String playerConfigFile; //name of the player file that will be loaded in
	private ArrayList<String> roomList; //list of all the room names
	private ArrayList<String> weaponsList; //list of the weapons
	private ArrayList<String> playersList; //list of the player names
	private ArrayList<Card> startingDeck; //initial created deck
	private ArrayList<Card> shuffledDeck; //shuffled deck after solution has been picked
	private ArrayList<Player> players; //list of the active Players
	private Solution theSolution; //The Solution of the game
	public boolean moved; //if the player has moved or not
	public boolean humanTurn; // if it is the human's turn or not
	public boolean madeGuess;
	public static final int WIDTH = 34; //scale of board tiles
	public static final int HEIGHT = 34; //scale of board tiles
	public static final int SCALE = 34; //scale of board tiles
	public Solution currentSuggestion;
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	/**
	 * Initializes the board
	 * Calls loadRoomConfig() to load the legend of the game board.
	 * Calls loadBoardConfig() to load the game board layout.
	 * Calls calcAdjacencies() to create the adjacencies map (adjMatrix)
	 * @throws BadConfigFormatException Error when creating the config files
	 */
	public void initialize() throws BadConfigFormatException {
		//resets each ArrayList
		playersList = new ArrayList<String>();
		roomList = new ArrayList<String>();
		startingDeck = new ArrayList<Card>();
		players = new ArrayList<Player>();
		shuffledDeck = new ArrayList<Card>();
		weaponsList = new ArrayList<String>();
		this.loadRoomConfig(); //always call legend config first
		this.loadBoardConfig(); //set up the game board
		this.loadCardConfigFiles(); //sets up players and cards
		this.calcAdjacencies(); //creates the Map of adjacent cells
		addMouseListener(new clickListener());
	}
	/**
	 * Sets the boardConfigFile variable to the passed in layoutfile and sets the roomConfigFile variable to the passed in legendfile
	 * @param layoutfile the game board layout file
	 * @param legendfile the legend file
	 */
	public void setConfigFiles(String layoutfile, String legendfile) {
		boardConfigFile = layoutfile;
		roomConfigFile = legendfile;
	}
	/**
	 * Sets the weaponConfigFile to the passed in weaponsfile and sets the playersConfigfile to the passed in playersfile
	 * @param weaponsfile the weapons file
	 * @param playersfile the players file
	 */
	public void setCardFiles(String weaponsfile, String playersfile) {
		weaponConfigFile = weaponsfile;
		playerConfigFile = playersfile;
	}
	/**
	 * Function that draws the board
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//draws the cells
		for (int i = 0; i < board.length; i++) {
			for (BoardCell b : board[i]){
				b.draw(g);
			}
		}
		//draws targets only for humans
		if (humanTurn) {
			for (BoardCell c : this.targets) {
				c.colorTargets(g);
			}
		}
		//draws the players
		for (Player p : players) {
			p.draw(g);
		}
		//draws the names
		for (int i = 0; i < board.length; i++) {
			for (BoardCell b : board[i]){
				if (b.isName()) {
					b.addLabels(g);
				}
			}
		}

	}
	/**
	 * Loads in the legend file and makes a map based on it
	 * @throws BadConfigFormatException Error when creating the config files
	 */
	public void loadRoomConfig() throws BadConfigFormatException{
		try {
			FileReader roomReader = new FileReader(roomConfigFile);
			Scanner in = new Scanner(roomReader);
			while (in.hasNext()) {
				String[] entry = in.nextLine().split(", ");
				//Check if the room type is valid in the file
				if (entry[2].equals("Card")  || entry[2].equals("Other")) {
					legend.put(entry[0].charAt(0), entry[1]);
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
	}
	/**
	 * Reads in the game board layout file and creates the game board with BoardCells
	 * @throws BadConfigFormatException Error when creating the config files
	 */
	public void loadBoardConfig() throws BadConfigFormatException{
		try {
			//Reads in file and counts rows and columns
			FileReader boardReader = new FileReader(boardConfigFile);
			Scanner in = new Scanner(boardReader);
			int rows = 1; //starting the row counter
			numColumns = in.nextLine().split(",").length; //default number of columns
			while (in.hasNext()) {
				String cycling = in.nextLine();
				if (cycling.split(",").length != this.numColumns) { //checks if all the columns are equal
					throw new BadConfigFormatException("Inconsistent Number of Columns in File");
				}
				rows++;
			}
			numRows = rows;
			in.close();
			in = null;
			
			//Initializes board with BoardCells per the read in dimensions
			board = new BoardCell[numRows][numColumns];
			for (int i = 0; i < numRows; i++) {
				for (int j = 0; j < numColumns; j++) {
					board[i][j] = new BoardCell(i,j); //fills game board with boardCells
				}
			}
			
			//Resets the filereader to read in the characters of the cells, sets the door directions 
			FileReader entryFile = new FileReader(boardConfigFile);
			Scanner inEntry = new Scanner(entryFile);
			for (int row = 0; row < numRows; row++) {
				String entry = inEntry.nextLine();
				String[] charEntry = entry.split(","); //makes an array of each line of the board
				for (int colm = 0; colm < numColumns; colm++) {
					if (!legend.containsKey(charEntry[colm].charAt(0))) {
						throw new BadConfigFormatException("Invalid Room in Game Board File");
					}
					else {
						board[row][colm].setInitial(charEntry[colm].charAt(0)); //sets the cell type using the first character
						if (charEntry[colm].length() == 2) { //If the cell is a doorway or the name cell
							board[row][colm].setDoorDirection(charEntry[colm].charAt(1)); //Sets doorway direction per character on board
							if (charEntry[colm].charAt(1) == 'N') {board[row][colm].setLabel(true);}
						}
						else { //If cell is not a doorway nor a name cell
							board[row][colm].setDoorDirection('N');	//'N' is just passed in for default case, stands for NONE					
						}
					}
				}
			}
			inEntry.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
	}
	/**
	 * Reads in the players and weapons from their respective files and uses those and the room list to create the Cards and Players
	 */
	public void loadCardConfigFiles() {
		 //creates the ROOM cards from the room list created from the read in room file in loadRoomConfig()
		for (String x : roomList) {
			startingDeck.add(new Card(x, CardType.ROOM));
		}
		//reads in the the weapons file and creates the WEAPON cards and adds the each weapon to the weapons list
		try {
			FileReader weaponReader = new FileReader(weaponConfigFile);
			Scanner in = new Scanner(weaponReader);
			while (in.hasNext()) {
				String entry = in.nextLine();
				startingDeck.add(new Card(entry, CardType.WEAPON));
				weaponsList.add(entry);
			}
			in.close();
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		//reads in the players file and creates the Players and creates the PERSON cards
		try {
			FileReader playerReader = new FileReader(playerConfigFile);
			Scanner in = new Scanner(playerReader);
			while (in.hasNext()) {
				String entry = in.nextLine();
				startingDeck.add(new Card(entry, CardType.PERSON));
				playersList.add(entry.trim());
			}
			in.close();
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		//Adds one Human Player and 5 Computer Players
		players.add(new HumanPlayer(playersList.get(0)));
		for (int i = 1; i < 6; i++) {
			ComputerPlayer newCom = new ComputerPlayer(playersList.get(i));
			for (Card c : startingDeck) {
				newCom.addUnseenCards(c);
			}
			players.add(newCom);			
		}

		//calls the shuffle deck function
		this.shuffleAndDeal(); 
	} 
	/**
	 * Separates the startingDeck by CardType, then pulls a random one from each and creates the Solution.
	 * Then it adds the Cards all together and shuffles, then deals to each Player
	 */
	public void shuffleAndDeal() {
		//ArrayLists to separate the CardTypes
		ArrayList<Card> people = new ArrayList<Card>();
		ArrayList<Card> weapons = new ArrayList<Card>();
		ArrayList<Card> rooms = new ArrayList<Card>();
		//Separates the Cards in the starting deck by CardType
		for (Card x : startingDeck) {
			if (x.isPerson()) {
				people.add(x);
			}
			else if (x.isRoom()) {
				rooms.add(x);
			}
			else if (x.isWeapon()) {
				weapons.add(x);
			}
		}

		//Generate random numbers to choose a random Card from each CardType
		Random rand = new Random();
		int p = rand.nextInt(6); //random number between 0-5
		int r = rand.nextInt(9); //random number between 0-9
		int w = rand.nextInt(6); //random number between 0-5
		//Creates the Solution with the random Cards
		theSolution = new Solution(people.get(p).getCardName(), rooms.get(r).getCardName(), weapons.get(w).getCardName());
		//Removes the Solution Cards from the separated decks
		people.remove(p);
		rooms.remove(r);
		weapons.remove(w);

		//Adds the separated decks altogether then shuffles them
		shuffledDeck.addAll(people);
		shuffledDeck.addAll(rooms);
		shuffledDeck.addAll(weapons);
		Collections.shuffle(shuffledDeck);

		//deals the Cards out to the Players
		Random rand2 = new Random();
		int num = rand2.nextInt(6); //shuffles the Players list so that when dealt cards, its doesn't start with the same player everytime
		for (Card x : shuffledDeck) {
			switch(num){
			case 6: //if the counter reaches 6, the counter resets
				num = 0;
			default:
				players.get(num).dealCard(x);
				num++;
			}
		}

	
	}
	/**
	 * Calculates the adjacent cells of each cell in the game board
	 */
	public void calcAdjacencies() {
		//Calculates the list of adjacent grid cell from a certain point, then stores them in a Set, then
		//puts them in adjSpaces map
		for (int y = 0; y < numColumns; y++) {
			for (int x = 0; x < numRows; x++) {
				Set<BoardCell> adjSet = new HashSet<BoardCell>();
				if (this.getCellAt(x,y).isWalkway() || this.getCellAt(x,y).isDoorway()){
					if (y == 0) { //left column
						if (x == 0) {//if space is on the top left corner
							BoardCell right = this.getCellAt(x, y+1);
							BoardCell down = this.getCellAt(x+1, y);
							//Can never be a door, so don't check current space door direction
							if (down.isWalkway()||down.getDoorDirection()==DoorDirection.UP) {adjSet.add(down);}
							if (right.isWalkway()||down.getDoorDirection()==DoorDirection.LEFT) {adjSet.add(right);}
						}
						else if (x == numRows-1) { //if space is bottom left corner
							BoardCell up = this.getCellAt(x-1, y);
							BoardCell right = this.getCellAt(x, y+1);
							//Can never be a door, so don't check current space door direction
							if (up.isWalkway()||up.getDoorDirection()==DoorDirection.DOWN) {adjSet.add(up);}
							if (right.isWalkway()||right.getDoorDirection()==DoorDirection.LEFT) {adjSet.add(right);}
						}
						else { //rest of left column
							BoardCell up = this.getCellAt(x-1, y);
							BoardCell right = this.getCellAt(x, y+1);
							BoardCell down = this.getCellAt(x+1, y);
							if (this.getCellAt(x,y).isDoorway()) {
								if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.DOWN && down.isWalkway()){adjSet.add(down);}
								else if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.RIGHT && right.isWalkway()){adjSet.add(right);}
								else if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.UP && up.isWalkway()){adjSet.add(up);}
							}
							else {
								if (up.isWalkway()||up.getDoorDirection()==DoorDirection.DOWN) {adjSet.add(up);}
								if (right.isWalkway()||right.getDoorDirection()==DoorDirection.LEFT) {adjSet.add(right);}
								if (down.isWalkway()||down.getDoorDirection()==DoorDirection.UP) {adjSet.add(down);}
							}
						}
					}
					else if (y == numColumns-1) { //right column
						if (x == 0) {//if space is on the top right corner
							BoardCell left = this.getCellAt(x, y-1);						
							BoardCell down = this.getCellAt(x+1, y);
							//Can never be a door, so don't check current space door direction
							if (down.isWalkway()||down.getDoorDirection()==DoorDirection.UP) {adjSet.add(down);}
							if (left.isWalkway()||left.getDoorDirection()==DoorDirection.RIGHT) {adjSet.add(left);}					
						}
						else if (x == numRows-1) { //if space is bottom right corner
							BoardCell left = this.getCellAt(x,y-1);
							BoardCell up = this.getCellAt(x-1, y);	
							//Can never be a door, so don't check current space door direction
							if (up.isWalkway()||up.getDoorDirection()==DoorDirection.DOWN) {adjSet.add(up);}
							if (left.isWalkway()||left.getDoorDirection()==DoorDirection.RIGHT) {adjSet.add(left);}
						}
						else { //rest of right column
							BoardCell down = this.getCellAt(x+1, y);						
							BoardCell left = this.getCellAt(x,y-1);
							BoardCell up = this.getCellAt(x-1, y);	
							if (this.getCellAt(x,y).isDoorway()) {
								if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.DOWN && down.isWalkway()){adjSet.add(down);}
								else if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.LEFT && left.isWalkway()){adjSet.add(left);}
								else if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.UP && up.isWalkway()){adjSet.add(up);}
							}
							else {
								if (up.isWalkway()||up.getDoorDirection()==DoorDirection.DOWN) {adjSet.add(up);}
								if (left.isWalkway()||left.getDoorDirection()==DoorDirection.RIGHT) {adjSet.add(left);}
								if (down.isWalkway()||down.getDoorDirection()==DoorDirection.UP) {adjSet.add(down);}
							}
						}
					}
					else if (x == 0 && y != 0 && y != numColumns-1) { //top row except corners
						BoardCell left = this.getCellAt(x,y-1);
						BoardCell right = this.getCellAt(x, y+1);
						BoardCell down = this.getCellAt(x+1, y);					
						if (this.getCellAt(x,y).isDoorway()) {
							if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.DOWN && down.isWalkway()){adjSet.add(down);}
							else if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.RIGHT && right.isWalkway()){adjSet.add(right);}
							else if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.LEFT && left.isWalkway()){adjSet.add(left);}
						}
						else {
							if (left.isWalkway()||left.getDoorDirection()==DoorDirection.RIGHT) {adjSet.add(left);}
							if (right.isWalkway()||right.getDoorDirection()==DoorDirection.LEFT) {adjSet.add(right);}
							if (down.isWalkway()||down.getDoorDirection()==DoorDirection.UP) {adjSet.add(down);}
						}			
					}
					else if (x == numRows-1 && y != 0 && y != numColumns-1) { //bottom row except corners
						BoardCell up = this.getCellAt(x-1, y);
						BoardCell left = this.getCellAt(x,y-1);
						BoardCell right = this.getCellAt(x, y+1);
						if (this.getCellAt(x,y).isDoorway()) {
							if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.LEFT && left.isWalkway()){adjSet.add(left);}
							else if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.RIGHT && right.isWalkway()){adjSet.add(right);}
							else if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.UP && up.isWalkway()){adjSet.add(up);}
						}
						else {
							if (up.isWalkway()||up.getDoorDirection()==DoorDirection.DOWN) {adjSet.add(up);}
							if (right.isWalkway()||right.getDoorDirection()==DoorDirection.LEFT) {adjSet.add(right);}
							if (left.isWalkway()||left.getDoorDirection()==DoorDirection.RIGHT) {adjSet.add(left);}
						}
					}
					else { //rest of the board
						BoardCell left = this.getCellAt(x,y-1);
						BoardCell right = this.getCellAt(x, y+1);
						BoardCell down = this.getCellAt(x+1, y);
						BoardCell up = this.getCellAt(x-1, y);
						if (this.getCellAt(x,y).isDoorway()) {
							if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.DOWN && down.isWalkway()){adjSet.add(down);}
							else if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.LEFT && left.isWalkway()){adjSet.add(left);}
							else if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.RIGHT && right.isWalkway()){adjSet.add(right);}
							else if (this.getCellAt(x,y).getDoorDirection()==DoorDirection.UP && up.isWalkway()){adjSet.add(up);}
						}
						else {
							if (up.isWalkway()||up.getDoorDirection()==DoorDirection.DOWN) {adjSet.add(up);}
							if (right.isWalkway()||right.getDoorDirection()==DoorDirection.LEFT) {adjSet.add(right);}
							if (left.isWalkway()||left.getDoorDirection()==DoorDirection.RIGHT) {adjSet.add(left);}
							if (down.isWalkway()||down.getDoorDirection()==DoorDirection.UP) {adjSet.add(down);}
						}
					}
				}
				this.adjMatrix.put(getCellAt(x,y), adjSet); //puts the created adjacent cells Set in a Map with the cell as the key
			}
		}
	}
	/**
	 * Returns the set of adjacent cells to the cells at the passed in coordinates
	 * @param x x-coordinate of a certain cell
	 * @param y y-coordinate of a certain cell
	 * @return List of adjacent cells to the passed in cell
	 */
	public Set<BoardCell> getAdjList(int x, int y) {	//Returns the adjacency list for one cell
		BoardCell space = this.getCellAt(x, y);
		return adjMatrix.get(space);
	}
	/**
	 * This is the function that calculates the cells we can move from a dice roll
	 * @param row the row coordinate of the current cell
	 * @param colm the column coordinate of the current cell
	 * @param pathLength the length of the dice roll
	 */
	public void calcTargets(int row, int colm, int pathLength) {
		//Calculates the targets that are pathLength distance from the startCell. 
		//The list of targets will be stored in an instance variable.
		BoardCell cell = this.getCellAt(row, colm);
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();

		visited.add(cell);//add current space to the visited list
		findAllTargets(cell, pathLength);
		targets.remove(cell);
	}
	/**
	 * This is the recursive function called by calcTargets that calculates the spaces the piece can move to from a dice roll
	 * @param thisSpace the current Board Cell
	 * @param pathLength the dice roll, which determines how many times we call this method 
	 */
	public void findAllTargets(BoardCell thisSpace, int pathLength) {
		if (pathLength == 0) { //return case
			visited.remove(thisSpace); 
			return;
		}
		for (BoardCell adjSpace : adjMatrix.get(thisSpace)) {
			if (!visited.contains(adjSpace)) {
				visited.add(adjSpace);
				if (adjSpace.isDoorway()) {checkDoorEntry(adjSpace, thisSpace);} //checks if entered from the correct direction
				else if (pathLength == 1) { //when looking at end of the roll
					if (adjSpace.isWalkway()){targets.add(adjSpace);}
					else if (adjSpace.isDoorway()) {checkDoorEntry(adjSpace, thisSpace);}
				}
				else {
					findAllTargets(adjSpace, pathLength-1);
				}
				visited.remove(adjSpace);
			}
		}
	}
	/**
	 * This function is used to check to see if a player can enter a doorway from their current direction
	 * @param adjSpace The doorway space
	 * @param thisSpace The space the player is on
	 */
	private void checkDoorEntry(BoardCell adjSpace, BoardCell thisSpace) {//checks if entered from the correct direction
		if(adjSpace.getDoorDirection()==DoorDirection.RIGHT && thisSpace.getRow()==adjSpace.getRow() && thisSpace.getColumn() == adjSpace.getColumn()+1){							
			targets.add(adjSpace);
		}
		else if (adjSpace.getDoorDirection()==DoorDirection.LEFT && thisSpace.getRow()==adjSpace.getRow() && thisSpace.getColumn() == adjSpace.getColumn()-1){
			targets.add(adjSpace);
		}
		else if(adjSpace.getDoorDirection()==DoorDirection.UP && thisSpace.getColumn()==adjSpace.getColumn() && thisSpace.getRow() == adjSpace.getRow()-1){
			targets.add(adjSpace);
		}
		else if (adjSpace.getDoorDirection()==DoorDirection.DOWN && thisSpace.getColumn()==adjSpace.getColumn()&&thisSpace.getRow() == adjSpace.getRow()+1){
			targets.add(adjSpace);
		}
	}
	/**
	 * Takes in a Player and their Suggestion and goes around the Players to see if someone can disprove it. Returns the Card they disproved with,
	 * or null if only the Player can disprove or no one can disprove
	 * @param suggestion
	 * @param currentPlayer
	 * @return
	 */
	public Card handleSuggestion(Solution suggestion, Player currentPlayer) {
		currentSuggestion = suggestion;
		int currentSpot = players.indexOf(currentPlayer);
		int counter = 0;
		while (counter < players.size()) {
			if (players.get(currentSpot).disproveSuggestion(suggestion) != null) {
				if (players.get(currentSpot) != currentPlayer) {
					Card response = players.get(currentSpot).disproveSuggestion(suggestion);
					for (Player p : players) {
						if (!p.isHuman()) {
							ComputerPlayer c = (ComputerPlayer) p;
							c.processResponse(response);
						}
					}
					return response;
				}
			}
			if(currentSpot == players.size()-1) {currentSpot = 0;}
			else {currentSpot++;}
			counter++;
		}
		return null;
	}
	/**
	 * Takes in a Player and a Solution that is a Player's Accusation and checks to see if it is correct, return true if so, false if wrong
	 * @param accusation - Player's accusation
	 * @return 
	 */
	public boolean checkAccusation(Solution accusation, Player currentPlayer) {
		if (accusation.person == this.theSolution.person && accusation.room == this.theSolution.room && accusation.weapon == this.theSolution.weapon) {return true;}
		else {return false;}
	}
	/**
	 * 
	 */
	public void gameWon(Player p) {
		if (p.isHuman()) {
			JDialog winner = new JDialog();
			String name = p.getPlayerName();
			winner.setTitle("You Won!");
			winner.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
			winner.setSize(400, 150);
			winner.setLocationRelativeTo(null);
			winner.setLayout(new GridLayout(4,1));
			JLabel message = new JLabel("You accused correctly. You Won!");
			JLabel message2 = new JLabel("The correct solution was: ");
			JLabel solution = new JLabel(theSolution.person + " in the " + theSolution.room + " with the " + theSolution.weapon + "!!!");
			solution.setHorizontalAlignment((int) CENTER_ALIGNMENT);
			message2.setHorizontalAlignment((int) CENTER_ALIGNMENT);
			message.setHorizontalAlignment((int) CENTER_ALIGNMENT);
			winner.add(message, BorderLayout.CENTER);
			winner.add(message2, BorderLayout.CENTER);
			winner.add(solution, BorderLayout.CENTER);
			JButton oK = new JButton("OK");
			class ExitListener implements ActionListener{
				public void actionPerformed(ActionEvent e) {
					winner.dispose();
					System.exit(0);}
			}
			oK.addActionListener(new ExitListener());
			winner.add(oK);
			winner.setVisible(true);
		}
		else {
			JDialog winner = new JDialog();
			String name = p.getPlayerName();
			winner.setTitle(name + " Won!");
			winner.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
			winner.setSize(400, 150);
			winner.setLocationRelativeTo(null);
			winner.setLayout(new GridLayout(4,1));
			JLabel message = new JLabel(name + " accused correctly. You lose!");
			JLabel message2 = new JLabel("The correct solution was: ");
			JLabel solution = new JLabel(theSolution.person + " in the " + theSolution.room + " with the " + theSolution.weapon + "!!!");
			solution.setHorizontalAlignment((int) CENTER_ALIGNMENT);
			message2.setHorizontalAlignment((int) CENTER_ALIGNMENT);
			message.setHorizontalAlignment((int) CENTER_ALIGNMENT);
			winner.add(message, BorderLayout.CENTER);
			winner.add(message2, BorderLayout.CENTER);
			winner.add(solution, BorderLayout.CENTER);
			JButton oK = new JButton("OK");
			class ExitListener implements ActionListener{
				public void actionPerformed(ActionEvent e) {
					winner.dispose();
					System.exit(0);}
			}
			oK.addActionListener(new ExitListener());
			winner.add(oK);
			winner.setVisible(true);
		}
	}
	/**
	 * Find the human player in the list and returns them
	 * @return Player
	 */
	public Player getHumanPlayer() {
		for (Player p : players) {
			if (p.isHuman()) {
				return p;
			}
		}
		return null;
	}
	
	/**
	 * Generates a random number between 1 and 6
	 * @return generated number
	 */
	public int rollDie() {
		Random rand = new Random();
		int p = rand.nextInt(6)+1; //random number between 1-6
		return p;
	}
	
	/**
	 * Controls what happens in the turn based on whether the player is a human or not
	 * @param p - current player
	 * @param roll - roll of the die
	 */
	public void turnControl(Player p, int roll) {
		if (p.isHuman()) { //when Human Player's turn
			humanTurn = true;
			moved = false;
			madeGuess = false;
			p.humanTargets(roll);
			repaint();
		}
		else { //when Computer's turn
			humanTurn = false;
			moved = false;
			p.move(roll, 0, 0);
			moved = true;
			repaint();
		}
	}
	/**
	 * Translates the player's click into boardcell, if not a target, gives error message
	 * @param x
	 * @param y
	 * @return
	 */
	public BoardCell clickedTarget(int x, int y) {
		for (BoardCell c : targets) {
			Rectangle rect = new Rectangle(c.getColumn()*SCALE, c.getRow()*SCALE, WIDTH, HEIGHT);
			if (rect.contains(new Point(x,y))&& !madeGuess) {return c;}
			else if (madeGuess) {
				JDialog oops = new JDialog();
				oops.setTitle("Oops");
				oops.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
				oops.setSize(380, 130);
				oops.setLocationRelativeTo(null);
				oops.setLayout(new GridLayout(3,1));
				JLabel message = new JLabel("Oops! You have already made a suggestion or accusation.");
				JLabel message2 = new JLabel("You must click Next Player to continue.");
				message.setHorizontalAlignment((int) CENTER_ALIGNMENT);
				message2.setHorizontalAlignment((int) CENTER_ALIGNMENT);
				oops.add(message, BorderLayout.CENTER);
				oops.add(message2, BorderLayout.CENTER);
				JButton oK = new JButton("OK");
				class ExitListener implements ActionListener{
					public void actionPerformed(ActionEvent e) {
						oops.dispose();}
				}
				oK.addActionListener(new ExitListener());
				oops.add(oK); 
				oops.setVisible(true);
				break;
			}
		}
		if (humanTurn && !madeGuess) {
			JDialog oops = new JDialog();
			oops.setTitle("Oops");
			oops.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
			oops.setSize(320, 100);
			oops.setLocationRelativeTo(null);
			oops.setLayout(new GridLayout(2,1));
			JLabel message = new JLabel("Oops! You can't move there, try clicking a cyan tile.");
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
		}
		
		return null;
	}

	/**
	 * Listener for click for human player movement
	 * @author eboyle, annelysebaker
	 *
	 */
	class clickListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent event) {
			BoardCell target = clickedTarget(event.getX(), event.getY());
			if (target != null && !madeGuess) {
				movePlayer(target);
			}
			repaint();			
		}
		public void mouseExited(MouseEvent event) {}
		public void mousePressed (MouseEvent event) {}
		public void mouseReleased (MouseEvent event) {}
		public void mouseEntered (MouseEvent event) {}
		
	}
	/**
	 * Calls move function for human player
	 * @param c
	 */
	public void movePlayer(BoardCell c) {
		for (Player p : players) {
			if (p.isHuman() && humanTurn) {
				p.move(0, c.getRow(), c.getColumn());
				moved = true;
				if (c.isRoom()) {
					SuggestionDialog sugg = new SuggestionDialog(legend.get(c.getInitial()));
					sugg.setVisible(true);
				}
			}
		}
	}
	
	public Map<Character, String> getLegend() {
		return legend;
	}

	public int getNumRows() {
		return board.length;
	}

	public int getNumColumns() {
		return board[0].length;
	}

	public BoardCell getCellAt(int row, int colm) {
		return board[row][colm];
	}

	public Set<BoardCell> getTargets() {//Returns the list of targets.
		return this.targets;
	}
	
	public ArrayList<Card> getCardDeck() {return startingDeck;}
	
	public ArrayList<Card> getShuffledDeck() {return shuffledDeck;}
	
	public ArrayList<Player> getPlayers() {return players;}
	
	public ArrayList<String> getRoomList() {return roomList;}
	
	public Solution getSolution() {return theSolution;}
	
	public ArrayList<String> getWeaponsList() {return weaponsList;}
	
	public ArrayList<String> getPlayerList() {
		return playersList;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players.clear();
		this.players = players;
	}
	public boolean movedYet() {
		if (moved) {return true;}
		else {return false;}
	}

}
