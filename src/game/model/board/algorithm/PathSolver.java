package game.model.board.algorithm;

import game.exceptions.MissingStartCardGoalCardException;
import game.model.board.Coordinate;
import game.model.board.Grid;
import game.model.card.Card;

/**
 * Path Solver interface provides the isAdjacentLeaf and hasPath methods that
 * add nodes to the tree and checks for a path from source to destination.
 * 
 * @author ehiew
 *
 */
public interface PathSolver {

	/**
	 * Returns true if there is a path from start to goal node.
	 * 
	 * Creates a new node for the start and goal node and passes control to
	 * helper method hasPathBFS to handle breadth-first search from start node.
	 * 
	 * @param grid
	 * @param start
	 * @param goal
	 * @return boolean
	 * @throws MissingStartCardGoalCardException
	 */
	boolean hasPath(Grid grid, Coordinate start, Coordinate goal) throws MissingStartCardGoalCardException;

	/**
	 * Checks the breadth first of each node to visit. Any visited nodes are
	 * checked and must be unique to avoid looped paths.
	 * 
	 * @param start
	 * @param end
	 * @return boolean
	 */
	boolean isAdjacentLeaf(Coordinate c, Card nextCard);
}
