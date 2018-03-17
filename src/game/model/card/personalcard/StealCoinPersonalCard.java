package game.model.card.personalcard;

import game.model.player.Player;

/**
 * Steal the coin from the targeted player.
 * @author pratap
 *
 */
@SuppressWarnings("serial")
public class StealCoinPersonalCard extends PersonalCard {

	public StealCoinPersonalCard() {
		super.setCardName("STEAL");
	}
	
	/**
	 * Steal coins from other player.It does not have any effect if player tries to steal from himself
	 */
	@Override
	public boolean action(Player presentPlayer,Player targetPlayer,int targetPlayerIndex) {
		
		boolean isPlayerStealed = false;
		//Gets the count
		int tempCoins = targetPlayer.getGoldCoins();
		targetPlayer.addGoldCoins((-1)*targetPlayer.getGoldCoins());
		presentPlayer.addGoldCoins(tempCoins);
		
		isPlayerStealed = true;
		
		return isPlayerStealed;
		
	}

	

}
