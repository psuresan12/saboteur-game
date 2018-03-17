package game.model.decorator;

import java.io.Serializable;

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
import game.model.player.Player;
import game.model.player.Role;

/**
 * Decorator that decorates the simple player
 * @author pratap
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractPlayerDecorator implements Player,Serializable,Cloneable {
	
	

	// store the decorated component
	private Player player;

	public AbstractPlayerDecorator(Player player) {
		this.player = player;
	}

	@Override
	public PlayerHand getHand() {
		return player.getHand();
	}

	@Override
	public int getSick() {
		return player.getSick();
	}

	@Override
	public void setSick(int i) {
		player.setSick(i);

	}

	@Override
	public void setWeak(boolean b) {
		player.setWeak(b);
	}

	@Override
	public String getName() {
		return player.getName();
	}

	@Override
	public void takeTurn(Grid boardShapeGrid, int handIndex, Coordinate xy, Deck cardDeck, Deck discardPile,
			boolean isDiscarded, int targetPlayerIndex)
			throws CannotAddPathCardException, IncorrectActionCardPairingException, UnconnectedPathException,
			MissingStartCardGoalCardException, ActionCardFunctionFailed, ImpotentPlayerException, IncorrectPersonalCardPairingException, ServiceDownException {
		player.takeTurn(boardShapeGrid, handIndex, xy, cardDeck, discardPile, isDiscarded, targetPlayerIndex);
	}

	@Override
	public void addLuckyCoin() {
		player.addLuckyCoin();
		
	}

	@Override
	public void removeLuckyCoin() {
		player.removeLuckyCoin();
		
	}

	@Override
	public void addPowerLevel() {
		player.addPowerLevel();
		
	}

	@Override
	public void removePowerLevel() {
		player.removePowerLevel();
	}

	@Override
	public int getGoldCoins() {
		return player.getGoldCoins();
	}

	@Override
	public void addGoldCoins(int i) {
		player.addGoldCoins(i);
	}

	@Override
	public void finalOperation() {
		if (player != null)
			player.finalOperation();
	}

	@Override
	public boolean isNewRound() {
		return player.isNewRound();
	}

	@Override
	public void setNewRound(boolean newRound) {
		player.setNewRound(newRound);

	}
			
	
	@Override
	public Role getRole() {
		return player.getRole();
	}

	@Override
	public void setRole(Role role) {
		player.setRole(role);
		
	}

	@Override
	public void setName(String name) {
		player.setName(name);
		
	}

	@Override
	public boolean isWeak() {
		return player.isWeak();
	}
	
	
	
	@Override
	public int getPowerLevel() {
		return player.getPowerLevel();
	}

	@Override
	public int getLuckyCoin() {
		return player.getLuckyCoin();
	}

	
}
