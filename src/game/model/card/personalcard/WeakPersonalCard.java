package game.model.card.personalcard;

import game.model.player.Player;

/**
 * Makes the player not to use action card , until the player is healed with
 * HealPersonalCard. 
 * @author pratap
 */
@SuppressWarnings("serial")
public class WeakPersonalCard extends PersonalCard {

	public WeakPersonalCard() {
		super.setCardName("WEAK");
	}

	/**
	 * Unable to use any Action Cards. Weak Player can play only path card,
	 * Personal Cards. He is weak until he gets healed using Heal Personal Card.
	 */
	@Override
	public boolean action(Player presentPlayer, Player targetPlayer, int targetPlayerIndex) {
		boolean isPlayerMadeWeak = false;
		// Make the player weak irrespective of the previous health status.
		targetPlayer.setWeak(true);
		isPlayerMadeWeak = true; //

		return isPlayerMadeWeak;
	}

}
