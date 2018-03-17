package game.model.card;

import java.awt.Graphics;
import java.awt.Polygon;

import game.model.visitor.Visitor;

public class StoneCard extends AbstractGoalCard {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 7043678133040878487L;

	public StoneCard() {
		super.setCardName("STONE");
	}

	public void accept(Visitor artist, Polygon polygon) {
		artist.visit(this);

	}

	@Override
	public String getCardName() {
		if (getVisibility())
			return "STONE";
		else
			return "      ?";

	}

}
