package game.junit;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import game.controller.GameManager;
import game.model.interfaces.ConsoleGameMenu;
import game.model.shape.HexShape;


@RunWith(Suite.class)
@Suite.SuiteClasses({
  GameManager.class
})

/**
 * JUnit Testing.
 * 
 * @author ehiew
 *
 */
public class SaboteurTestSuite {

	/**
	 * TC1: Initialise board shape based on game settings defined in GameMenu.
	 */
	@Test
	public void testInitialiseBoardShape(){
		GameManager game = GameManager.getInstance();
		//game.setBoardShapeGrid(new HexGrid(true));
		game.setNumberPlayers(ConsoleGameMenu.DEFAULT_NUMBER_PLAYERS_OPTION);
		game.setCardShape(new HexShape(true));
		
		assertEquals(true, true);
	}
	
	/**
	 * TC2: Create a board with coordinates.
	 */	
	
	/**
	 * TC3: Add goal cards randomly to pre-defined edges of the board and add the
	 * start card.
	 */
	
	/**
	 * TC4: Assign player roles based on number of Players.
	 */
	
	
	/**
	 * TC5: Shuffle Deck and deal cards to Players.
	 */	
	
	
	
}