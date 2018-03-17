package game.model.command;

import java.util.List;

import game.model.board.Grid;
import game.model.deck.Deck;
import game.model.player.Player;
import game.model.prototype.PrototypeFactory;

/**
 * Invoker that invokes the command operation.Creates two commands once, and allow 
 * the ability to call those commands multiple times.
 * @author pratap
 *
 */
public class GameOperations {
	private Command saveCommand, storeCommand;
	private GameStatusImpl gameStatusImpl;
	public static int playerCount;
	

	/**
	 * Constructor that initiates the command object
	 * 
	 * @param newGrid
	 * @param newPlayers
	 */
	public GameOperations() {
		PrototypeFactory prototypeFactory  = new PrototypeFactory();
		gameStatusImpl = new GameStatusImpl(prototypeFactory);
		storeCommand = new StoreCommand(prototypeFactory,gameStatusImpl);
		saveCommand = new SaveCommand(prototypeFactory,gameStatusImpl);
	}

	public void addPlayerCount(int newplayerCount) {
		playerCount = newplayerCount;
	}

	/**
	 * Stores the turns of each turn. Need to call this method for every turns.
	 */
	public void storeTurn() {
		storeCommand.execute();
	}

	/**
	 * Rewind the game state based on number of number of turn.
	 * 
	 * @param number
	 */
	public void UndoTurn() {
		storeCommand.undo();
	}

	/**
	 * Additional feature to store the game state in file database
	 */
	public void saveGame() {
		saveCommand.execute();
	}

	/**
	 * Additional feature to resumes the game state from the file database.
	 */
	public void restoreGame() {
		saveCommand.undo();

	}

	/**
	 * Returns the current state of the grid
	 * 
	 * @return
	 */
	public Grid getGrid() {
		return gameStatusImpl.getGrid();
	}

	/**
	 * Returns the current state of the player lists
	 * 
	 * @return
	 */
	public List<Player> getPlayerList() {
		return gameStatusImpl.getPlayerList();
	}
	
	
	/**
	 * Returns the current state of the cardDeck
	 * @return
	 */
	public Deck getCardDeck(){
		return gameStatusImpl.getCardDeck();
	}

	
	/**
	 * Returns the current state of discardDeck
	 * @return
	 */
	public Deck getDiscardDeck(){
		return gameStatusImpl.getDiscardDeck();
	}
	
	
	/**
	 * Check if the game is resumable and played from the stored file
	 * @return boolean variable
	 */
	public boolean isGameResumable() {
		return gameStatusImpl.isResumable();
	}

	
	/**
	 * 
	 */
	public void deleteSavedFile(){
		gameStatusImpl.deleteSavedFile();
	}
	
}
