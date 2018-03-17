package game.model.card.personalcard;

import game.controller.GameManager;
import game.model.decorator.PowerPlayerDecorator;
import game.model.player.Player;
/**
 * Add power level to player. Player can play turn twice consequently.The number of 
 * power level depends on number of decorator.It has no negative effect.
 * @author pratap
 */
@SuppressWarnings("serial")
public class AddPowerPersonalCard extends PersonalCard{

	
	public AddPowerPersonalCard() {
		super.setCardName("+TURN");
	}
	
	@Override
	public boolean action(Player presentPlayer,Player targetPlayer,int targetPlayerIndex) {
		boolean isPowerAdded = false;
		Player tempPlayer = GameManager.getPlayerByIndex(targetPlayerIndex);
		tempPlayer	= new PowerPlayerDecorator(tempPlayer);
		GameManager.setPlayerByIndex(tempPlayer, targetPlayerIndex);
		isPowerAdded = true;
		
		
		return isPowerAdded;
	}

}
