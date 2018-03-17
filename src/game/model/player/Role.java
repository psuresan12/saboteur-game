package game.model.player;

/**
 * Handles the winning gold calculations based on Role of player: Worker or Saboteur.
 * 
 * @author ehiew
 *
 */
public interface Role {

	public void calculateGold(Player player, int winnerGold, int winners);
	
}
