package game.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import game.exceptions.ActionCardFunctionFailed;
import game.exceptions.CannotAddPathCardException;
import game.exceptions.ImpotentPlayerException;
import game.exceptions.IncorrectActionCardPairingException;
import game.exceptions.IncorrectPersonalCardPairingException;
import game.exceptions.MissingStartCardGoalCardException;
import game.exceptions.NumberOfPlayersException;
import game.exceptions.ServiceDownException;
import game.exceptions.UnconnectedPathException;
import game.model.board.Coordinate;
import game.model.board.Grid;
import game.model.board.HexGrid;
import game.model.board.RectangleGrid;
import game.model.board.SquareGrid;
import game.model.board.TriangleGrid;
import game.model.card.Card;
import game.model.command.GameOperations;
import game.model.command.GameStatusImpl;
import game.model.deck.Deck;
import game.model.interfaces.ConsoleGameMenu;
import game.model.interfaces.GameMenuView;
import game.model.player.Player;
import game.model.player.Role;
import game.model.player.Saboteur;
import game.model.player.SimplePlayer;
import game.model.player.Worker;
import game.model.shape.HexShape;
import game.model.shape.RectangleShape;
import game.model.shape.Shape;
import game.model.shape.SquareShape;
import game.model.shape.TriangleShape;
import game.viewer.board.AppFrame;
import game.viewer.hexagon.AppHexagonalPanel;

/**
 * GameManager is a Singleton Controller that implements ConsoleGameMenu (for
 * console management) and GameMenuView for UI.
 * 
 * 
 * @author ehiew
 *
 */
public class GameManager implements ConsoleGameMenu, GameMenuView {

	/* Static constants */
	private static final int MAX_GAME_ROUNDS = 3;
	private static final int TREASURE_GOLD = 100;

	/* Singleton Controller */
	private static GameManager game = null;
	public static List<Boolean> undoStatus;

	/* Static Variables */
	private static Grid board; // Grid
	private static Shape shape; // Card Shape
	private static Deck cardDeck; // Deck of Play Cards,
	private static Deck discardPile; // Discard Pile
	private static List<Player> players; // Players
	private static int gameRoundsCounter = 0; // Start of Round
	private static int currentPlayerIndex = 0; // Index of Players
	private static int gameStateIndex = 0; // Index of Game State
	private static int numPerRole; // number of players per role

	/* Game Behaviour flags */
	private static boolean reverseDirection = false; // Direction of player
														// turns

	/* GameOperation variable for storing and saving the game */
	private static GameOperations gameOperation;

	/* Game Init flags */
	private boolean isBoardInit;
	private boolean isPlayerInit;
	private boolean isCardInit;

	/**
	 * Private Singleton Constructor
	 */
	private GameManager() {
		// defaults for testing
		board = null;
		shape = null;
		// initialise deck
		cardDeck = null;
		// initialise discard pile deck
		discardPile = new Deck();
		// initialise players list
		players = new LinkedList<Player>();
		undoStatus = new ArrayList<Boolean>();
		// game init flags
		isBoardInit = false;
		isPlayerInit = false;
		isCardInit = false;
		gameOperation = new GameOperations();
		numPerRole = 0;

	}

	/**
	 * Static Method to get Singleton instance. Synchronised for Thread-safety.
	 * 
	 * @Requires
	 * @return
	 */
	public static synchronized GameManager getInstance() {
		if (game == null) {
			return new GameManager();
		}
		return game;
	}

	@Override
	public void startGame(int numPlayers, Shape shape, Grid boardGrid) {
		// Initialise Board Shape
		setBoardShapeGrid(boardGrid);
		if (!getIsBoardInit())
			initBoard();

		// Create Card Shape and then the Deck (shuffle)
		setCardShape(shape);
		if (!getIsCardInit())
			initDeck(shape);

		// Create Players using number players.
		if (!getIsPlayerInit())
			initPlayers(numPlayers);

		// Deal cards from deck to Player Hands
		for (Player player : players) {
			// loop through player hands and deal appropriate card numbers.
			cardDeck.deal(player.getHand());
		}
		// Passing player count during initialization
		gameOperation.addPlayerCount(players.size());

		// call the viewer
		updateTakeTurn();
	}

