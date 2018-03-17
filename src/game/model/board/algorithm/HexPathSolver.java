package game.model.board.algorithm;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import game.exceptions.MissingStartCardGoalCardException;
import game.model.board.Coordinate;
import game.model.board.Grid;
import game.model.card.Card;
import game.model.card.PathCard;
import game.model.card.StartCard;
import game.model.card.TreasureCard;

/**
 * HexPathSolver implements the PathSolver interface.
 * 
 * 
 * @author ehiew
 *
 */
public class HexPathSolver implements PathSolver,Serializable,Cloneable {

	/**
	 * Tree node structure.
	 * 
	 * List of adjacent nodes, each node has a coordinate and an array of
	 * integers representing the path.
	 * 
	 * @author ehiew
	 *
	 */
	private static class Node {
		private Coordinate c;
		private Card card;
		
		//static for now, but needs to be dynamic in case of bomb card in future
		static LinkedList<Node> adjacent = new LinkedList<Node>();

		private Node(Coordinate c, Card card) {
			this.c = c;
			this.card = card;
		}

		public Card getCard() {
			return card;
		}

	}

	/**
	 * Returns a built node with the coordinate and card.
	 * 
	 * @param c
	 * @param card
	 * @return
	 */
	private Node getNode(Coordinate c, Card card) {
		return new Node(c, card);
	}

	/**
	 * Returns true if there is a path from start to goal node.
	 * 
	 * Creates a new node for the start and goal node and passes control to
	 * helper method hasPathBFS to handle breadth-first search from start node.
	 */
	@Override
	public boolean hasPath(Grid grid, Coordinate start, Coordinate goal) throws MissingStartCardGoalCardException {
		return hasPathBFS(getNode(start, grid.getCardFromGrid(start)), getNode(goal, grid.getCardFromGrid(goal)));
	}

	/**
	 * Checks the breadth first of each node to visit. Any visited nodes are
	 * checked and must be unique to avoid looped paths.
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public boolean hasPathBFS(Node start, Node goal) {
		// queue of nodes to visit
		LinkedList<Node> nextToVisit = new LinkedList<Node>();
		// unique set of visited coordinates
		HashSet<Coordinate> visited = new HashSet<Coordinate>();
		// add root to the queue to visit
		nextToVisit.add(start);
		while (!nextToVisit.isEmpty()) {
			//handle null - if bomb has removed card...
			if(nextToVisit.peek() != null) {
				
				Node node = nextToVisit.remove();
	
				if (node.getCard() == goal.getCard()) {
					// we reached the treasure card
					return true;
				}
	
				// if this is a visited node, we should ignore it
				if (visited.contains(node.c)) {
					continue;
				}
	
				// ...node has been visited, add it to the set
				visited.add(node.c);
	
				// add all the nodes to the list
				for (Node child : Node.adjacent) {
					// add child to the queue
					nextToVisit.add(child);
				}
			} else {
				//...remove it from the list to visit
				nextToVisit.remove();
			}

		}
		return false;
	}

	/**
	 * Check that the current coordinate is an adjacent node with a nearby path
	 * node. Check the exposed edges of the path edges to ensure they are
	 * 
	 * 
	 * @return
	 */
	@Override
	public boolean isAdjacentLeaf(Coordinate c, Card handCard) {
		if (!(handCard instanceof PathCard) && !(handCard instanceof StartCard) && !(handCard instanceof TreasureCard))
			return false; // ignore non-PathCards
		// create a new node representing the current coordinate and its card.
		Node node = getNode(c, handCard);
		// add the new node to the current list of adjacent nodes.
		Node.adjacent.add(node);
		return true;
	}

}
