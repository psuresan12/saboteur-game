package game.model.card;

import java.io.Serializable;

import game.model.shape.Shape;
import game.model.visitor.Element;
import game.model.visitor.Visitor;

public abstract class Card implements Element, Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -2470288564694584364L;
	
	private boolean isVisible = false; // card is face-down default
	private Shape shape;
	protected String cardName;

	public Card() {

	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public Shape getShape() {
		return shape;
	}

	public void setVisibility(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public boolean getVisibility() {
		return isVisible;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardName() {
		return cardName;
	}

	
	public void accept(Visitor artist){
		artist.visit(this);
	}
}