	private void initDeck(Shape shape) {
		cardDeck = new Deck(shape);
	}

	private void initPlayers(int numPlayers) {
		setNumberPlayers(numPlayers);
		setNumPerRole(numPlayers / 2);
	}

	/**
	 * This is called by the controller to update the viewer
	 * 
	 */
	public static void updateTakeTurn() {
		// set the current player
		Player player = getCurrentPlayer();

		// capture state of game
		gameOperation.storeTurn();

		// Saboteurs Win
		if (checkCardsLeft())
			saboteursWin();

		// call viewer
		AppFrame.getInstance().populateGameBoard(getBoardShapeGrid(), player);
	}

	private static boolean checkCardsLeft() {
		// check all player hands if empty
		int cardsLeft = 0;
		for (Player p : getPlayerList())
			cardsLeft += p.getHand().getPlayerHandSize();
		return cardsLeft == 0 ? true : false;
	}

	/**
	 * Iterates through the players and interacts with the player hands. Send
	 * control to UI. EventListeners to handle click and drag from UI.
	 * 
	 * @param players
	 * @throws IncorrectActionCardPairingException
	 * @throws CannotAddPathCardException
	 * @throws UnconnectedPathException
	 * @throws MissingStartCardGoalCardException
	 * @throws ImpotentPlayerException
	 * @throws ActionCardFunctionFailed
	 * @throws IncorrectPersonalCardPairingException
	 * @throws ServiceDownException
	 */
	public static void takeTurn(int handIndex, Coordinate xy, boolean isDiscarded, int targetPlayerIndex)
			throws CannotAddPathCardException, IncorrectActionCardPairingException, UnconnectedPathException,
			MissingStartCardGoalCardException, ActionCardFunctionFailed, ImpotentPlayerException,
			IncorrectPersonalCardPairingException, ServiceDownException {
		Player player = getCurrentPlayer();
		// player take turn
		player.takeTurn(getBoardShapeGrid(), handIndex, xy, cardDeck, discardPile, isDiscarded, targetPlayerIndex);
		// notify the viewer of update
		Player currentPlayer = getCurrentPlayer();
		currentPlayer.finalOperation(); // as per the decorator pattern
		resetAllotherPlayers(); // Reset all other player, to ensure the
								// settings for decorator pattern
		updateTakeTurn();

	}

	public static Player getCurrentPlayer() {
		return players.get(currentPlayerIndex);
	}

	public static List<Player> getPlayerList() {
		return players;
	}

	public static void incrementPlayerIndex() {
		if (currentPlayerIndex == players.size() - 1) {
			currentPlayerIndex = 0;
			return;
		}
		currentPlayerIndex++;
		skipPlayerOnSick(); // checks for player skip turn logic
	}

	public static void decrementPlayerIndex() {
		if (currentPlayerIndex == 0) {
			currentPlayerIndex = players.size() - 1;
			return;
		}
		currentPlayerIndex--;
		skipPlayerOnSick(); // checks for player skip turn logic
	}

	private static void skipPlayerOnSick() {
		if (players.get(currentPlayerIndex).getSick() != 0) {
			Player tempPlayer = players.get(currentPlayerIndex);
			// Reduce the sick count (Skips the turn to next player)
			tempPlayer.setSick(tempPlayer.getSick() - 1);
			incrementPlayerIndex(); // Making the recursion call
		}
	}

	/**
	 * Reset the all other player, except the current player, to ensure the
	 * decorator pattern works well, and so that the decoratable player cannot
	 * play any extra turns
	 */
	private static void resetAllotherPlayers() {

		for (int i = 0; i < players.size(); i++) {
			if (currentPlayerIndex != i)
				players.get(i).setNewRound(true);
		}
	}

