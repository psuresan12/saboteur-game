package game.model.visitor;

import java.awt.Color;

import game.model.card.Card;
import game.model.card.PathCard;
import game.viewer.hexagon.HexogonImplUI;

public class ColorVisitor implements Visitor {

	@Override
	public void visit(PathCard pathCard) {
		HexogonImplUI.color = Color.green;
	}

	@Override
	public void visit(Card Card) {
		HexogonImplUI.color = Color.red;
	}

}
