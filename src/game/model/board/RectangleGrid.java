package game.model.board;

import game.model.shape.RectangleShape;
import game.model.shape.Shape;

public class RectangleGrid extends Grid implements Shape {

	private Shape shape;

	public RectangleGrid() {
		shape = new RectangleShape();
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