	public static Shape getCardShape() {
		return shape;
	}

	public static Grid getBoardShapeGrid() {
		return board;
	}

	public static Deck getDeck() {
		return cardDeck;
	}

	/**
	 * Based on the shape of board selected in the menu, the board Coordinates
	 * and TilePanes need to be initialized. During this, GoalCards and
	 * StartingCard need to be positioned, according to the board shape.
	 * 
	 */
	private void initBoard() {
		// Create Board & Coordinate objects <UI Call>
		// Layout Randomised Goal Cards & Starting Card <UI Call>
		board.initBoard();
		// set flag for board initialization
		setBoardInit(true);
	}

	/**
	 * 
	 * @throws NumberOfPlayersException
	 */
	@Override
	public void setNumberPlayers(int numPlayers) { 
		if (numPlayers > SimplePlayer.getMaxPlayers() || numPlayers < SimplePlayer.getMinPlayers()) {
			// throw new NumberOfPlayersException();
		}

		for (int i = 0; i < numPlayers; i++) {
			SimplePlayer player = new SimplePlayer();
			player.setRole(randomRole());
			players.add(player);
			undoStatus.add(false);
		}
		// flag for Player Initialisation
		setPlayerInit(true);
	}

	/**
	 * Role is allocated randomly for each player using Math.random()*2.
	 * 
	 * @return
	 */
	private Role randomRole() {
		
		int role = (int) (Math.random() * 2);
		return role < 1 ? new Worker() : new Saboteur();
	}

	@Override
	public void setBoardShapeGrid(Grid boardShape) {
		board = boardShape;
	}

	@Override
	public void setCardShape(Shape newshape) {
		shape = newshape;
	}

	@Override
	public void exit() {
		System.exit(0);
	}

	public static int getGameRounds() {
		return gameRoundsCounter;
	}

	public static void incrementGameRounds() {
		GameManager.gameRoundsCounter++;
	}

	public boolean getIsBoardInit() {
		return isBoardInit;
	}

	public void setBoardInit(boolean isBoardInit) {
		this.isBoardInit = isBoardInit;
	}

	public boolean getIsPlayerInit() {
		return isPlayerInit;
	}

	public void setPlayerInit(boolean isPlayerInit) {
		this.isPlayerInit = isPlayerInit;
	}

	public boolean getIsCardInit() {
		return isCardInit;
	}

	public void setCardInit(boolean isCardInit) {
		this.isCardInit = isCardInit;
	}

	public static Deck getDiscardPile() {
		return discardPile;
	}

	public void setDiscardPile(Deck newdiscardPile) {
		discardPile = newdiscardPile;
	}

	public static int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	public static int getMaxGameRounds() {
		return MAX_GAME_ROUNDS;
	}

	public static Card getNextCardFromDeck() {
		return cardDeck.removeNextCard();
	}

	public static boolean isReverseDirection() {
		return reverseDirection;
	}

	public static void setReverseDirection(boolean reverseDirection) {
		GameManager.reverseDirection = reverseDirection;
	}

	public static int getGameStateIndex() {
		return gameStateIndex;
	}

	public static void setGameStateIndex(int gameStateIndex) {
		GameManager.gameStateIndex = gameStateIndex;
	}

	public static Player getPlayerByIndex(int index) {
		Player tempPlayer = null;
		if (index < players.size() && index > -1)
			tempPlayer = players.get(index);

		return tempPlayer;

	}

	public static void setPlayerByIndex(Player player, int index) {
		
		if (index < players.size() && index > -1 && player != null)
			players.set(index, player);
	}

	/**
	 * Display the Game interface.
	 */
	@Override
	public void display() {
		// Read User Input
		Scanner scan = new Scanner(System.in);

		// Show options
		displayMenu(scan);

		// close input stream
		scan.close();
	}

