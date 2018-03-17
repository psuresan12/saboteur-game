package game.model.interfaces;

import java.util.Scanner;

import game.exceptions.NumberOfPlayersException;
import game.model.board.Grid;
import game.model.shape.Shape;

public interface ConsoleGameMenu {

	public final static int DEFAULT_BOARD_SHAPE_OPTION = 4; /*
															 * 1 = Rectangle 2 =
															 * Square 3 =
															 * Triangle 4 =
															 * Hexagonal (flat)
															 * 5 = Hexagonal
															 * (pointed)
															 */

	public final static int DEFAULT_NUMBER_PLAYERS_OPTION = 1; /*
																 * 1 = 3 Players
																 * 2 = 4 Players
																 * 3 = 5 Players
																 * 4 = 6 Players
																 */

	public final static int DEFAULT_CARD_SHAPE_OPTION = 4; /*
															 * 1 = Rectangle 2 =
															 * Square 3 =
															 * Triangle 4 =
															 * Hexagonal (flat)
															 * 5 = Hexagonal
															 * (pointed)
															 */

	/**
	 * Display menu
	 */
	public abstract void display();

	/**
	 * Start Initialising
	 */
	// public abstract void startGame();

	/**
	 * Display Player Number Settings
	 */
	public abstract void displayPlayerSettings(Scanner scan);

	/**
	 * Display Board Settings
	 */
	public abstract void displayBoardSettings(Scanner scan);

	/**
	 * Display Card Settings
	 */
	public abstract void displayCardSettings(Scanner scan);

	/**
	 * Player settings 3-6
	 * 
	 * @throws NumberOfPlayersException
	 */
	public abstract void setNumberPlayers(int numPlayers);
	// throws NumberOfPlayersException;

	/**
	 * Board settings - Rectangle, Square, Triangle, Hexagonal(flat),
	 * Hexagonal(pointed)
	 * 
	 * @return
	 */
	public abstract void setBoardShapeGrid(Grid boardShape);

	/**
	 * Card Settings - Rectangle, Square, Triangle, Hexagonal(flat),
	 * Hexagonal(pointed)
	 */
	public abstract void setCardShape(Shape cardShape);

	/**
	 * Exit
	 */
	public abstract void exit();

}
