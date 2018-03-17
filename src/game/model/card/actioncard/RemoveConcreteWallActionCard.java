package game.model.card.actioncard;

import game.model.board.Coordinate;
import game.model.board.Grid;
import game.model.card.Card;
import game.model.deck.Deck;

@SuppressWarnings("serial")
public class RemoveConcreteWallActionCard extends ActionCard {

	public RemoveConcreteWallActionCard() {
		super.setCardName("NOWALL");
	}

	@Override
	public boolean action(Grid grid, Coordinate xy, Deck discardPile) {
		boolean isActionCompleted = false;
		Card tempCard = grid.getCardFromGrid(xy); // Get the reference of card
		// TODO - Check if the card is Concrete pair card
		tempCard = grid.removeCardFromGrid(xy); // Remove the ConcreteWall Card
		discardPile.addCard(tempCard); // Add card to discard pile
		isActionCompleted = true;

		return isActionCompleted;

	}

}
