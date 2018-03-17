package game.model.card.actioncard;

import game.model.board.Coordinate;
import game.model.board.Grid;
import game.model.card.AbstractGoalCard;
import game.model.card.Card;
import game.model.deck.Deck;

/**
 * Reveals the selected goal card.
 * @author pratap
 *
 */
@SuppressWarnings("serial")
public class RevealGoalCard extends ActionCard {

	public RevealGoalCard() {
		super.setCardName("SHOW");
	}

	@Override
	public boolean action(Grid grid, Coordinate xy,Deck discardPile) {
		boolean isActionCompleted = false;
		Card tempCard = grid.getCardFromGrid(xy); // Get the reference of card
		//Check if the card is instance of Goal Card, and  the card is already hidden.
		if (tempCard instanceof AbstractGoalCard && !tempCard.getVisibility()) {
			tempCard.setVisibility(true); //Change the visibility of the card to true.
			isActionCompleted = true;
		}

		return isActionCompleted;

	}

}
