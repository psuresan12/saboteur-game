
package game.viewer.hexagon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

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
import game.model.board.Grid;
import game.model.card.Card;
import game.model.hand.PlayerHand;
import game.model.player.Player;
import game.model.visitor.PaintVisitor;
import game.model.visitor.Visitor;
import game.viewer.board.BoardUISettings;
import game.viewer.board.PlayerUISettings;

@SuppressWarnings("serial")
public class AppHexagonalPanel extends JPanel {

	private final int boardRow = 7;
	private final int boardColumn = 7;
	public static Grid board;
	private PlayerHand playerHand;
	private Player player;
	// List of Players
	private JButton[] listOfPlayerBtn;
	@SuppressWarnings("unused")
	private JLabel[] listOfplayerNameLabel;
	@SuppressWarnings("unused")
	private JLabel[] listOfPlayerPtsLabel;
	private JTextArea[] listOfPlayerTextArea;
	private JButton saveGameButton;

	public int playerCardSize;
	public static Graphics gameGraphics;
	private static AppHexagonalPanel instance;

	// Variable for timer
	private Timer timer;

	public Timer getTimer() {
		return timer;
	}

	@SuppressWarnings("unused")
	private long startTime = -1;
	private SimpleDateFormat df = new SimpleDateFormat("mm:ss:SSS");
	private static final long DURATION = 15000;
	private static long duration;

	// variable to store the current round and current player name
	JLabel round, roundLabel, playerName, playerNameLabel, messageBoxLabel, timerLabel;
	public JTextArea messageBox;
	JButton rotateCard, undoButton, flipButton;
	public static JButton discardButton;
	public static int currentSource = -1; // storing the indices of playerhand
											// when its clicked
	public static Coordinate currentDestination = null; // storing the board
														// indices when its
														// clicked

	// variable to change the color of path when user selects the player card
	public boolean isSelectedPlayerIndex = false;
	private String[] undoSettingsOption = new String[] { "1", "2", "3" };
	private JComboBox<String> undoSettings = new JComboBox<String>(undoSettingsOption);

	// variable to refer the polygon
	private Polygon polygon;

