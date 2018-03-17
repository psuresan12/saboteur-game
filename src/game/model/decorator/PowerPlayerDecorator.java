package game.model.decorator;

import java.io.Serializable;

import game.controller.GameManager;
import game.model.player.Player;
import game.model.prototype.ClonableUtils;

/**
 * Power Player Decorator that decorates the simple player. Power player gets
 * the extra turn Player can play twice as long they are doceorated with power
 * player decorator
 * 
 * @author pratap
 *
 */
@SuppressWarnings("serial")
public class PowerPlayerDecorator extends AbstractPlayerDecorator implements Serializable {

	private Player player;

	/**
	 * Serial ID
	 */
	public PowerPlayerDecorator(Player player) {
		super(player);
		super.addPowerLevel();
		this.player = player;
		// Modification of simple player attributes

	}

	@Override
	public void finalOperation() {
		super.finalOperation(); // forwards to decoratable Player.
		if (isNewRound()) {
			GameManager.decrementPlayerIndex();
			setNewRound(false);

		}

	}

	@Override
	public Player removeDecorator() {
		super.removePowerLevel();
		return player;
	}

	/**
	 * Player is made to implement Cloneable, so that we could create a copy of
	 * this Player, and its properties using Prototype pattern.This clone()
	 * method is the Overriden method of Object Class that return the copy to
	 * the caller.
	 */
	@Override
	public Player makeCopy() throws CloneNotSupportedException {

		PowerPlayerDecorator tempPlayer;
		tempPlayer = ClonableUtils.cloneThis(this);

		return tempPlayer;
	}

}
