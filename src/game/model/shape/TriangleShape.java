package game.model.shape;

import game.model.shape.Shape;

public class TriangleShape implements Shape {
	
	/* Static Variables */
	private final static int NUM_EDGES = 3;
	private final static double ROTATION_ANGLE = 360 / NUM_EDGES;
	

	@Override
	public double getRotationAngle() {
		return ROTATION_ANGLE;
	}

	@Override
	public int getNumEdges() {
		return NUM_EDGES;
	}


}
