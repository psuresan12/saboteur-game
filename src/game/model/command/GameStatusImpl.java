package game.model.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import game.controller.GameManager;
import game.model.board.Grid;
import game.model.deck.Deck;
import game.model.player.Player;
import game.model.prototype.PrototypeFactory;

/**
 * 
 * @author pratap
 *
 */
@SuppressWarnings("serial")
public class GameStatusImpl implements Serializable {

	private List<Grid> turnsOfGrid;
	private List<List<Player>> turnsOfPlayers;
	private List<Deck> turnsOfCardDeck;
	private List<Deck> turnsOfDiscardDeck;
	
	private List<Player> savedPlayerList;
	private Grid savedGrid;
	private Deck savedCardDeck;
	private Deck savedDiscardDeck;
	public static Integer savedPlayerIndex;
	private PrototypeFactory prototypeFactory;

	public GameStatusImpl(PrototypeFactory prototypeFactory) {
		turnsOfGrid = new ArrayList<Grid>();
		turnsOfPlayers = new ArrayList<List<Player>>();
		turnsOfCardDeck = new ArrayList<Deck>();
		turnsOfDiscardDeck = new ArrayList<Deck>();
		this.prototypeFactory = prototypeFactory;
		
	}

	/**
	 * Returns the grid from the last index.i.e. the latest turn
	 * 
	 * @return
	 */
	public Grid getGrid() {
		Grid lastGrid = turnsOfGrid.get(turnsOfGrid.size() - 1);
		Grid clonedGrid = null;
		clonedGrid = prototypeFactory.getClone(lastGrid);
		return clonedGrid;
	}

	/**
	 * Returns the list of player from the last index.i.e. the latest turn
	 * 
	 * @return
	 */
	public List<Player> getPlayerList() {
		return turnsOfPlayers.get(turnsOfPlayers.size() - 1);
	}
	
	
	/**
	 * Returns the cardDeck from the last index
	 * @return
	 */
	public Deck getCardDeck(){
		Deck lastCardDeck = turnsOfCardDeck.get(turnsOfCardDeck.size()-1);
		Deck clonedCardDeck = null;
		clonedCardDeck = prototypeFactory.getClone(lastCardDeck);
		return clonedCardDeck;
	}

	
	/**
	 * Returns the disardDeck from the last index
	 * @return
	 */
	public Deck getDiscardDeck(){
		Deck lastDiscardDeck = turnsOfDiscardDeck.get(turnsOfDiscardDeck.size()-1);
		Deck clonedDiscardDeck = null;
		clonedDiscardDeck = prototypeFactory.getClone(lastDiscardDeck);
		return clonedDiscardDeck;
	}
	
	
	/**
	 * Writes the current session of grid and set of player and their hands in
	 * text file
	 *
	 * @param newGrid
	 * @param newPlayers
	 * @return
	 */
	public boolean saveGame(Grid newGrid, List<Player> tempPlayers, Deck tempCardDeck,Deck tempDiscardDeck) {
		boolean isSaved = false;
		File file = new File("save.ser");
		List<Object> saveGameObject = new ArrayList<Object>();
		saveGameObject.add(newGrid);
		saveGameObject.add(tempPlayers);
		saveGameObject.add(tempCardDeck);
		saveGameObject.add(tempDiscardDeck);
		saveGameObject.add((GameManager.getCurrentPlayerIndex()));
		FileOutputStream fos;
		ObjectOutputStream oos;

		try {
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(saveGameObject);
			fos.close();
			oos.close();
			isSaved = true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return isSaved;
	}

	/**
	 * Fetches the stored session of grid and set of player Hands from external file
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isResumable() {
		boolean isGameSaved = false;
		File file = new File("save.ser");
		List<Object> saveGameObject = new ArrayList<Object>();
		FileInputStream fis;
		ObjectInputStream ois;

		try {
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			saveGameObject = (List<Object>) ois.readObject();
			Grid tempGrid = (Grid) saveGameObject.get(0);
			List<Player> tempPlayers = (List<Player>) saveGameObject.get(1);
			Deck tempCardDeck = (Deck) saveGameObject.get(2);
			Deck tempDiscardDeck = (Deck) saveGameObject.get(3);
			savedPlayerIndex = (Integer) saveGameObject.get(4);
			savedPlayerList = tempPlayers;
			savedGrid = tempGrid;
			savedCardDeck = tempCardDeck;
			savedDiscardDeck = tempDiscardDeck;
			isGameSaved = true;

			fis.close();
			ois.close();
		} catch (Exception e) {}

		return isGameSaved;
	}

	/**
	 * add individual turns of players to the list
	 * 
	 * @param newGrid
	 * @param newPlayer
	 */
	public void addTurns(Grid newGrid, List<Player> newPlayer,Deck newCardDeck,Deck newDiscardDeck) {

		turnsOfPlayers.add(newPlayer);
		turnsOfGrid.add(newGrid);
		turnsOfCardDeck.add(newCardDeck);
		turnsOfDiscardDeck.add(newDiscardDeck);
		
	}

	/**
	 * add individual turns of game to the list.
	 */
	public boolean removeTurn() {
		boolean isTurnRemoved = false;

		// Get the player count for the current game
		int playerCount = GameOperations.playerCount;
		// Check if its valid to remove a single turn.
		if (turnsOfGrid.size() - playerCount >= 1) {
			for (int i = 0; i < playerCount; i++) {
				turnsOfPlayers.remove(turnsOfPlayers.size() - 1);
				turnsOfGrid.remove(turnsOfGrid.size() - 1);
				turnsOfCardDeck.remove(turnsOfCardDeck.size()-1);
				turnsOfDiscardDeck.remove(turnsOfDiscardDeck.size()-1);

			}
			isTurnRemoved = true;
		} else
			System.out.println("Unable to undo as not even a single turn completed");

		return isTurnRemoved;
	}

	/**
	 * Saves the locally stored grid and playerlist to the list.(add as turn)
	 */
	public void resumeGame(){		
		addTurns(savedGrid, savedPlayerList,savedCardDeck,savedDiscardDeck);
	}
	
	
	
	public void deleteSavedFile()
	{
		File file = new File("save.ser");
		file.deleteOnExit();
	}
	
	
	
	/**
	 * Test method to print all the grid
	 */
	public void printAllGrid() {
		System.out.println("Printing from GameStatusImpl");
		for (int i = 0; i < turnsOfGrid.size(); i++) {
			GameManager.printGrid(turnsOfGrid.get(i));
		}
	}

}
