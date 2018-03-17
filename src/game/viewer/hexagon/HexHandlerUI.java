package game.viewer.hexagon;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import game.controller.GameManager;
import game.exceptions.ActionCardFunctionFailed;
import game.exceptions.CannotAddPathCardException;
import game.exceptions.ImpotentPlayerException;
import game.exceptions.IncorrectActionCardPairingException;
import game.exceptions.IncorrectPersonalCardPairingException;
import game.exceptions.MissingStartCardGoalCardException;
import game.exceptions.ServiceDownException;
import game.exceptions.UnconnectedPathException;
import game.model.board.Coordinate;
import game.viewer.board.BoardUISettings;
import game.viewer.board.PlayerUISettings;

public class HexHandlerUI implements MouseListener {

	Coordinate coordinate;

	/**
	 * Event handler for hexagon shaped game
	 */
	public void mouseClicked(MouseEvent event) {
		int[] indexes = new int[2];
		//System.out.println("In mouseClicked() You clicked the Mouse " + event.getX() + " and " + event.getY());

		/*
		 * stores the index of the playerhand, user can able to modify their
		 * changes
		 */
		if (AppHexagonalPanel.getCurrentDestination() == null) {
			HexMathLogicUI.uiSettings = new PlayerUISettings();
			// indices of the player hand
			indexes = HexMathLogicUI.getPosition(event.getX(), event.getY());
			// check for the valid indices
			if (indexes[0] != -1 || indexes[1] != -1) {
				if (indexes[1] >= 0) // allows the user to modify the changes of
										// the card before placing on the gird
				{
					AppHexagonalPanel.currentSource = indexes[1]; // stores the
																	// selected
																	// index
																	// from the
																	// player
																	// hand
					// refreshes the messageBox
					AppHexagonalPanel.getInstance().messageBox
							.setText("You have selected card Number: " + (indexes[1] + 1));
					// refreshing the screen to high light the selected player
					// hand
					AppHexagonalPanel.getInstance().refresh();
				}
				AppHexagonalPanel.discardButton.setEnabled(true); // Enable the
																	// discard
																	// button
																	// for the
																	// selected
																	// card
				AppHexagonalPanel.discardButton.setVisible(true);
				AppHexagonalPanel.getInstance().rotateCard.setEnabled(true); // Enable
																				// the
																				// rotate
																				// card
																				// for
																				// the
																				// selected
																				// card
				AppHexagonalPanel.getInstance().flipButton.setEnabled(true); // Enable
																				// the
																				// flip
																				// card
																				// button
																				// for
																				// the
																				// selected
																				// card
			}
		}

		/**
		 * stores the selected coordinate of the board and allows IF logic to
		 * allow the user to modify the playerIndices but not the grid
		 * co-ordinate
		 */
		if (AppHexagonalPanel.getCurrentSource() != -1 && AppHexagonalPanel.getCurrentDestination() == null) {
			// indexes of the game grid
			HexMathLogicUI.uiSettings = new BoardUISettings();
			indexes = HexMathLogicUI.getPosition(event.getX(), event.getY());

			// check for the valid indices
			if (indexes[0] != -1 || indexes[1] != -1) {
				// Iterating through all the possible coordinate of grid
				boolean isValidMove = false;
				for (Coordinate c : AppHexagonalPanel.board.getGrid().keySet()) {
					// matches the coordinate with the selected index of the
					// board
					if (indexes[1] == c.getRow() && (indexes[0] == c.getCol())) {
						// check for the valid move by passing selected index
						// and coordinate
						try {
							GameManager.takeTurn(AppHexagonalPanel.currentSource, c, false, -1);
						} catch (CannotAddPathCardException e1) {
							AppHexagonalPanel.getInstance().messageBox.setText("Unable to place PathCard");
							e1.getMessage();
						} catch (UnconnectedPathException e2) {
							AppHexagonalPanel.getInstance().messageBox.setText("Unable to place pathCard");
							e2.getMessage();
						} catch (MissingStartCardGoalCardException e3) {
							AppHexagonalPanel.getInstance().messageBox
									.setText("Invalid Move - MissingStartCardGoalCard");
							e3.getMessage();
						} catch (IncorrectActionCardPairingException e4) {
							AppHexagonalPanel.getInstance().messageBox
									.setText("Invalid Move - IncorrectActionCardPairing");
							e4.getMessage();
						} catch (ActionCardFunctionFailed e) {
							AppHexagonalPanel.getInstance().messageBox
									.setText("Invalid Move -Invalid Move - Unable to perform action");
						} catch (ImpotentPlayerException e) {
							AppHexagonalPanel.getInstance().messageBox
									.setText("Invalid Move - ImpotentPlayer to play action card");
						} catch (IncorrectPersonalCardPairingException e) {
							AppHexagonalPanel.getInstance().messageBox
									.setText("Invalid Move - IncorrectPersonalCardPairing");
						} catch (ServiceDownException e) {
							AppHexagonalPanel.getInstance().messageBox.setText("Service Down at this moment");
						}
						break; // comes out of the loop after sends the request
								// to controller

					}

				}

				if (!isValidMove)
					// refreshing the screen to remove the high lighted player
					// hand index
					AppHexagonalPanel.getInstance().refresh();

				AppHexagonalPanel.currentSource = -1; // sets the index of
														// player to default
														// value after passing
														// to controller
				AppHexagonalPanel.currentDestination = null; // sets the
																// cooridnate to
																// default value
																// after passign
																// to controller
				AppHexagonalPanel.discardButton.setEnabled(false); // sets the
																	// discard
																	// button to
																	// default
				AppHexagonalPanel.discardButton.setVisible(false);
				AppHexagonalPanel.getInstance().rotateCard.setEnabled(false); // sets
																				// the
																				// rotate
																				// card
																				// to
																				// default
				AppHexagonalPanel.getInstance().flipButton.setEnabled(false); // sets
																				// the
																				// flip
																				// card
																				// button
																				// to
																				// default
				// Request controller to update the board and player hand if the
				// move is valid
				// Update the messageBox if the move is invlaid
				// else{
				// AppHexagonalPanel.getInstance().messageBox.setText("Invalid
				// Move");
			}
		}
	}

	public void mouseEntered(MouseEvent arg0) {

	}

	public void mouseExited(MouseEvent arg0) {

	}

	public void mousePressed(MouseEvent arg0) {

	}

	public void mouseReleased(MouseEvent arg0) {

	}

}
