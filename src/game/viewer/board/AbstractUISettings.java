package game.viewer.board;

import game.viewer.board.UISettings;

public abstract  class AbstractUISettings implements UISettings{
	
	@Override
	public int getRow() {
		return ROW;
	}

	@Override
	public int getTopBorder(){
		return TOP_BORDER;
	}
	
	@Override
	public int getHexagonHeight(){
		
		return hexagonHeight;
		
	}
	
	@Override
	public int getHexagonOuterSide(){
		return hexagonOuterSide;
		
	}
	
	@Override
	public int getHexagonInnerSide(){
		return hexagonInnerSide;
		
	}
}
