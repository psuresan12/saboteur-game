package game.model.card.personalcard;

import game.controller.GameManager;
import game.model.decorator.LuckyPlayerDecorator;
import game.model.player.Player;

/**
 * Add lucky coin to the player. The number of lucky coin is calculated by number of decorator.
 * @author pratap
 *
 */
@SuppressWarnings("serial")
public class AddLuckPersonalCard extends PersonalCard{

	public AddLuckPersonalCard() {
		super.setCardName(" +LUCK");
	}
	
	
	@Override
	public boolean action(Player presentPlayer,Player targetPlayer,int targetPlayerIndex) {
		boolean isLuckAdded = false;
		Player tempPlayer = GameManager.getPlayerByIndex(targetPlayerIndex);
		tempPlayer	= new LuckyPlayerDecorator(tempPlayer);
		GameManager.setPlayerByIndex(tempPlayer, targetPlayerIndex);
		isLuckAdded = true;
		
		
		return isLuckAdded;
	}
	
	
	

}
