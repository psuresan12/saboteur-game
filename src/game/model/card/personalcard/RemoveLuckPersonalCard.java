package game.model.card.personalcard;

import game.controller.GameManager;
import game.model.decorator.LuckyPlayerDecorator;
import game.model.player.Player;
import game.model.player.SimplePlayer;

/**
 * Removes the luck coin.It has no negative effect on the player.
 * 
 * @author pratap
 *
 */
@SuppressWarnings("serial")
public class RemoveLuckPersonalCard extends PersonalCard {

	public RemoveLuckPersonalCard() {
		super.setCardName(" -LUCK");
	}

	@Override
	public boolean action(Player presentPlayer, Player targetPlayer, int targetPlayerIndex) {
		boolean isLuckRemoved = false;
		int counter = 0;
		while (!(targetPlayer instanceof LuckyPlayerDecorator)) {
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
		isLuckRemoved = true;

		return isLuckRemoved;
	}

}
