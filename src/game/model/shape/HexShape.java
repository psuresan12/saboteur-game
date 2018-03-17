package game.model.shape;

import java.io.Serializable;

public class HexShape implements Shape, Serializable, Cloneable {

	private static final int NUM_EDGES = 6;
	private static final double ROTATION_ANGLE = 360.0 / NUM_EDGES;

	private boolean isFlat = true;

	public HexShape(boolean isFlat) {
		this.setFlat(isFlat);
	}

	public boolean isFlat() {
		return isFlat;
	}

	public void setFlat(boolean isFlat) {
		this.isFlat = isFlat;
	}

	/* Static Methods */
	@Override
	public double getRotationAngle() {
		return ROTATION_ANGLE;
	}

	@Override
	public int getNumEdges() {
		return NUM_EDGES;
	}

}
