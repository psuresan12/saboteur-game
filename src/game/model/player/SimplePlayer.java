package game.model.player;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;

import game.controller.GameManager;
import game.exceptions.ActionCardFunctionFailed;
import game.exceptions.CannotAddPathCardException;
import game.exceptions.ImpotentPlayerException;
import game.exceptions.IncorrectActionCardPairingException;
import game.exceptions.MissingStartCardGoalCardException;
import game.exceptions.UnconnectedPathException;
import game.model.board.Coordinate;
import game.model.board.Grid;
import game.model.card.Card;
import game.model.card.PathCard;
import game.model.card.AbstractGoalCard;
import game.model.card.StartCard;
import game.model.card.actioncard.ActionCard;
import game.model.card.actioncard.BombActionCard;
import game.model.card.personalcard.PersonalCard;
import game.model.deck.Deck;
import game.model.hand.PlayerHand;
import game.model.prototype.ClonableUtils;
import game.model.prototype.GameItem;

/**
 * 
 * Player Class represents each player in the game.
 * 
 * @author ehiew
 *
 */
public class SimplePlayer extends AbstractPlayer implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -8340572340629417103L;
	
	private boolean isNewRound = true;

	/**
	 * Serial ID
	 */
	public SimplePlayer() {
		playerNumber++;
		hand = new PlayerHand();
		role = null;
		name = "Player" + playerNumber;
		goldCoins = 0;
	}

	/**
	 * Player is made to implement Cloneable, so that we could create a copy of
	 * this Player, and its properties using Prototype pattern.This clone()
	 * method is the Overriden method of Object Class that return the copy to
	 * the caller.
	 */
	@Override
	public Player makeCopy() throws CloneNotSupportedException {
		
		Player tempPlayer = new SimplePlayer();		
		tempPlayer = ClonableUtils.cloneThis(this);

		return tempPlayer;
	}

	@Override
	public Player removeDecorator() {
		return this;
	}

	@Override
	public boolean isNewRound() {
		return isNewRound;
	}

	@Override
	public void setNewRound(boolean newRound) {
		isNewRound = newRound;

	}

	
}
