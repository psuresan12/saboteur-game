package game.model.command;

import java.util.ArrayList;
import java.util.List;

import game.controller.GameManager;
import game.model.board.Grid;
import game.model.deck.Deck;
import game.model.player.Player;
import game.model.prototype.PrototypeFactory;

/**
 * Save command operation that saves the references into file and get the reference before 
 * loading the game,if references are stored earlier.
 * @author pratap
 *
 */
public class SaveCommand implements Command {

	private PrototypeFactory prototypeFactory;
	private GameStatusImpl gameStatusImpl;
	
	public SaveCommand(PrototypeFactory prototypeFactory, GameStatusImpl gameStatusImpl){
		this.prototypeFactory = prototypeFactory;
		this.gameStatusImpl = gameStatusImpl;
	}

	@Override
	public void execute() {
		// Get the current references from GameManager
		Grid grid = GameManager.getBoardShapeGrid();
		List<Player> players = GameManager.getPlayerList();
		Deck cardDeck = GameManager.getDeck();
		Deck discardDeck = GameManager.getDiscardPile();
		// Creating temporary grid from GameManager
		Grid tempGrid = null;
		// Creating temporary list of players from GameManager
		List<Player> tempPlayers = new ArrayList<Player>();
		Deck tempCardDeck = new Deck();
		Deck tempDiscardDeck = new Deck();
		// cloning Grid
		tempGrid = prototypeFactory.getClone(grid);
		// cloning individual players and added them to list
		for (Player player : players) {
			{	
				tempPlayers.add(prototypeFactory.getClone(player));
			}			    	
		}
		//Cloning both the decks
		tempCardDeck = prototypeFactory.getClone(cardDeck);
		tempDiscardDeck = prototypeFactory.getClone(discardDeck);

		gameStatusImpl.saveGame(tempGrid, tempPlayers,tempCardDeck,tempDiscardDeck);
	}

	@Override
	public void undo() {
		 gameStatusImpl.resumeGame();
	}

}
