package game.model.prototype;

/**
 * Interface that groups the the game items that need to be cloned
 * @author pratap
 *
 */
public interface GameItem extends Cloneable {
	
	public GameItem makeCopy() throws CloneNotSupportedException;

}
