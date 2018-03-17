package game.viewer.board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import game.controller.GameManager;
import game.model.board.Grid;
import game.model.shape.Shape;
import game.model.board.*;
import game.model.shape.HexShape;
import game.model.shape.RectangleShape;
import game.model.shape.SquareShape;
import game.model.shape.TriangleShape;

/**
 * 
 * 
 * @description Class to describe the menu settings screen.
 *
 */
@SuppressWarnings("serial")
public class AppMenuPanel extends JPanel {

	private AppFrame appFrame;

	// Label to display info about options.
	JLabel playerDescLabel = new JLabel("Select number of players and enter names:");
	JLabel boardDescLabel = new JLabel("Select Game configuration:");
	JLabel rowLabel = new JLabel("Row");
	JLabel columnLabel = new JLabel("Column");

	// Game settings option to display Players and board shapes.
	String[] numberOfPlayersOption = new String[] { "3 Players", "4 Players", "5 Players", "6 Players" };
	String[] cardShapesOption = new String[] { "Rectangle", "Square", "Triangle", "Hexagonal Flat", "Hexagonal Point" };
	String[] boardShapeOption = new String[] { "Hexagonal", "Rectangle", "Square", "Triangle" };
	String[] boardSettingOption = new String[] { "7", "6" };

	// Combo boxes to show the settings option
	JComboBox<String> playerSettings = new JComboBox<String>(numberOfPlayersOption);
	JComboBox<String> cardSettings = new JComboBox<String>(cardShapesOption);
	JComboBox<String> boardSettings = new JComboBox<String>(boardShapeOption);
	JComboBox<String> rowBoard = new JComboBox<String>(boardSettingOption);
	JComboBox<String> columnBoard = new JComboBox<String>(boardSettingOption);
	JButton submit = new JButton("Start Game"); // Button to start the game in
												// menu settings.
	Shape shape;
	Grid grid;
	int numberOfPlayers;

	// player name and player labels:
	JLabel[] playerListLabel;
	JTextField[] playerNameTextBox;

	/**
	 * 
	 * @param currentFrame
	 */
	public AppMenuPanel(AppFrame newAppFrame) {
		appFrame = newAppFrame;
		setLayout(null);
		add(playerSettings);
		// add(cardSettings);
		// add(boardSettings);
		add(playerDescLabel);
		add(boardDescLabel);
		// add(rowLabel);
		// add(columnLabel);
		add(rowBoard);
		// add(columnBoard);

		add(submit);
		addPlayerName();

		boardDescLabel.setBounds(145, 135, 300, 20);
		playerDescLabel.setBounds(145, 170, 300, 20);

		// Position for row label and combo box
		rowLabel.setBounds(498, 110, 50, 20);
		rowBoard.setBounds(498, 135, 50, 20);
		// Position for column label and combo box
		columnLabel.setBounds(578, 110, 50, 20);
		columnBoard.setBounds(578, 135, 50, 20);

		playerSettings.setBounds(498, 170, 150, 20);
		submit.setBounds(366, 466, 100, 20);

		/**
		 * @Description Event Handler to pass the game settings and initiate the
		 *              game
		 */
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				switch (cardSettings.getSelectedIndex()) {

				case 0:
					shape = new RectangleShape();
				case 1:
					shape = new SquareShape();
				case 2:
					shape = new TriangleShape();
				case 3:
					shape = new HexShape(true);
				case 4:
					shape = new HexShape(false);
				default:
					shape = new HexShape(true);
				}

				switch (boardSettings.getSelectedIndex()) {

				case 0:
					grid = new HexGrid(true);
				case 1:
					grid = new RectangleGrid();
				case 2:
					grid = new SquareGrid();
				case 3:
					grid = new TriangleGrid();

				default:
					grid = new HexGrid(true);
				}

				numberOfPlayers = Character.getNumericValue(playerSettings.getSelectedItem().toString().charAt(0));
				int row = Integer.parseInt((String) rowBoard.getSelectedItem());
				// int column = Integer.parseInt((String)
				// columnBoard.getSelectedItem());
				Grid newGrid = new Grid(row, 7, new HexShape(true));
				GameManager.getInstance().startGame(numberOfPlayers, new HexShape(true), newGrid);

				appFrame.isSettingsUpdated = true;
				// currentFrame.populateBoard();
			}
		});

		/**
		 * Added Mouse Listener for testing purporses:
		 */
		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("In AppMenuPanel" + arg0.getX() + " " + arg0.getY());

			}
		});

		playerSettings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// addPlayerName();

			}

		});

		playerSettings.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				addPlayerName();

			}

		});

	}

	public void addPlayerName() {

		if (playerListLabel != null) {
			for (int i = 0; i < numberOfPlayers; i++) {
				remove(playerListLabel[i]);
				remove(playerNameTextBox[i]);
			}

		}

		numberOfPlayers = Character.getNumericValue(playerSettings.getSelectedItem().toString().charAt(0));
		// System.out.println("In AppMenuPanel - no of players
		// "+numberOfPlayers);
		playerListLabel = new JLabel[numberOfPlayers];
		playerNameTextBox = new JTextField[numberOfPlayers];
		for (int i = 0; i < numberOfPlayers; i++) {
			playerListLabel[i] = new JLabel("Player " + (i + 1) + ":");
			playerListLabel[i].setBounds(498, 205 + (i * 35), 150, 20);
			add(playerListLabel[i]);

			playerNameTextBox[i] = new JTextField("PLAYER_" + (i + 1));
			playerNameTextBox[i].setBounds(570, 205 + (i * 35), 150, 20);
			add(playerNameTextBox[i]);

		}

		revalidate();
		repaint();

	}

}
