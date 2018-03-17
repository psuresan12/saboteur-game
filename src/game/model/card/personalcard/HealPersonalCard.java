package game.model.card.personalcard;

import game.model.player.Player;

/**
 * Heal the player if player is affected from weak card.
 * @author pratap
 *
 */
@SuppressWarnings("serial")
public class HealPersonalCard extends PersonalCard{

	public HealPersonalCard() {
		super.setCardName("HEAL");
	}
	
	
	/**
	 * Heals the Player Weak. If the player is not affected by any Weak, this Heal card has no
	 * effect on player.
	 * Healing the Weak Player - Player resumes to play Action and Personal Cards.
	 * Healing Sick Player - Player resumes to play the turn as normal.
	 * 
	 */	
	@Override
	public boolean action(Player presentPlayer,Player targetPlayer,int targetPlayerIndex) {
		boolean isPlayerHealed = false;
		//Make the player heal from weak, irrespective of the health status.
		targetPlayer.setWeak(false);
		//Make the player heal from sick(i.e make the sick count to 0 )
		targetPlayer.setSick(0);
		isPlayerHealed = true;
		
		return isPlayerHealed;
	}


	

}
