package game.model.shape;

import game.model.shape.Shape;

public class RectangleShape implements Shape {

	private static final int NUM_EDGES = 4;
	private static final double ROTATION_ANGLE = 360.0/(NUM_EDGES/2);
	
	@Override
	public double getRotationAngle() {
		return ROTATION_ANGLE;
	}

	@Override
	public int getNumEdges() {
		return NUM_EDGES;
	}

}
