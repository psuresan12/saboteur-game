package game.model.player;

import java.io.Serializable;

import game.viewer.hexagon.AppHexagonalPanel;

/**
 * Worker will take an even share of gold based on number of winners.
 * 
 * @author ehiew
 *
 */
public class Worker implements Role,Serializable,Cloneable{

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 7095469179570726085L;
	
	public Worker() {
		super();
	}

	@Override
	public void calculateGold(Player player, int winnerGold, int winners) {
		player.addGoldCoins(winnerGold/winners);
		AppHexagonalPanel.getInstance().messageBox.append(player.getName() + " was a " + player.getRole()
		+ " this round, and has " + player.getGoldCoins() + " gold coins.");
	}


}