	/**
	 * Initializes the board with grid and player hand of current player
	 * Initializes Listener for event handling
	 */
	private AppHexagonalPanel() {
		setLayout(null);
		// sets the dynamic round
		round = new JLabel("1");
		round.setBounds(465, 30, 100, 30);
		// sets the label for round
		// roundLabel = new JLabel("Round No:");
		// roundLabel.setBounds(400, 30, 100, 30);
		// sets the label for playerName
		playerNameLabel = new JLabel("<html><font color='red'size='4'>PlayerName:</html>");
		playerNameLabel.setBounds(340, 15, 100, 30);
		// sets the label for dynamic playerName
		playerName = new JLabel();
		playerName.setBounds(440, 15, 100, 30);

		// sets the discard button for player hand
		discardButton = new JButton("Discard Card");
		discardButton.setBounds(605, 250, 125, 25);
		discardButton.setEnabled(false);
		discardButton.setVisible(false);

		// Message box
		messageBoxLabel = new JLabel("Message Box:");
		messageBoxLabel.setBounds(605, 297, 100, 100);
		messageBox = new JTextArea();
		messageBox.setBounds(605, 360, 300, 250);

		// rotate button
		rotateCard = new JButton("Rotate Card");
		rotateCard.setBounds(605, 200, 125, 25);
		rotateCard.setEnabled(false);

		// flip Button
		flipButton = new JButton("Flip Card");
		flipButton.setBounds(780, 200, 125, 25);
		flipButton.setEnabled(false);

		// undo button
		undoButton = new JButton("UNDO");
		undoButton.setBounds(605, 300, 125, 25);

		// Undo combo box
		undoSettings.setBounds(750, 300, 50, 20);
		undoSettings.setSelectedIndex(0);

		// saveGameButton
		saveGameButton = new JButton("Save Game");
		saveGameButton.setBounds(250, 617, 125, 25);

		// Timer
		timerLabel = new JLabel();
		timerLabel.setBounds(700, 10, 250, 50);

		// Event listener
		HexHandlerUI playerListener = new HexHandlerUI();
		addMouseListener(playerListener);

		add(playerName);
		// add(playerNameLabel);
		add(discardButton);
		add(messageBoxLabel);
		add(messageBox);
		add(rotateCard);
		add(flipButton);
		add(undoButton);
		add(undoSettings);
		add(saveGameButton);
		add(timerLabel);
		updateUndoButton();
		init();
		runTimer();
		// multiPlayerVisibleStatus();

		// Action Listener to request for the card rotation
		rotateCard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				// get the current source of card
				System.out.println(AppHexagonalPanel.currentSource);
				playerHand.rotateCardByIndex(currentSource, 1);
				refresh();
			}

		});

		// Action Listener to request for the card rotation
		flipButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				playerHand.rotateCardByIndex(currentSource, 3);
				refresh();
			}

		});

		// Action Listener to request for discard button
		discardButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				@SuppressWarnings("unused")
				boolean isValidMove = false;
				// Get dummy coordinate of any row/Column as the model demands
				// coordinate even for discard
				Coordinate dummy = new Coordinate(5, 5);

				try {
					GameManager.takeTurn(AppHexagonalPanel.currentSource, dummy, true, -1);
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
					AppHexagonalPanel.getInstance().messageBox
							.setText("Invalid Move - Invalid Move - Unable to perform action");
				} catch (ImpotentPlayerException e) {
					AppHexagonalPanel.getInstance().messageBox
							.setText("Invalid Move - ImpotentPlayer to play action card");
				} catch (IncorrectPersonalCardPairingException e) {
					AppHexagonalPanel.getInstance().messageBox.setText("Invalid Move - IncorrectPersonalCardPairing");
				} catch (ServiceDownException e) {
					AppHexagonalPanel.getInstance().messageBox.setText("Service Down at this moment");
				}

				// if (!isValidMove)
				currentSource = -1; // Set back to the default value
				isSelectedPlayerIndex = false; // ask to reset the color to
												// unselected.
				refresh(); // Refresh the screen in case even if discarded or
							// not discarded.
			}

		});

		// Action Listener to UNDO operation
		undoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentSource = -1; // Set back to the default value
				isSelectedPlayerIndex = false; // ask to reset the color to
												// unselected.
				GameManager.undo(undoSettings.getSelectedIndex() + 1);

			}

		});

		// Action Listener to perform SAVE GAME operation
		saveGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GameManager.saveGame();

			}
		});

	}

	// get the single instance of GameBoardUI
	public static AppHexagonalPanel getInstance() {
		if (instance == null)
			instance = new AppHexagonalPanel();

		return instance;
	}

	public Grid getBoard() {
		return board;
	}

	// storing locally the grid for iteraing
	public void setBoard(Grid newBoard) {
		board = newBoard;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player2) {
		this.player = player2;
		setPlayerHand(player2.getHand());
		setPlayerName(player2.getName());
	}

	public PlayerHand getPlayerHand() {
		return playerHand;
	}

	// storing current player hand locally
	public void setPlayerHand(PlayerHand playerHand) {
		this.playerHand = playerHand;
		this.playerCardSize = playerHand.getPlayerHandSize();
	}

	public void setPlayerName(String playerName) {
		this.playerName.setText("<html><font color='red' size='5'>" + playerName + "</html>");
	}

	// get the player indices that was selected
	public static int getCurrentSource() {
		return currentSource;
	}

	public static void setCurrentSource(int currentSource) {
		AppHexagonalPanel.currentSource = currentSource;
	}

	// get the coordinate of board indices
	public static Coordinate getCurrentDestination() {
		return currentDestination;
	}

	public void setCurrentDestination(Coordinate newCurrentDestination) {
		currentDestination = newCurrentDestination;
	}

	public int getBoardRow() {
		return boardRow;
	}

	public int getBoardColumn() {
		return boardColumn;
	}

	public Polygon getPolygon() {
		return polygon;
	}

	// paint the screen with current board and current player
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		playerName.setText("<html><font color='red' size ='5'>" + player.getName() + "</html>");

		// Assigning the current graphics to global variable,so that event
		// handler can access.
		gameGraphics = graphics;

		// setting UI for grid.
		HexMathLogicUI.uiSettings = new BoardUISettings();

		// Initializing the visitor
		Visitor visitor = new PaintVisitor();
		// Polygon polygon; // local variable to construct polygon
		Card card;

		// Iterating through the board grid
		for (int i = 0; i < boardRow; i++) {
			for (int j = 0; j < boardColumn; j++) {

				graphics.setColor(Color.blue);
				// matching the key of the map(grid)
				for (Coordinate c : board.getGrid().keySet()) {
					if (i == c.getRow() && (j == c.getCol())) {

						// Getting the card from the gird
						card = board.getCardFromGrid(c);
						// Getting the polygon corresponding to the grid
						// position
						polygon = HexMathLogicUI.getPolygon(j, i);
						// Draw the above generated polygon, irrespective of the
						// card type,including null value.
						HexogonImplUI.drawHexagon(polygon);
						// Draw all the cards unless it is empty
						if (card != null) {
							card.accept(visitor); // using
													// Visitor
													// Pattern
						}
					}
				}
			}
		}

		// UI Settings for Player
		HexMathLogicUI.uiSettings = new PlayerUISettings();
		// draw player hand by iterating through card
		for (int i = 0; i < playerHand.getPlayerHandSize(); i++) {
			graphics.setColor(Color.blue);
			polygon = HexMathLogicUI.getPolygon(0, i);
			// Draw the above generated polygon, irrespective of the card
			// type,including null value.
			HexogonImplUI.drawHexagon(polygon);
			card = playerHand.getCardByIndex(i);
			// Settings to change the color of path when player card is selected
			// to play
			if (currentSource == i) {
				isSelectedPlayerIndex = true;
			}
			card.accept(visitor); // using visitor pattern

		}
	}

	public void refresh() {
		updateGoldCoins();
		updateUndoButton();
		runTimer();
		repaint();

	}

	public void init() {
		int numberOfPlayers = GameManager.getPlayerCount();
		List<Player> playerList = GameManager.getPlayerList();
		listOfPlayerBtn = new JButton[numberOfPlayers];
		listOfplayerNameLabel = new JLabel[numberOfPlayers];
		listOfPlayerPtsLabel = new JLabel[numberOfPlayers];
		listOfPlayerTextArea = new JTextArea[numberOfPlayers];

		for (int i = 0; i < numberOfPlayers; i++) {
			// add the list of player name labels
			/*
			 * listOfplayerNameLabel[i] = new JLabel(
			 * playerList.get(i).getName() + " (" +
			 * playerList.get(i).getGoldCoins() + ")");
			 * listOfplayerNameLabel[i].setBounds(105 + (i * 125), 70, 100, 20);
			 * add(listOfplayerNameLabel[i]);
			 */

			// add the list of player name buttons
			listOfPlayerBtn[i] = new JButton("PLAYER_" + (i + 1));
			listOfPlayerBtn[i].setBounds(105 + (i * 125), 150, 100, 20);
			add(listOfPlayerBtn[i]);

			// add the list of player text area
			listOfPlayerTextArea[i] = new JTextArea(
					" GoldCoins - " + playerList.get(i).getGoldCoins() + "\n LuckyLevel - "
							+ playerList.get(i).getLuckyCoin() + "\n PowerLevel -" + playerList.get(i).getPowerLevel()
							+ "\n " + (playerList.get(i).isWeak() == true ? "Weak" : "Not Weak"));
			listOfPlayerTextArea[i].setBounds(105 + (i * 125), 70, 100, 70);
			add(listOfPlayerTextArea[i]);

			// assign the mouse adaptor
			listOfPlayerBtn[i].addMouseListener(new PersonalCardHandler(this, i));
		}

	}

	/**
	 * Update the gold coin details for each player that have been represented
	 * in label.
	 */
	public void updateGoldCoins() {
		List<Player> playerList = GameManager.getPlayerList();
		for (int i = 0; i < playerList.size(); i++) {
			listOfPlayerTextArea[i].setText(" GoldCoins - " + playerList.get(i).getGoldCoins() + "\n LuckyLevel - "
					+ playerList.get(i).getLuckyCoin() + "\n PowerLevel -" + playerList.get(i).getPowerLevel() + "\n "
					+ (playerList.get(i).isWeak() == true ? "Weak" : "Not Weak"));
		}
	}

	private void updateUndoButton() {
		if (GameManager.getPlayerCount() != 0) {
			if (GameManager.undoStatus != null)
				if (GameManager.undoStatus.get(GameManager.getCurrentPlayerIndex())) {
					undoButton.setEnabled(false);
					undoSettings.setEnabled(false);
				} else {
					undoButton.setEnabled(true);
					undoSettings.setEnabled(true);
				}
		}
	}

	/*
	 * Implementation of timer logic.Currently the timer is set to 15 seconds.
	 * This can be altering the final variable DURATION
	 */
	private void runTimer() {
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (!(duration <= 0)) {
					duration = duration - 10;
					timerLabel.setText(
							"<html><font size ='5' color='red'>SHOT CLOCK: " + df.format(duration) + "<font></html>");
				} else {
					currentSource = -1; // Set back to the default value
					isSelectedPlayerIndex = false; // ask to reset the color to
													// unselected.
					GameManager.playerTookTooLong(); // ask for the next player
														// turn , because it is
														// time out
				}

			}
		};
		if (timer == null) {
			timer = new Timer(10, taskPerformer);
			timer.start();
			duration = DURATION;
		} else {
			timer.restart();
			duration = DURATION;
		}
		timer.setRepeats(true);
	}

	/**
	 * @deprecated
	 */
	public void multiPlayerVisibleStatus() {
		int currentPlayerIndex = GameManager.getCurrentPlayerIndex();

		for (int i = 0; i < GameManager.getPlayerCount(); i++) {
			listOfPlayerBtn[i].setVisible(false);
			/*
			 * Checking if the current button belongs to the current player. If
			 * yes, change the visible status. Note: Both variable i and player
			 * index start from 0.
			 */

			if (i != currentPlayerIndex) {
				listOfPlayerBtn[i].setVisible(true);
			}
		}
	}
}
