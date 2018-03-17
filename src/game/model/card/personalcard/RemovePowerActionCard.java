package game.model.card.personalcard;

import game.controller.GameManager;
import game.model.decorator.LuckyPlayerDecorator;
import game.model.decorator.PowerPlayerDecorator;
import game.model.player.Player;
import game.model.player.SimplePlayer;

/**
 * Removes the power.It has no negative effect on player.
 * 
 * @author pratap
 */
@SuppressWarnings("serial")
public class RemovePowerActionCard extends PersonalCard {

	public RemovePowerActionCard() {
		super.setCardName("-TURN");
	}

	@Override
	public boolean action(Player presentPlayer, Player targetPlayer, int targetPlayerIndex) {
		boolean isPowerRemoved = false;
		int counter = 0;
		while (!(targetPlayer instanceof PowerPlayerDecorator)) {
			if (targetPlayer instanceof SimplePlayer)
				break;
			targetPlayer = targetPlayer.removeDecorator();
			counter++;
		}
		targetPlayer = targetPlayer.removeDecorator();// removing the correct
														// decorator.
		while (counter != 0) {
			targetPlayer = new LuckyPlayerDecorator(targetPlayer);
			counter--;
		}
		GameManager.setPlayerByIndex(targetPlayer, targetPlayerIndex);
		isPowerRemoved = true;

		return isPowerRemoved;
	}

}
