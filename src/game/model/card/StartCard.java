package game.model.card;

import java.awt.Graphics;
import java.awt.Polygon;

import game.model.visitor.Visitor;

public class StartCard extends Card{

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 8369273410531341005L;

	public StartCard() {
		super.setCardName("START");
	}
	
	public void accept(Visitor artist,Polygon polygon){
		artist.visit(this);
		
	}

}
