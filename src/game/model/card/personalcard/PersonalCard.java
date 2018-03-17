package game.model.card.personalcard;

import game.model.card.AbstractPlayCard;
import game.model.player.Player;
/**
 * Abstract personal card, with the signature required to execute personal
 * card functionalities.
 * @author pratap
 *
 */
@SuppressWarnings("serial")
public abstract class PersonalCard extends AbstractPlayCard{
	
	public abstract boolean action(Player presentPlayer,Player targetPlayer, int targetPlayerIndex);

}
