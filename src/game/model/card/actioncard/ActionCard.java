package game.model.card.actioncard;

import game.model.board.Coordinate;
import game.model.board.Grid;
import game.model.card.AbstractPlayCard;
import game.model.deck.Deck;

@SuppressWarnings("serial")
public abstract class ActionCard extends AbstractPlayCard{

	public abstract boolean action(Grid grid,Coordinate xy, Deck discardPile);
	
}
