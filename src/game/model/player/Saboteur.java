package game.model.player;

import java.io.Serializable;
import game.model.player.Player;
import game.viewer.hexagon.AppHexagonalPanel;

/**
 * Saboteur will take an even share of gold based on number of winners.
 * 
 * @author ehiew
 *
 */
public class Saboteur implements Role, Serializable, Cloneable {

	public Saboteur() {
		super();
	}

	@Override
	public void calculateGold(Player player, int winnerGold, int winners) {
		player.addGoldCoins(winnerGold / winners);
		AppHexagonalPanel.getInstance().messageBox.append(player.getName() + " was a " + player.getRole()
		+ " this round, and has " + player.getGoldCoins() + " gold coins.");
	}

}
