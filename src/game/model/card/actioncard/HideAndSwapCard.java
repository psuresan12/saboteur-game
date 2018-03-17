package game.model.card.actioncard;

import java.util.List;

import game.model.board.Coordinate;
import game.model.board.Grid;
import game.model.card.Card;
import game.model.card.AbstractGoalCard;
import game.model.deck.Deck;

/**
 * Hides the goal card, and swaps them.
 * @author pratap
 *
 */
@SuppressWarnings("serial")
public class HideAndSwapCard extends ActionCard {
	
	public HideAndSwapCard() {
		super.setCardName("HIDE");
	}
	

	@Override
	public boolean action(Grid grid, Coordinate xy, Deck discardPile) {
		boolean isActionCompleted = false;
		List<AbstractGoalCard> goalCards;
		Card tempCard = grid.getCardFromGrid(xy); // Get the reference of card in the grid
		// Check if the card is instance of Goal Card and already hidden.
		if (tempCard instanceof AbstractGoalCard && tempCard.getVisibility()) {
			tempCard.setVisibility(false); // Change the visibility of the card to true.
			//Remove the goal cards and get those in list of abstract goal.
			goalCards = grid.removeGoalCards();
			grid.placeGoalCards(goalCards);
			isActionCompleted = true;
		}

		return isActionCompleted;
	}

}
