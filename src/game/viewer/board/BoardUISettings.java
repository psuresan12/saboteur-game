package game.viewer.board;

public class BoardUISettings extends AbstractUISettings{

	
	final int LEFT_BORDER = 100;
	final int COLUMN = 7;
	
	
	@Override
	public int getLeftBorder() {
		return LEFT_BORDER;
	}


	@Override
	public int getColumn() {
		return COLUMN;
	}

}
