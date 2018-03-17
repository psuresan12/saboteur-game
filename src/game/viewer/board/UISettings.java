package game.viewer.board;

public interface UISettings {
	
	final int TOP_BORDER = 200;
	final int ROW = 7;
	final int hexagonHeight = 50;
	final int hexagonOuterSide = (int) (hexagonHeight/1.73205);
	final int hexagonInnerSide = (int) (hexagonHeight/(2*1.73205));
	
	
	public int getLeftBorder();
	public int getRow();
	public int getColumn();
	public int getTopBorder();
	public int getHexagonHeight();
	public int getHexagonOuterSide();
	public int getHexagonInnerSide();

}
