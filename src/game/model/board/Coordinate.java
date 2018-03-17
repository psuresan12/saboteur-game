package game.model.board;

import java.io.Serializable;

import game.model.prototype.GameItem;

public class Coordinate implements Serializable {

	
	private final int row;
	private final int col;
	
	
	public Coordinate(int row, int col){
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}


	public int getCol() {
		return col;
	}
}
