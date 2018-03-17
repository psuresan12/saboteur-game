package game.model.player;

import game.exceptions.ActionCardFunctionFailed;
import game.exceptions.CannotAddPathCardException;
import game.exceptions.ImpotentPlayerException;
import game.exceptions.IncorrectActionCardPairingException;
import game.exceptions.IncorrectPersonalCardPairingException;
import game.exceptions.MissingStartCardGoalCardException;
import game.exceptions.ServiceDownException;
import game.exceptions.UnconnectedPathException;
import game.model.board.Coordinate;
import game.model.board.Grid;
import game.model.deck.Deck;
import game.model.hand.PlayerHand;
import game.model.prototype.GameItem;

/**
 * Interface for player. client connects only with Player interface that provides necessary methods to access player contents.
 * @author pratap
 *
 */
public interface Player extends GameItem {

	public void finalOperation();

	public PlayerHand getHand();

	public int getSick();

	public void setSick(int i);

	public void setWeak(boolean b);

	public String getName();

	public void takeTurn(Grid boardShapeGrid, int handIndex, Coordinate xy, Deck cardDeck, Deck discardPile,
			boolean isDiscarded, int targetPlayerIndex)
			throws CannotAddPathCardException, IncorrectActionCardPairingException, UnconnectedPathException,
			MissingStartCardGoalCardException, ActionCardFunctionFailed, ImpotentPlayerException, IncorrectPersonalCardPairingException, ServiceDownException;

	public int getGoldCoins();

	public void addGoldCoins(int luckyCoin);

	public Player removeDecorator();

	public boolean isNewRound();

	public void setNewRound(boolean newRound);

	public Role getRole();

	public void setRole(Role role);

	public void setName(String name);

	public boolean isWeak();
	
	public void addLuckyCoin();
	
	public void removeLuckyCoin();
	
	public void addPowerLevel();
	
	public void removePowerLevel();
	
	public int getPowerLevel();
	
	public int getLuckyCoin();

	
	
}
