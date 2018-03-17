package game.model.prototype;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;
/**
 * 
 * @author pratap
 *
 */
public class ClonableUtils {
	
	/**
	 * Get the deep cloned object returns it to client
	 * @param toClonable
	 * @return
	 */
	public static <T extends Serializable> T cloneThis(T toClonable){
		T afterCloned = SerializationUtils.clone(toClonable);
		
		return afterCloned;
		
	}

}
