package game.model.card.actioncard;

import game.model.board.Coordinate;
import game.model.board.Grid;
import game.model.card.Card;
import game.model.card.PathCard;
import game.model.deck.Deck;

@SuppressWarnings("serial")
public class ConcreteWallActionCard extends ActionCard{

	public ConcreteWallActionCard() {
		super.setCardName("WALL");
	}

	@Override
	public boolean action(Grid grid, Coordinate xy,Deck discardPile) {
		boolean isActionCompleted = false;
		Card tempCard = grid.getCardFromGrid(xy); // Get the reference of card
		//Check if the card is instance of Goal Card and already hidden.
		if (tempCard instanceof PathCard) {
			//Add ConcreteWallActionCard to the grid coordinate
			grid.addCardToGrid(xy, this); 
			isActionCompleted = true;
		}
		
		return isActionCompleted;
		
	}
	
}
