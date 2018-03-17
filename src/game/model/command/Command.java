package game.model.command;

/**
 * Command interface for store and undo , and save and resume operation  
 * @author pratap
 *
 */
public interface Command {
	
	public void execute();
	public void undo();
	
}
