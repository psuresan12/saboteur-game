package game.viewer.board;

import java.awt.Container;

import javax.swing.JFrame;

import game.controller.GameManager;
import game.model.board.Grid;
import game.model.player.Player;
import game.viewer.hexagon.AppHexagonalPanel;

@SuppressWarnings("serial")
public class AppFrame extends JFrame {

	@SuppressWarnings("unused")
	private Container cp;
	// variable to check if the game settings has been done
	public AppMenuPanel menu; // handle the Menu settings screen
	public boolean isSettingsUpdated = false;
	private static AppFrame instance;
	private AppResumeDialog appResumeDialog = null;

	public AppHexagonalPanel hexagonBoardUI = null;

	public static AppFrame getInstance() {
		if (instance == null)
			instance = new AppFrame();
		return instance;
	}

	// Initiates the frame to handle settings and board screen
	private AppFrame() {
		super("High Distintion");
		cp = getContentPane();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Check of the game has to be resumed
		if (GameManager.isGameResumable()) {
			setSize(500, 200);
			setLocationRelativeTo(null);
			appResumeDialog = new AppResumeDialog(this);
			add(appResumeDialog);

		}

		else {

			// Initiates Menu screen after checking the menu settings status
			if (!isSettingsUpdated) {
				populateMenu();
			}
		}
		setVisible(true);
	}

	/**
	 * Get the status of Menu settings screen
	 * 
	 * @return
	 */
	public boolean isSettingsUpdated() {
		return isSettingsUpdated;
	}

	/**
	 * Set the settings to true once the Game Menu settings has been done.
	 * 
	 * @param isSettingsUpdated
	 */
	public void setSettingsUpdated(boolean isSettingsUpdated) {
		this.isSettingsUpdated = isSettingsUpdated;
	}

	/**
	 * Initiates the Game Menu settings screen
	 */
	public void populateMenu() {
		if (appResumeDialog != null) {
			remove(appResumeDialog);
			appResumeDialog = null;
		}
		menu = new AppMenuPanel(this);
		makeBiggerScreen();
		add(menu);
		isSettingsUpdated = true; // Game Settings has been made true
		setVisible(true);
	}

	/**
	 * Initiate the game board screen with current grid and playerHand
	 * 
	 * @param boardGrid
	 * @param playerHand
	 */
	public void populateGameBoard(Grid boardGrid, Player player) {

		hexagonBoardUI = AppHexagonalPanel.getInstance();
		hexagonBoardUI.setBoard(boardGrid);
		// Passing the player to set the player and load the screen
		populateGameBoard(player);

	}

	public void populateGameBoard(Player player) {

		hexagonBoardUI.setPlayer(player);
		// Remove menuSettings screen if settings has been completed
		if (isSettingsUpdated) {
			remove(menu);
			isSettingsUpdated = false;
			setVisible(false);
			add(hexagonBoardUI);
			setVisible(true);
		}

		else if (appResumeDialog != null) {
			remove(appResumeDialog);
			setVisible(false);
			add(hexagonBoardUI);
			setVisible(true);
			appResumeDialog = null;
		}

		// Refresh the screen with the updated grid and player hand
		else
			AppHexagonalPanel.getInstance().refresh();
	}

	public void refreshScreen() {
		setVisible(false);
		setVisible(true);
	}

	public void makeBiggerScreen() {
		setSize(1000, 700);
		setLocationRelativeTo(null);
	}

}
