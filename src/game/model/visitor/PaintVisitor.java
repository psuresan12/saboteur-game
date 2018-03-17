package game.model.visitor;

import game.model.card.Card;
import game.model.card.PathCard;
import game.viewer.hexagon.HexogonImplUI;

public class PaintVisitor implements Visitor {

	@Override
	public void visit(PathCard pathCard) {
		HexogonImplUI.drawHexagon(pathCard);

	}

	@Override
	public void visit(Card card) {
		HexogonImplUI.drawHexagon(card);
	}
}
