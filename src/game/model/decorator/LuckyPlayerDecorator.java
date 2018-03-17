
package game.model.decorator;

import java.io.Serializable;

import game.model.player.Player;
import game.model.prototype.ClonableUtils;

/**
 * Lucky Player Decorator that decorates the simple player. Simple player gets the lucky coin
 * by just participating the turns.
 * @author pratap
 *
 */
@SuppressWarnings("serial")
public class LuckyPlayerDecorator extends AbstractPlayerDecorator implements Cloneable, Serializable {

	private Player player;
	private final static int LUCKY_COIN = 1;

	/**
	 * Serial ID
	 */
	public LuckyPlayerDecorator(Player player) {
		super(player);
		super.addLuckyCoin();
		this.player = player;

	}

	
	

	@Override
	public void finalOperation() {
		player.finalOperation(); // forwards to decoratable Player.
		player.addGoldCoins(LUCKY_COIN);
		System.out.println(player.getName() + " has " + player.getGoldCoins());
	}

	@Override
	public Player removeDecorator() {
		super.removeLuckyCoin();
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
		
		LuckyPlayerDecorator tempPlayer;		
		tempPlayer = ClonableUtils.cloneThis(this);

		return tempPlayer;
	}


}
