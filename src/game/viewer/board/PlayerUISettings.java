package game.viewer.board;

public class PlayerUISettings extends AbstractUISettings {

	
	final int LEFT_BORDER = 500;
	final int COLUMN = 1;
	
	
	@Override
	public int getLeftBorder() {
		return LEFT_BORDER;
	}


	@Override
	public int getColumn() {
		return COLUMN;
	}
	
}
