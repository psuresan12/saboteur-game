package game.model.prototype;

/**
 * Prototype factory for cloning the object
 * @author pratap
 *
 */
public class PrototypeFactory {
	GameItem gameItem;
	
	@SuppressWarnings("unchecked")
	public <T> T getClone(T sample){
		GameItem gameItem = (GameItem) sample;
		GameItem newGameItem = null;
		try {
			newGameItem = gameItem.makeCopy();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}		
		return (T) newGameItem;		
	}
}
