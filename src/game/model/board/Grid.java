/**
 * Boards are representations of coordinates when the game is initialised.
 * Coordinates can host card objects and can have cards removed.
 */
package game.model.board;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.google.java.contract.Requires;

import game.exceptions.MissingStartCardGoalCardException;
import game.model.board.algorithm.HexPathSolver;
import game.model.board.algorithm.PathSolver;
import game.model.card.AbstractGoalCard;
import game.model.card.Card;
import game.model.card.PathCard;
import game.model.card.StartCard;
import game.model.card.StoneCard;
import game.model.card.TreasureCard;
import game.model.prototype.GameItem;
import game.model.shape.Shape;
import game.viewer.hexagon.AppHexagonalPanel;

public class Grid implements Shape, GameItem, Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -5096817575824788979L;
	protected static final int NUM_ROWS = 7;
	protected static final int NUM_COLS = 7;

	/* Constants */
	protected Coordinate startSite;
	protected Coordinate[] treasureSites;

	/* Instance Variables */
	protected Map<Coordinate, Stack<Card>> grid = new HashMap<Coordinate, Stack<Card>>();

	/* Path Solver */
	protected PathSolver solver;
	protected Shape shape;

	public Grid() {

	}

	public Grid(int rows, int cols, Shape shape) {
		// define start site & treasure sites on the board
		startSite = new Coordinate(rows - 1, (cols - 1) / 2);
		treasureSites = new Coordinate[(cols - 1) / 2];
		{
			// make sure the treasure sites are parametric with user input
			treasureSites[0] = new Coordinate(0, 0);
			treasureSites[1] = new Coordinate(0, (cols - 1) / 2);
			treasureSites[2] = new Coordinate(0, cols - 1);
		}
		// take user input
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				grid.put(new Coordinate(i, j), new Stack<Card>());
			}
		}
		this.shape = shape;
		solver = new HexPathSolver();
	}

	/**
	 * Initialise the game board grid. Starting card, 2 stone cards and a
	 * treasure card need to randomly be placed and the goal cards hidden.
	 * 
	 */
	public void initBoard() {

		// Start cards and Goal Cards to be set on the Grid.
		StartCard startCard = new StartCard();
		TreasureCard treasureCard = new TreasureCard();
		StoneCard stoneCard1 = new StoneCard();
		StoneCard stoneCard2 = new StoneCard();

		// add the goal cards to array
		List<AbstractGoalCard> goalCards = new ArrayList<AbstractGoalCard>();
		goalCards.add(treasureCard);
		goalCards.add(stoneCard1);
		goalCards.add(stoneCard2);

		// Place the startCard
		placeStartCard(startCard);

		// Place set of goal cards, and random rotation is achieved inside the
		// procedure itself
		placeGoalCards(goalCards);

	}

	/**
	 * First cards placed on the grid.
	 * 
	 * @param startCard
	 * @param cards
	 */
	private void placeStartCard(StartCard startCard) {

		// Place the starting cards on the grid.
		int y = 0;
		int x = 0;
		Coordinate start = getStartSite();

		for (Coordinate c : grid.keySet()) {
			y = c.getRow();
			x = c.getCol();

			// place the cards on the grid.
			if (y == start.getRow() && x == start.getCol())
				placeStartingCards(c, startCard);

		}

	}

	/**
	 * Place the three Goal Cards in the respective location, after random
	 * rotation.
	 * 
	 * @param cards
	 */
	public void placeGoalCards(List<AbstractGoalCard> cards) {

		// Goal Cards are made rotated randomly
		randomRotate(cards);
		int y = 0;
		int x = 0;

		for (Coordinate c : grid.keySet()) {
			y = c.getRow();
			x = c.getCol();
			Coordinate[] treasure = getTreasureSites();

			// place the cards on the grid.
			if ((y == treasure[0].getRow() && x == treasure[0].getCol())
					|| (y == treasure[1].getRow() && x == treasure[1].getCol())
					|| (y == treasure[2].getRow() && x == treasure[2].getCol()))

				// Placing the card in the coordinate after removing from the
				// list
				placeStartingCards(c, cards.remove(0));

		}

	}

	public List<AbstractGoalCard> removeGoalCards() {
		List<AbstractGoalCard> cards = new ArrayList<AbstractGoalCard>();
		int y = 0;
		int x = 0;
		Coordinate[] treasure = getTreasureSites();

		for (Coordinate c : grid.keySet()) {
			y = c.getRow();
			x = c.getCol();

			// Remove all the goal to create Abstract goal cards.
			if ((y == treasure[0].getRow() && x == treasure[0].getCol())
					|| (y == treasure[1].getRow() && x == treasure[1].getCol())
					|| (y == treasure[2].getRow() && x == treasure[2].getCol()))

				// Remove the goal card , and move into list.
				cards.add((AbstractGoalCard) this.removeCardFromGrid(c));

		}

		return cards;

	}

	private Coordinate getStartSite() {
		return startSite;
	}

	private Coordinate[] getTreasureSites() {
		return treasureSites;
	}

	/**
	 * Place a card on a coordinate pile.
	 * 
	 * @param c
	 * @param card
	 */
	private void placeStartingCards(Coordinate c, Card card) {
		getCardStack(c).push(card);
	}

	/**
	 * Return the collection of cards from the given coordinate.
	 * 
	 * @param c
	 * @return
	 */
	public Stack<Card> getCardStack(Coordinate c) {
		return grid.get(c);
	}

	/**
	 * Randomly Rotate the cards.
	 * 
	 * Using Cofoja DbC.
	 * 
	 * @param cards
	 * @return
	 */
	@Requires({ "card.size() > 1" })
	private List<AbstractGoalCard> randomRotate(List<AbstractGoalCard> cards) {
		int distance = (int) (Math.random() * cards.size());
		Collections.rotate(cards, distance);
		return cards;
	}

	/**
	 * Helper class to get coordinate from grid.
	 * 
	 * Using Cofoja DbC.
	 * 
	 * @param n
	 * @return
	 */
	@Requires({ "c.getRow() >= 0 && c.getCol() >= 0" })
	private Coordinate getCoordinateFromGrid(Coordinate xy) {
		for (Coordinate c : grid.keySet())
			if (c.getRow() == xy.getRow() && c.getCol() == xy.getCol())
				return c;
		return null;
	}

	/**
	 * Add card at given node.
	 * 
	 * @param n
	 * @param c
	 */
	public void addCardToGrid(Coordinate xy, Card card) {
		getCardStack(getCoordinateFromGrid(xy)).push(card);
	}

	/**
	 * Get the card at a grid node.
	 * 
	 * @param n
	 * @return
	 */
	public Card getCardFromGrid(Coordinate xy) {
		if (getCardStack(getCoordinateFromGrid(xy)).isEmpty())
			return null;
		return getCardStack(getCoordinateFromGrid(xy)).peek();
	}

	/**
	 * Remove the card from the grid.
	 * 
	 * @param n
	 */
	public Card removeCardFromGrid(Coordinate xy) {
		if (getCardStack(getCoordinateFromGrid(xy)).isEmpty())
			return null;
		return getCardStack(getCoordinateFromGrid(xy)).pop();

	}

	/**
	 * Return the grid Map.
	 * 
	 * @return
	 */
	public Map<Coordinate, Stack<Card>> getGrid() {
		return grid;
	}

	/**
	 * Set the grid Map.
	 * 
	 * @param grid
	 */
	public void setGrid(Map<Coordinate, Stack<Card>> grid) {
		this.grid = grid;
	}

	/**
	 * Check if coordinate stack is empty, if so, return true.
	 * 
	 * @param xy
	 * @return
	 */
	public boolean checkCoordinateIsEmpty(Coordinate xy) {
		// if the return stack is empty, return true
		if (grid.get(getCoordinateFromGrid(xy)).isEmpty())
			return true;
		return false;
	}

	/**
	 * Grid is made to implement Cloneable, so that we could create a copy of
	 * this Grid using Prototype pattern.This clone() method is the Overriden
	 * method of Object Class that return the copy to the caller.
	 * 
	 */
	@Override
	public Grid makeCopy() throws CloneNotSupportedException {
		// Creating the temporary grid
		Grid tempGrid = new HexGrid(true);
		try {
			// Writing current Grid object into stream to implement cloning
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);

			// Reading Grid object from stream
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			// Assigning the grid to temporary grid
			tempGrid = (Grid) ois.readObject();
			// close the streams
			/*
			 * baos.flush(); baos.reset(); oos.flush(); oos.reset();
			 * oos.close(); baos.close(); bais.close(); ois.close();
			 */
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return tempGrid;
	}

	/**
	 * Template Method.
	 * 
	 * @return
	 * @throws MissingStartCardGoalCardException
	 */
	public boolean hasPath() throws MissingStartCardGoalCardException {
		Coordinate source = null;
		Coordinate destination = null;

		// check that these have been stored, otherwise we have an error.
		try {
			destination = getTreasureCardCoordinate();
			source = getStartCardCoordinate();
		} catch (NullPointerException e) {
			throw new MissingStartCardGoalCardException();
		}

		return solver.hasPath(this, source, destination);
	}

	private Coordinate getTreasureCardCoordinate() {
		Coordinate[] treasure = getTreasureSites();
		// get the end coordinate
		for (Coordinate c : treasure) {
			if (getCardFromGrid(c) instanceof TreasureCard)
				return c;
		}

		return null;
	}

	private Coordinate getStartCardCoordinate() {
		Coordinate start = getStartSite();
		// find the start coordinate
		for (Coordinate c : getGrid().keySet()) {
			if (c.getCol() == start.getCol() && c.getRow() == start.getRow()
					&& getCardFromGrid(c) instanceof StartCard) {
				// we have the start node
				return c;
			}
		}
		return null;
	}

	/**
	 * Template Method
	 * 
	 * Check that the current coordinate is an adjacent node with a nearby path
	 * node. Check the exposed edges of the path edges to ensure they are
	 * complementary.
	 * 
	 * neighbor definition is different for even and odd rows. For a cell
	 * (row,col) where col is even, the neighbors are:
	 * (row,col-1),(row+1,col-1),(row-1,col),(row+1,col),(row,col+1),(row+1,col+1)
	 * For a cell (row,col) where col is odd, the neighbors are:
	 * (row-1,col-1),(row,col-1),(row-1,col),(row+1,col),(row-1,col+1),(row,col+1)
	 * 
	 * @return
	 */
	public boolean isAdjacentLeaf(Coordinate c, Card handCard) {
		// check that we have th correct type of card
		if (!(handCard instanceof StartCard) && !(handCard instanceof PathCard))
			return false;
		boolean result = false;
		Coordinate coord;
		int row = c.getRow();
		int col = c.getCol();
		PathCard card;
		card = (PathCard) handCard;
		int edges = card.getNumEdges();
		int mid = edges / 2;
		List<Integer> segments = card.getPathSegments();
		int[][] surround = findSurround(new int[1][edges], row, col, edges);
		int pathSeg = 0;
		// check all the paths of the current pathcard
		for (int i = 0; i < edges; i++) {
			// check that the current edge is valid
			if (segments.get(i) > 0) {
				// check the direction of the path
				pathSeg = getPathSeg(i, mid);
				// check that boundaries have not been reached on a given
				// coordinate
				if (!(surround[i][0] == -1 || surround[i][1] == -1)) {
					coord = new Coordinate(surround[i][0], surround[i][1]);
					// check the connecting path
					Card nextCard = this.getCardFromGrid(coord);
					if (nextCard != null) {
						if (nextCard instanceof PathCard) {
							// Check that the nextCard segment connects with the
							// current card.
							if (((PathCard) nextCard).getPathSegments().get(pathSeg) > 0) {
								// check the path segment match the original
								// card
								// segment
								if (Math.abs(i - pathSeg) == 3) {
									// we have a match! add it to adjacent,
									// return
									// true when successful
									solver.isAdjacentLeaf(coord, nextCard);
									result = true;
								}
							}
						} else if (nextCard instanceof StartCard || nextCard instanceof AbstractGoalCard) {
							// StartCard and AbstractGoalCard connects
							//make goal cards visible
							nextCard.setVisibility(true);
							AppHexagonalPanel.getInstance().getTimer().stop();
							solver.isAdjacentLeaf(coord, nextCard);
							result = true;
						}
					}
				}
			} else {
				// out of bounds do nothing
				// update viewer
				AppHexagonalPanel.getInstance().messageBox.setText("Try Again. Please select an adjacent cell!!!");

			}
		}
		return result;
	}

	private int getPathSeg(int index, int mid) {
		return index < mid ? index + mid : index % mid;
	}

	/**
	 * Returns the surrounding coordinates according to the column, either being
	 * odd or even.
	 * 
	 * @param surround
	 * @param row
	 * @param col
	 * @return
	 */
	private int[][] findSurround(int[][] surround, int row, int col, int edges) {

		// finding the surrounding coordinates
		if (col % 2 == 0) {
			surround = new int[][] { { checkBoundary(row + 1, edges), col }, { row, checkBoundary(col - 1, edges) },
					{ checkBoundary(row - 1, edges), checkBoundary(col - 1, edges) },
					{ checkBoundary(row - 1, edges), col },
					{ checkBoundary(row - 1, edges), checkBoundary(col + 1, edges) },
					{ row, checkBoundary(col + 1, edges) } };

		} else {
			surround = new int[][] { { checkBoundary(row + 1, edges), col },
					{ checkBoundary(row + 1, edges), checkBoundary(col - 1, edges) },
					{ row, checkBoundary(col - 1, edges) }, { checkBoundary(row - 1, edges), col },
					{ row, checkBoundary(col + 1, edges) },
					{ checkBoundary(row + 1, edges), checkBoundary(col + 1, edges) } };
		}
		// TODO - debug
		System.out.print("Col:" + col + ", Surrounding Coordinates: " + surround.toString());
		return surround;
	}

	/**
	 * Boundary test for cells that are off the edge.
	 * 
	 * @param num
	 * @param edges
	 * @return
	 */
	private int checkBoundary(int num, int edges) {
		return (num < 0 ? -1 : num) | (num > edges ? -1 : num);
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
