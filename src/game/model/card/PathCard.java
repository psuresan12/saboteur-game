package game.model.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import game.model.shape.Shape;
import game.model.visitor.Visitor;

public class PathCard extends AbstractPlayCard implements Shape{

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -7192774073307314469L;

	/* Instance Variables */
	private List<Integer> pathSegments;
	private Shape shape;

	public PathCard(Shape shape) {
		pathSegments = new ArrayList<Integer>();
		super.setCardName("PATHCARD");
		this.shape = shape;
	}

	/**
	 * Initialise the pathEdges for the PathCard.
	 * 
	 * @param integers
	 */

	public void setInitialPathSegments(Integer[] integers) {
		for (int i = 0; i < integers.length; i++)
			pathSegments.add(integers[i]);
	}

	/**
	 * Once it is initialised, we can set the values for each of the path
	 * segments.
	 * 
	 * @param integers
	 */
	public void setPathSegments(Integer[] integers) {
		for (int i = 0; i < integers.length; i++)
			pathSegments.set(i, integers[i]);
	}

	public List<Integer> getPathSegments() {
		return pathSegments;
	}

	public void rotatePathCard(int distance) {
		Collections.rotate(pathSegments, distance);
	}

	public void accept(Visitor artist) {
		artist.visit(this);
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
