package game.model.interfaces;

import game.model.board.Grid;
import game.model.shape.Shape;

public interface GameMenuView {
	

	/**
	* Start Initialising
	*/
	void startGame(int numPlayers, Shape cardShape, Grid boardShapeGrid);
	
	
	/**
	* Exit
	*/
	void exit();

	
//	/**
//	* Player settings 3-6
//	* @throws NumberOfPlayersException 
//	*/
//	public abstract void setNumberPlayers(int numPlayers);
//	//throws NumberOfPlayersException;
//	
//	/**
//	* Board settings - Rectangle, Square, Triangle, 
//	* Hexagonal(flat), Hexagonal(pointed)
//	* @return 
//	*/
//	public abstract void setBoardShapeGrid(Grid boardShape);
//	
//	/**
//	* Card Settings - Rectangle, Square, Triangle, 
//	* Hexagonal(flat), Hexagonal(pointed) 
//	*/
//	public abstract void setCardShape(CardShape cardShape);
//	

}
