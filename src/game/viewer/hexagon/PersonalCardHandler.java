package game.viewer.hexagon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

public class PersonalCardHandler extends MouseAdapter {

	AppHexagonalPanel appHexagonalPanel;
	int targetPlayerIndex;

	public PersonalCardHandler(AppHexagonalPanel appHexagonalPanel, int targetPlayerIndex) {
		this.appHexagonalPanel = appHexagonalPanel;
		this.targetPlayerIndex = targetPlayerIndex;
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		Coordinate dummy = new Coordinate(5, 5);
		boolean isValidMove = false;
		try {			
			GameManager.takeTurn(AppHexagonalPanel.currentSource, dummy, false, targetPlayerIndex);
			isValidMove = true;
		} catch (CannotAddPathCardException e1) {
			AppHexagonalPanel.getInstance().messageBox.setText("Unable to place PathCard");
			e1.getMessage();
		} catch (UnconnectedPathException e2) {
			AppHexagonalPanel.getInstance().messageBox.setText("Unable to place pathCard");
			e2.getMessage();
		} catch (MissingStartCardGoalCardException e3) {
			AppHexagonalPanel.getInstance().messageBox.setText("Invalid Move - MissingStartCardGoalCard");
			e3.getMessage();
		} catch (IncorrectActionCardPairingException e4) {
			AppHexagonalPanel.getInstance().messageBox.setText("Invalid Move - IncorrectActionCardPairing");
			e4.getMessage();
		} catch (ActionCardFunctionFailed e) {
			AppHexagonalPanel.getInstance().messageBox.setText("Invalid Move - Unable to perform action");
		} catch (ImpotentPlayerException e) {
			AppHexagonalPanel.getInstance().messageBox.setText("Invalid Move - ImpotentPlayer to play action card");
		} catch (IncorrectPersonalCardPairingException e) {
			AppHexagonalPanel.getInstance().messageBox.setText("Invalid Move - IncorrectPersonalCardPairing");
		} catch (ServiceDownException e) {
			AppHexagonalPanel.getInstance().messageBox.setText("Service Down at this moment");
		}
		
		AppHexagonalPanel.currentSource = -1; //Set back to the default value
		appHexagonalPanel.isSelectedPlayerIndex = false; //ask to reset the color to unselected.
		if(!isValidMove){
			//refreshing the screen to remove the high lighted  player hand index
			 AppHexagonalPanel.getInstance().refresh();
		}
	}

}
