package game.model.visitor;

import game.model.card.Card;
import game.model.card.PathCard;

public interface Visitor {
	
	void visit(PathCard pathCard);
	void visit(Card Card);
	
	

}
