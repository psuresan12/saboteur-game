package game.model.card.actioncard;

import game.model.board.Coordinate;
import game.model.board.Grid;
import game.model.card.Card;
import game.model.card.PathCard;
import game.model.deck.Deck;

@SuppressWarnings("serial")
public class BombActionCard extends ActionCard {

	public BombActionCard() {
		super.setCardName("BOMB");
	}

	@Override
	public boolean action(Grid grid, Coordinate xy, Deck discardPile) {
		boolean isActionCompleted = false;
		Card tempCard = grid.getCardFromGrid(xy); // Get the reference of card
		if (tempCard instanceof PathCard) // Check if the grid has path card
		{
			tempCard = grid.removeCardFromGrid(xy); // Remove the path Card
			discardPile.addCard(tempCard); // Add card to discard pile
			isActionCompleted = true;
		}

		return isActionCompleted;
	}

}
