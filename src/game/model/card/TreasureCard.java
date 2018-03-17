package game.model.card;

import java.awt.Graphics;
import java.awt.Polygon;

import game.model.visitor.Visitor;

public class TreasureCard extends AbstractGoalCard {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 4007114541083477473L;

	public TreasureCard() {
		super.setCardName("T");
	}

	public void accept(Visitor artist, Polygon polygon) {
		artist.visit(this);

	}
	
	@Override
	public String getCardName(){
		if(getVisibility())
			return "GOLD";
		else
			return "      ?";			
		
	}

}
