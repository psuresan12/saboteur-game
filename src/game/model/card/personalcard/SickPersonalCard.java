package game.model.card.personalcard;

import game.model.player.Player;

/**
 * Makes the player not to play the turn once.
 * @author pratap
 */
@SuppressWarnings("serial")
public class SickPersonalCard extends PersonalCard {
	
	public SickPersonalCard() {
		super.setCardName("SICK");
	}

	
	/**
	 * Makes the Player sick for 1 turn.Player cannot able to play turn as long the player 
	 * status is sick.
	 */
	@Override
	public boolean action(Player presentPlayer,Player targetPlayer,int targetPlayerIndex) {
		boolean isPlayerMadeSick = false;
		//Increase the sick count by 1
		targetPlayer.setSick(targetPlayer.getSick()+1);
		isPlayerMadeSick = true; //
		
		return isPlayerMadeSick;
	}


	

}