	public void displayMenu(Scanner scan) {
		int choice = 0;

		while (choice != 5) {
			// Display Main Menu options
			System.out.print("Saboteur\n");
			System.out.print("********\n");
			System.out.println();
			System.out.println("1. Start Game");
			System.out.println("2. Player Settings");
			System.out.println("3. Board Settings");
			System.out.println("4. Card Settings");
			System.out.println("5. Exit");

			// Read user input
			choice = scan.nextInt();

			switch (choice) {
			case 1:
				startGame(SimplePlayer.getMinPlayers(), getCardShape(), getBoardShapeGrid());
				break;
			case 2:
				displayPlayerSettings(scan);
				break;
			case 3:
				displayBoardSettings(scan);
				break;
			case 4:
				displayCardSettings(scan);
				break;
			case 5:
				exit();
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void displayPlayerSettings(Scanner scan) {
		String buffer = "Player Settings\n";
		buffer += "***************\n";
		buffer += "\n";
		buffer += "1. 3-Players\n";
		buffer += "2. 4-Players\n";
		buffer += "3. 5-Players\n";
		buffer += "4. 6-Players\n";
		buffer += "5. Return to Main Menu\n";
		System.out.print(buffer);

		// Read user input
		int choice = scan.nextInt();
		switch (choice) {
		case 1:
			setNumberPlayers(3);
			break;
		case 2:
			setNumberPlayers(4);
			break;
		case 3:
			setNumberPlayers(5);
			break;
		case 4:
			setNumberPlayers(6);
		case 5:
			return;
		default:
			System.out.println("Type an option between 3 and 6.");
		}
	}

	@Override
	public void displayBoardSettings(Scanner scan) {
		String buffer = "Board Settings\n";
		buffer += "***************\n";
		buffer += "\n";
		buffer += "1. Rectangle Board\n";
		buffer += "2. Square Board\n";
		buffer += "3. Triangle Board\n";
		buffer += "4. Hexagonal (flat) Board\n";
		buffer += "5. Hexagonal (pointed) Board\n";
		buffer += "6. Return to Main Menu\n";
		System.out.print(buffer);

		boolean isFlat;

		// Read user input
		int choice = scan.nextInt();

		switch (choice) {
		case 1:
			setBoardShapeGrid(new RectangleGrid());
			break;
		case 2:
			setBoardShapeGrid(new SquareGrid());
			break;
		case 3:
			setBoardShapeGrid(new TriangleGrid());
			break;
		case 4:
			isFlat = true;
			setBoardShapeGrid(new HexGrid(isFlat));
			break;
		case 5:
			isFlat = true;
			setBoardShapeGrid(new HexGrid(isFlat));
			break;
		case 6:
			return; // return to main menu
		default:
			break;
		}
	}

	@Override
	public void displayCardSettings(Scanner scan) {
		String buffer = "";
		buffer += "Card Settings\n";
		buffer += "***************\n";
		buffer += "\n";
		buffer += "1. Rectangle Cards\n";
		buffer += "2. Square Cards\n";
		buffer += "3. Triangle Cards\n";
		buffer += "4. Hexagonal (flat) Cards\n";
		buffer += "5. Hexagonal (pointed) Cards\n";
		buffer += "6. Return to Main Menu\n";
		System.out.print(buffer);

		boolean isFlat;

		// Read user input
		int choice = scan.nextInt();
		switch (choice) {
		case 1:
			setCardShape(new RectangleShape());
			break;
		case 2:
			setCardShape(new SquareShape());
			break;
		case 3:
			setCardShape(new TriangleShape());
			break;
		case 4:
			isFlat = true;
			setCardShape(new HexShape(isFlat));
			break;
		case 5:
			isFlat = false;
			setCardShape(new HexShape(isFlat));
			break;
		case 6:
			return; // return to main menu
		default:
			break;
		}
	}

	/**
	 * Perform UNDO operation .View calls this method. UNDO functionality is
	 * delegated to GameOperation class.
	 */
	public static void undo(int number) {
		// Perform undo operation for given number of times.
		undoStatus.set(currentPlayerIndex, true);
		while (number != 0) {
			gameOperation.UndoTurn();
			number--;
		}

		board = gameOperation.getGrid();
		players = gameOperation.getPlayerList();
		cardDeck = gameOperation.getCardDeck();
		discardPile = gameOperation.getDiscardDeck();

		// printGrid(boardShape);
		AppFrame.getInstance().populateGameBoard(getBoardShapeGrid(), getCurrentPlayer());

	}

	/**
	 * Perform saveGame operation.View calls this method.Save game functionality
	 * is delegated to GameOperation class.
	 * 
	 */
	public static void saveGame() {
		gameOperation.saveGame();
	}

	/**
	 * Perform resumeGame operation. Loads the grid and list of player from
	 * external file, to play the game from previous saved state.
	 */
	public static void resumeGame() {
		gameOperation.restoreGame();
		board = gameOperation.getGrid();
		players = gameOperation.getPlayerList();
		cardDeck = gameOperation.getCardDeck();
		discardPile = gameOperation.getDiscardDeck();
		currentPlayerIndex = GameStatusImpl.savedPlayerIndex;

		// Reset the undo operation to all players
		for (int i = 0; i < players.size(); i++) {
			undoStatus.add(false);
		}
		AppFrame.getInstance().populateGameBoard(getBoardShapeGrid(), getCurrentPlayer());
	}

	/**
	 * Checks if the game isResumable from the saved Game
	 * 
	 * @return boolean variable
	 */
	public static boolean isGameResumable() {
		return gameOperation.isGameResumable();
	}

	/**
	 * Deletes the stored file that contains grid and list of athlete
	 */
	public static void deleteStoredFile() {
		gameOperation.deleteSavedFile();
	}

	public static int getPlayerCount() {
		return players.size();
	}

	/**
	 * Timer Functionality to automatically shift the next player
	 */
	public static void playerTookTooLong() {
		GameManager.incrementPlayerIndex();
		updateTakeTurn();
	}

	/**
	 * Test Method to print the Gird
	 * 
	 * @param grid
	 */
	public static void printGrid(Grid grid) {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				for (Coordinate c : grid.getGrid().keySet()) {
					if (i == c.getRow() && (j == c.getCol())) {
						Card card = grid.getCardFromGrid(c);
						if (card != null)
							System.out.println(i + " " + j + " :" + card);
						else
							System.out.println(i + " " + j + " :null");
					}
				}
			}
		}
	}

	/**
	 * Test method to print players and their list of cards.
	 * 
	 * @param players2
	 */
	public static void printPlayers(List<Player> players2) {
		for (int j = 0; j < players2.size(); j++) {
			List<Card> cardList = players2.get(j).getHand().getCard();
			int size = players2.get(j).getHand().getCard().size();
			for (int i = 0; i < size; i++) {
				System.out.println("card no: " + (i + 1) + "-" + cardList.get(i));
			}
		}
		System.out.println("Finished printing players");

	}

	public static void workersWin() {
		Role role;
		for (Player p : GameManager.getPlayerList()) {
			role = p.getRole();
			if (role instanceof Worker)
				role.calculateGold(p, TREASURE_GOLD, getNumPerRole());
		}
		// update viewer
		AppHexagonalPanel.getInstance().messageBox.setText("Workers Win!!! " + TREASURE_GOLD + " shared!!!");
		AppHexagonalPanel.getInstance().getTimer().stop();
	}

	private static void saboteursWin() {
		Role role;
		for (Player p : GameManager.getPlayerList()) {
			role = p.getRole();
			if (role instanceof Saboteur)
				role.calculateGold(p, TREASURE_GOLD, getNumPerRole());
		}
		// update viewer
		AppHexagonalPanel.getInstance().messageBox.setText("Saboteurs Win!!! " + TREASURE_GOLD + " shared!!!");
		AppHexagonalPanel.getInstance().getTimer().stop();
	}

	private static int getNumPerRole() {
		return numPerRole;
	}

	private void setNumPerRole(int num) {
		numPerRole = num;
	}

}