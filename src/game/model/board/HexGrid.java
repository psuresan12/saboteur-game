package game.model.board;

import java.util.Stack;

import game.model.card.Card;
import game.model.shape.HexShape;
import game.model.shape.Shape;

public class HexGrid extends Grid implements Shape{

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -7827126603999898506L;
	
	private Shape shape;
	private boolean isFlat;

	public HexGrid(boolean isFlat) {
		this.setFlat(isFlat);
		shape = new HexShape(isFlat);
	}

	public boolean isFlat() {
		return isFlat;
	}

	public void setFlat(boolean isFlat) {
		this.isFlat = isFlat;
	}


	@Override
	public double getRotationAngle() {
		return shape.getRotationAngle();
	}


	@Override
	public int getNumEdges() {
		return shape.getNumEdges();
	}

}
