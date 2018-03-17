package game.model.command;

import java.util.ArrayList;
import java.util.List;

import game.controller.GameManager;
import game.model.board.Grid;
import game.model.deck.Deck;
import game.model.player.Player;
import game.model.prototype.PrototypeFactory;

/**
 * Stores the references, and deletes them on undo request.
 * @author pratap
 *
 */
public class StoreCommand implements Command {
	private GameStatusImpl gameStatusImpl;
	private PrototypeFactory prototypeFactory;

	public StoreCommand(PrototypeFactory prototypeFactory, GameStatusImpl gameStatusImpl){
		this.prototypeFactory = prototypeFactory;
		this.gameStatusImpl = gameStatusImpl;
	}
	
	@Override
	public void execute() {
		// Get the latest grid from GameManager.
		Grid grid = GameManager.getBoardShapeGrid();
		// Get the latest list of player from GameManager.
		List<Player> players = GameManager.getPlayerList();
		Deck cardDeck = GameManager.getDeck();
		Deck discardDeck = GameManager.getDiscardPile();
		// Creating temporary Grid
		Grid tempGrid = null;
		// Creating temporary list of players
		List<Player> tempPlayers = new ArrayList<Player>();
		Deck tempCardDeck = new Deck();
		Deck tempDiscardDeck = new Deck();
		// cloning grid
		tempGrid = prototypeFactory.getClone(grid);
		// cloning individual players and added them to list
		for (Player player : players) {
			{					
				Player pla1 = prototypeFactory.getClone(player);
				tempPlayers.add(pla1);
				}
		}
		//Cloning both the decks
		tempCardDeck = prototypeFactory.getClone(cardDeck);
		tempDiscardDeck = prototypeFactory.getClone(discardDeck);
		gameStatusImpl.addTurns(tempGrid, tempPlayers,tempCardDeck,tempDiscardDeck);
	}

	@Override
	public void undo() {
		 gameStatusImpl.removeTurn();
	}

}
