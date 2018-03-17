package game.model.card.actioncard;

import game.model.board.Coordinate;
import game.model.board.Grid;
import game.model.card.Card;
import game.model.card.PathCard;
import game.model.deck.Deck;

/**
 * Converts any path card to six path card.It has no effect on 6 path card.
 * @author pratap
 *
 */
@SuppressWarnings("serial")
public class PowerPathActionCard extends ActionCard {
	
	public PowerPathActionCard() {
		super.setCardName("POWER");
	}

	@Override
	public boolean action(Grid grid, Coordinate xy, Deck discardPile) {
		boolean isActionCompleted = false;
		Card tempCard = grid.getCardFromGrid(xy); // Get the reference of card
		PathCard tempPathCard;
		if (tempCard instanceof PathCard) // Check if the grid has path card
		{
			tempPathCard = (PathCard)tempCard; //Type cast to PathCard
			Integer[] sixPathSegment = {1,1,1,1,1}; //Create path segment with six paths
			tempPathCard.setPathSegments(sixPathSegment); //Assign the path segment
			isActionCompleted = true;
		}
			
		return isActionCompleted;
	}

}
